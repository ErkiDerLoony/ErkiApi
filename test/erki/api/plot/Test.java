package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JFrame;

import erki.api.plot.drawables.CirclePoint;
import erki.api.plot.drawables.DrawableLine;
import erki.api.plot.drawables.LineAxes;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.plot.style.StyleProvider;

public class Test {
    
    private static boolean killed = false;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D();
        final StyleProvider styleProvider = new BasicStyleProvider();
        plot.add(new LineAxes(styleProvider));
        cp.add(plot, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        new Thread() {
            
            @Override
            public void run() {
                super.run();
                
                Random random = new Random();
                int timestamp = 0;
                Point2D.Double old = new Point2D.Double(timestamp, random
                        .nextGaussian() + 5.0);
                plot.add(new CirclePoint(old, styleProvider));
                
                while (!killed) {
                    timestamp++;
                    Point2D.Double newPoint = new Point2D.Double(timestamp,
                            random.nextGaussian() + 5.0);
                    plot.add(new DrawableLine(old, newPoint, styleProvider));
                    plot.add(new CirclePoint(newPoint, styleProvider));
                    plot.autorange();
                    old = newPoint;
                    
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                    }
                }
            }
            
        }.start();
        
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                killed = true;
            }
        });
    }
}
