package erki.api.plot.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Drawable;

public class CirclePoint extends Point2D.Double implements Drawable {
    
    private static final long serialVersionUID = -2994504030381719105L;
    
    private static final Stroke STROKE = new BasicStroke(1.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    
    private static final int WIDTH = 10, HEIGHT = 10;
    
    private Color colour;
    
    private Stroke stroke;
    
    public CirclePoint(double x, double y, Color colour, Stroke stroke) {
        super(x, y);
        this.colour = colour;
        this.stroke = stroke;
    }
    
    public CirclePoint(double x, double y, Color colour) {
        this(x, y, colour, STROKE);
    }
    
    public CirclePoint(double x, double y) {
        this(x, y, Color.BLACK, STROKE);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point p = transformer.getScreenCoordinates(this);
        
        g2.setStroke(stroke);
        g2.setColor(colour);
        g2.drawArc(p.x - (int) (0.5 * WIDTH), p.y - (int) (0.5 * HEIGHT),
                WIDTH, HEIGHT, 0, 360);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(getX(), getY(), 0.0, 0.0);
    }
}
