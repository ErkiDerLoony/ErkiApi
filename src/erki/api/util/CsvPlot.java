package erki.api.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.action.AutoRange;
import erki.api.plot.action.Move;
import erki.api.plot.action.PdfExport;
import erki.api.plot.action.PopupMenu;
import erki.api.plot.action.Zoom;
import erki.api.plot.drawables.LineAxes;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.plot.style.StyleProvider;

/**
 * This program can plot simple csv files that contain two values in each line (one x- and one
 * y-coordinate). The coordinates are expected to be of double precision (see
 * {@link Double#parseDouble(String)}).
 * 
 * @author Edgar Kalkowski
 */
public class CsvPlot {
    
    private static Color[] colours = { Color.BLUE, Color.GREEN, Color.RED, Color.CYAN };
    
    public static void main(String[] arguments) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        StyleProvider styleProvider = new BasicStyleProvider();
        
        Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(800, 600));
        plot.add(new Move(MouseEvent.BUTTON1));
        plot.add(new Zoom());
        plot.add(new LineAxes(styleProvider));
        PopupMenu menu = new PopupMenu();
        plot.add(menu);
        plot.add(new AutoRange(menu.getPopupMenu()));
        plot.add(new PdfExport(menu.getPopupMenu()));
        cp.add(plot, BorderLayout.CENTER);
        
        int counter = 0;
        
        if (arguments.length == 1) {
            frame.setTitle(arguments[0]);
        } else {
            frame.setTitle("Plot");
        }
        
        for (String arg : arguments) {
            
            try {
                BufferedReader fileIn = new BufferedReader(new FileReader(arg));
                
                TimeSeries timeSeries = new TimeSeries(colours[counter++], false, styleProvider);
                plot.add(timeSeries);
                String line;
                
                while ((line = fileIn.readLine()) != null) {
                    String[] split = line.split(",");
                    
                    if (split.length < 2) {
                        Log.error("Line “" + line + "” contains too few values!");
                        continue;
                    }
                    
                    if (split.length > 2) {
                        Log.warning("Line ”" + line + "” contains too many values (ignoring all "
                                + "but the first two)!");
                    }
                    
                    try {
                        double x = Double.parseDouble(split[0].trim());
                        double y = Double.parseDouble(split[1].trim());
                        timeSeries.add(x, y);
                    } catch (NumberFormatException e) {
                        Log.error("Could not parse values in line “" + line
                                + "” (ignoring this line).");
                    }
                }
                
                Log.info("Characteristics of “" + arg + "”:");
                Log.info("\tMinimum: " + MathUtil.round(timeSeries.getMin(), 7));
                Log.info("\tMaximum: " + MathUtil.round(timeSeries.getMax(), 7));
                Log.info("\tMean: " + MathUtil.round(timeSeries.getMean(), 7));
                Log.info("\tStd: " + MathUtil.round(timeSeries.getStd(), 7));
                
            } catch (FileNotFoundException e) {
                Log.error(e);
                Log.warning("Ignoring file “" + arg + "”.");
            } catch (IOException e) {
                Log.error(e);
                Log.warning("Ignoring the rest of file “" + arg + "”.");
            }
        }
        
        plot.autorange();
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
