package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import erki.api.plot.drawables.CirclePoint;
import erki.api.plot.drawables.DrawableLine;
import erki.api.plot.drawables.FixedTickWidthPositiveXAxisWithArrow;
import erki.api.plot.drawables.FixedTickWidthPositiveYAxisWithArrow;

public class UsefulnessTest {
    
    private static double evaluation, init, time;
    
    private static boolean killed = false;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test der Nützlichkeitsformel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(500, 500));
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addDrawable(new FixedTickWidthPositiveXAxisWithArrow(0.5));
        plot.addDrawable(new FixedTickWidthPositiveYAxisWithArrow(0.1));
        
        plot.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                evaluation += 0.1;
            }
        });
        
        time = 0.1;
        init = 0.5;
        evaluation = init;
        
        new Thread("FadeThread") {
            
            private Point2D.Double oldPoint = null;
            
            @Override
            public void run() {
                super.run();
                
                while (!killed && time < 5.0) {
                    // evaluation = (evaluation - init) * Math.exp(-lambda) +
                    // init;
                    // evaluation = (evaluation - init) * 0.75 + init;
                    evaluation = 0.75 * evaluation + 0.25 * init;
                    Point2D.Double newPoint = new Point2D.Double(time,
                            evaluation);
                    plot.addDrawable(new CirclePoint(newPoint.getX(), newPoint
                            .getY(), Color.RED));
                    
                    if (oldPoint != null) {
                        plot.addDrawable(new DrawableLine(oldPoint, newPoint,
                                Color.RED));
                    }
                    
                    oldPoint = newPoint;
                    time += 0.1;
                    
                    System.out.println("time = " + time + ", evaluation = "
                            + evaluation);
                    plot.autorange();
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
            
        }.start();
        
        frame.pack();
        plot.autorange();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                killed = true;
            }
        });
    }
}
