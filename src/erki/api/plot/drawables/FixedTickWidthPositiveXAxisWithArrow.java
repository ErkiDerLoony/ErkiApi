package erki.api.plot.drawables;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Drawable;

public class FixedTickWidthPositiveXAxisWithArrow implements Drawable {
    
    /** Offset in pixels to the border of the plot. */
    private static final int OFFSET = 5;
    
    private double tickWidth;
    
    private NumberFormat nf;
    
    public FixedTickWidthPositiveXAxisWithArrow(double tickWidth) {
        this.tickWidth = tickWidth;
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        
        // Draw axis with arrow
        Point origin = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                0.0));
        g2.drawLine(origin.x, origin.y, transformer.getScreenWidth() - OFFSET,
                origin.y);
        g2.drawLine(transformer.getScreenWidth() - OFFSET, origin.y,
                transformer.getScreenWidth() - 4 * OFFSET, origin.y - OFFSET);
        g2.drawLine(transformer.getScreenWidth() - OFFSET, origin.y,
                transformer.getScreenWidth() - 4 * OFFSET, origin.y + OFFSET);
        
        // Draw ticks and labels
        for (double i = 0.0; i < transformer.getCartMaxX() - tickWidth; i += tickWidth) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(i,
                    0.0));
            g2.drawLine(p.x, p.y, p.x, p.y + OFFSET);
            String tick = nf.format(i);
            g2.drawString(tick, (float) (p.x - 0.5 * g2.getFontMetrics()
                    .stringWidth(tick)), (float) (p.y + OFFSET + g2
                    .getFontMetrics().getHeight()));
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 1.5 * tickWidth, 0.0);
    }
}
