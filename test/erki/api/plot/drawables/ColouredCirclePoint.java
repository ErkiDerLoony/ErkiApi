package erki.api.plot.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;

public class ColouredCirclePoint implements Drawable {
    
    private static final int OFFSET = 3;
    
    private Point2D.Double point;
    
    private Color colour;
    
    public ColouredCirclePoint(Point2D.Double point, Color colour) {
        this.point = point;
        this.colour = colour;
    }
    
    public double getX() {
        return point.getX();
    }
    
    public double getY() {
        return point.getY();
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Point point = transformer.getJava(this.point);
        Color oldColour = g2.getColor();
        g2.setColor(colour);
        g2.drawArc(point.x - OFFSET, point.y - OFFSET, 2 * OFFSET, 2 * OFFSET, 0, 360);
        g2.setColor(oldColour);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(point.getX() - OFFSET, point.getY() - OFFSET, 2.0 * OFFSET,
                2.0 * OFFSET);
    }
}
