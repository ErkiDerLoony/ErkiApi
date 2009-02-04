package erki.api.plot;

import java.awt.Graphics2D;

public interface Drawable {
    
    void draw(Graphics2D g2, CoordinateTransformer transformer);
    
}
