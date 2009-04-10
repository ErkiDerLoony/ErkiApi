package erki.api.plot.mandelbrot;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.drawables.Drawable;

public class Mandelbrot implements Drawable {
    
    private static final int MAX_ITERATIONS = 100;
    
    private static final double THRESHOLD = 0.0001;
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        
        for (int x = 0; x < transformer.getScreenWidth(); x++) {
            
            for (int y = 0; y < transformer.getScreenHeight(); y++) {
                Point2D.Double p = transformer.getCarthesianCoordinates(new Point(x, y));
                Complex c = new Complex(p.getX(), p.getY());
                Complex z = new Complex(0.0, 0.0);
                
                for (int i = 0; i < MAX_ITERATIONS; i++) {
                    z = Complex.add(Complex.mult(z, z), c);
                }
                
                if (Complex.abs(z) > THRESHOLD) {
                    g2.fillArc(x, y, 1, 1, 0, 360);
                }
            }
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
