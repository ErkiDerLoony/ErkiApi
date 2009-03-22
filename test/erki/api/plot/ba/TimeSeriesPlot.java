package erki.api.plot.ba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.action.Move;
import erki.api.plot.action.Zoom;
import erki.api.plot.drawables.LineAxes;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.plot.style.StyleProvider;
import erki.api.util.CommandLineParser;
import erki.api.util.MathUtil;

public class TimeSeriesPlot {
    
    private static Color[] colours = { Color.BLUE, Color.GREEN, Color.RED, Color.CYAN };
    
    public static void main(String[] arguments) {
        TreeMap<String, String> args = CommandLineParser.parse(arguments);
        System.out.println(args);
        
        JFrame frame = new JFrame("Measures");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        StyleProvider styleProvider = new BasicStyleProvider();
        
        Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(700, 450));
        plot.add(new Move(MouseEvent.BUTTON1));
        plot.add(new Zoom());
        plot.add(new LineAxes(styleProvider));
        cp.add(plot, BorderLayout.CENTER);
        
        if (!args.keySet().contains("--files")) {
            System.err.println("I need some --files!");
            System.exit(-1);
        }
        
        int counter = 0;
        
        if (args.keySet().contains("--markers")) {
            
            for (String arg : args.get("--markers").split(";")) {
                
                try {
                    BufferedReader fileIn = new BufferedReader(new FileReader(arg));
                    MarkerDrawer markerDrawer = new MarkerDrawer(colours[counter++]);
                    plot.add(markerDrawer);
                    String line;
                    
                    while ((line = fileIn.readLine()) != null) {
                        
                        try {
                            markerDrawer.addMarker(Double.parseDouble(line));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        counter = 0;
        
        for (String arg : args.get("--files").split(";")) {
            
            try {
                BufferedReader fileIn = new BufferedReader(new FileReader(arg));
                
                TimeSeries timeSeries = new TimeSeries(colours[counter++], false, styleProvider);
                plot.add(timeSeries);
                String line;
                
                while ((line = fileIn.readLine()) != null) {
                    String[] split = line.split(",");
                    
                    if (split.length < 2) {
                        System.err.println("Line “" + line
                                + "” is too short (two semicolon separated values needed)!");
                        continue;
                    }
                    
                    if (split.length > 2) {
                        System.err.println("Line ”" + line
                                + "” is too long (two semicolon separated values needed)!");
                        continue;
                    }
                    
                    try {
                        double x = Double.parseDouble(split[0].trim());
                        double y = Double.parseDouble(split[1].trim());
                        timeSeries.add(x, y);
                    } catch (NumberFormatException e) {
                        System.err.println("Could not parse line “" + line + "” (ignoring).");
                    }
                }
                
                System.out.println(arg);
                System.out.println("\tMin: " + MathUtil.round(timeSeries.getMin(), 3));
                System.out.println("\tMax: " + MathUtil.round(timeSeries.getMax(), 3));
                System.out.println("\tMean: " + MathUtil.round(timeSeries.getMean(), 3));
                System.out.println("\tStd: " + MathUtil.round(timeSeries.getStd(), 3));
                
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + arg + " (ignoring that series).");
            } catch (IOException e) {
                System.err.println("Error while reading from file " + arg
                        + " (ignoring the rest of that series).");
            }
        }
        
        plot.autorange();
        
        if (args.containsKey("--range")) {
            
            try {
                double maxX = Double.parseDouble(args.get("--range"));
                double rangeX = maxX - plot.getXMin();
                plot.setXRange(0 - 0.1 * rangeX, maxX + 0.1 * rangeX);
            } catch (NumberFormatException e) {
                System.err.println("Could not parse range argument!");
            }
        }
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
