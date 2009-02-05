package erki.api.plot.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Drawable;

public class CrossPoint extends Point2D.Double implements Drawable {
    
    private static final long serialVersionUID = -7603800844085258749L;
    
    private static final int OFFSET = 5;
    
    private Color colour;
    
    public CrossPoint(double x, double y, Color colour) {
        super(x, y);
        this.colour = colour;
    }
    
    public CrossPoint(double x, double y) {
        this(x, y, Color.BLACK);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        
        Point p = transformer.getScreenCoordinates(this);
        g2.setColor(colour);
        g2.drawLine(p.x - OFFSET, p.y - OFFSET, p.x + OFFSET, p.y + OFFSET);
        g2.drawLine(p.x - OFFSET, p.y + OFFSET, p.x + OFFSET, p.y - OFFSET);
        
        g2.setColor(oldColour);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(getX(), getY(), 0.0, 0.0);
    }
}
