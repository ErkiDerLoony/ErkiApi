package erki.api.plot.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;

public class ColouredLine implements Drawable {
    
    private Point2D.Double from, to;
    private Color colour;
    
    public ColouredLine(Point2D.Double from, Point2D.Double to, Color colour) {
        this.from = from;
        this.to = to;
        this.colour = colour;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        g2.setColor(colour);
        Point from = transformer.getJava(this.from);
        Point to = transformer.getJava(this.to);
        g2.drawLine(from.x, from.y, to.x, to.y);
        g2.setColor(oldColour);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        double minx = Math.min(from.getX(), to.getX());
        double maxx = Math.max(from.getX(), to.getX());
        double miny = Math.min(from.getY(), to.getY());
        double maxy = Math.max(from.getY(), to.getY());
        return new Rectangle2D.Double(minx, miny, maxx - minx, maxy - miny);
    }
}
