package erki.api.plot.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Drawable;

public class DrawableLine implements Drawable {
    
    private static final long serialVersionUID = 6844062407525196699L;
    
    private Point2D.Double start;
    private Point2D.Double end;
    private Color colour;
    
    public DrawableLine(Point2D.Double start, Point2D.Double end, Color colour) {
        this.start = start;
        this.end = end;
        this.colour = colour;
    }
    
    public DrawableLine(Point2D.Double start, Point2D.Double end) {
        this(start, end, Color.BLACK);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        
        Point p = transformer.getScreenCoordinates(start);
        Point q = transformer.getScreenCoordinates(end);
        g2.setColor(colour);
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        g2.setColor(oldColour);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(Math.min(start.getX(), end.getX()), Math
                .min(start.getY(), end.getY()), Math.abs(start.getX()
                - end.getX()), Math.abs(start.getY() - end.getY()));
    }
}
