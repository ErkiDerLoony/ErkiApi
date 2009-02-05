package erki.api.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MixtureInfluenceFunction implements Drawable {
    
    private double o;
    
    public MixtureInfluenceFunction(double o) {
        this.o = o;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point p1 = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                f(0.0)));
        Point p2 = transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o)));
        Point p3 = transformer.getScreenCoordinates(new Point2D.Double(1.0,
                f(1.0)));
        
        g2.setColor(Color.RED);
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2.drawLine(p2.x, p2.y, p3.x, p3.y);
        
        Line2D.Double l1 = new Line2D.Double(transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o))), transformer
                .getScreenCoordinates(new Point2D.Double(o, 0.0)));
        Line2D.Double l2 = new Line2D.Double(transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o))), transformer
                .getScreenCoordinates(new Point2D.Double(0.0, f(o))));
        
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f, 10.0f },
                0.0f));
        g2.draw(l1);
        g2.draw(l2);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    private double f(double x) {
        
        if (x <= o) {
            return x / (2.0 * o);
        } else {
            return x / (2.0 * (1.0 - o)) + 0.5 - o / (2 * (1 - o));
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
    }
}
