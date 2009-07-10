package erki.api.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.drawables.LineAxes;
import erki.api.plot.style.BasicStyleProvider;

public class OnlineCsvPlot {
    
    private static final Color[] colours = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE };
    
    private static boolean killed = false;
    
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.err.println("I need at least one csv file to plot!");
        }
        
        JFrame frame = new JFrame("Plot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(800, 600));
        frame.add(plot);
        
        BasicStyleProvider styleProvider = new BasicStyleProvider();
        plot.add(new LineAxes(styleProvider));
        
        if (args.length == 1) {
            frame.setTitle(args[0]);
        }
        
        for (int i = 0; i < args.length; i++) {
            final String filename = args[i];
            final TimeSeries series = new TimeSeries(colours[i], false);
            plot.add(series);
            
            new Thread(filename) {
                
                @Override
                public void run() {
                    super.run();
                    File file;
                    
                    while (!killed) {
                        
                        if ((file = new File(filename).getAbsoluteFile()).exists()) {
                            
                            try {
                                BufferedReader fileIn = new BufferedReader(new FileReader(file));
                                String line;
                                
                                while ((line = fileIn.readLine()) != null) {
                                    String[] split = line.split(",");
                                    
                                    if (split.length >= 2) {
                                        
                                        try {
                                            series.add(Double.parseDouble(split[0]), Double
                                                    .parseDouble(split[1]));
                                            plot.autorange();
                                        } catch (NumberFormatException e) {
                                            System.out.println("Error parsing line “" + line
                                                    + "”. Skipping that line.");
                                        }
                                    }
                                }
                                
                            } catch (FileNotFoundException e) {
                                // Try again in next loop.
                            } catch (IOException e) {
                                // Try again in next loop.
                            }
                        }
                        
                        try {
                            Thread.sleep(33);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                
            }.start();
        }
        
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                killed = true;
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
