package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import erki.api.plot.drawables.CrossPoint;
import erki.api.plot.drawables.FixedTickWidthPositiveXAxisWithArrow;
import erki.api.util.Log;

public class SimpleTest {
    
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setTitle("A simple test of the plot api");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Log.setDebug(true);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D(0.0, 1.0, 0.0, 1.0);
        plot.setPreferredSize(new Dimension(500, 500));
        plot.addDrawable(new FixedTickWidthPositiveXAxisWithArrow(0.1));
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.getButton() == MouseEvent.BUTTON2) {
                    Point2D.Double p = plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY()));
                    plot.addDrawable(new CrossPoint(p.x, p.y, new Color(
                            (int) (Math.random() * 256),
                            (int) (Math.random() * 256),
                            (int) (Math.random() * 256))));
                    plot.repaint();
                }
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
