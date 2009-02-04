package erki.api.plot;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import erki.api.util.Log;

public class DrawablePoint extends Point2D.Double implements Drawable {
    
    private static final long serialVersionUID = -7603800844085258749L;
    
    public DrawablePoint(double x, double y) {
        super(x, y);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Point p = transformer.getScreenCoordinates(this);
        g2.drawLine(p.x - 5, p.y - 5, p.x + 5, p.y + 5);
        g2.drawLine(p.x - 5, p.y + 5, p.x + 5, p.y - 5);
        Log.debug(this, "Drawing point (" + getX() + ", " + getY() + ") to ("
                + p.x + ", " + p.y + ").");
    }
}
