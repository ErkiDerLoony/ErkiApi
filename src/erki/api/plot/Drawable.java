package erki.api.plot;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public interface Drawable {
    
    void draw(Graphics2D g2, CoordinateTransformer transformer);
    
    Rectangle2D.Double getBounds();
    
}
