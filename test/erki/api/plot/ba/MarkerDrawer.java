package erki.api.plot.ba;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.drawables.Drawable;

public class MarkerDrawer implements Drawable {
    
    private Color colour;
    
    private LinkedList<Double> markers = new LinkedList<Double>();
    
    public MarkerDrawer(Color colour) {
        this.colour = colour;
    }
    
    public void addMarker(double x) {
        markers.add(x);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        for (double marker : markers) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(marker, 0.0));
            Point q = transformer.getScreenCoordinates(new Point2D.Double(marker, transformer
                    .getCartMaxY()));
            
            g2.setColor(colour);
            g2.setStroke(new BasicStroke(1.5f));
            
            g2.drawLine(p.x, p.y, q.x, q.y);
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        
        if (markers.isEmpty()) {
            min = 0;
            max = 0;
        } else {
            
            for (double d : markers) {
                
                if (d < min) {
                    min = d;
                }
                
                if (d > max) {
                    max = d;
                }
            }
        }
        
        return new Rectangle2D.Double(min, 0, max - min, 0);
    }
}
