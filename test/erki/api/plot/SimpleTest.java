package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import erki.api.util.Log;

public class SimpleTest {
    
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Log.setDebug(true);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(500, 500));
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Point2D.Double p = plot
                        .getCoordinateTransformer()
                        .getCarthesianCoordinates(new Point(e.getX(), e.getY()));
                plot.addDrawable(new DrawablePoint(p.x, p.y));
                plot.repaint();
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
