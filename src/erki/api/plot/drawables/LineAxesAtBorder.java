package erki.api.plot.drawables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.util.TreeSet;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;
import erki.api.util.Log;
import erki.api.util.MathUtil;

/**
 * This class provides classic coordinate axes that always include the origin of the coordinate
 * system. In contrast to this {@link LineAxesAtBorder} are always located at the lower and left
 * border of the plot panel regardless of the coordinates.
 * 
 * @author Edgar Kalkowski
 */
public class LineAxesAtBorder extends StyledDrawable {
    
    public LineAxesAtBorder(StyleProvider styleProvider) {
        super(styleProvider);
    }
    
    private double[] steps = { 0.00001, 0.000025, 0.00005, 0.0001, 0.00025, 0.0005, 0.001, 0.0025,
            0.005, 0.01, 0.025, 0.05, 0.1, 0.25, 0.5, 1.0, 2.5, 5.0, 10.0, 25.0, 50.0, 100.0,
            250.0, 500.0, 1000.0, 2500.0, 5000.0, 10000.0 };
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Stroke oldStroke = g2.getStroke();
        Color oldColour = g2.getColor();
        Font oldFont = g2.getFont();
        
        g2.setStroke(styleProvider.getProperty(new StylePropertyKey<Stroke>("AXES_STROKE"))
                .getProperty());
        g2.setColor(styleProvider.getProperty(new StylePropertyKey<Color>("AXES_COLOR"))
                .getProperty());
        g2.setFont(styleProvider.getProperty(new StylePropertyKey<Font>("AXES_TICK_FONT"))
                .getProperty());
        
        final int BORDER = styleProvider.getProperty(new StylePropertyKey<Integer>("AXES_BORDER"))
                .getProperty();
        final int FONT_HEIGHT = g2.getFontMetrics().getHeight();
        final int ARROW_OFFSET = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_ARROW_OFFSET")).getProperty();
        final int TICK_OFFSET = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_TICK_OFFSET")).getProperty();
        final int Y0 = transformer.getScreenHeight() - 2 * BORDER - FONT_HEIGHT - TICK_OFFSET;
        
        // Estimate tick steps
        int maxWidth = 0;
        int xStep = 0, yStep = 0;
        boolean ticksFit = true;
        
        do {
            int oldY = Y0;
            ticksFit = true;
            maxWidth = 0;
            
            for (double i = steps[yStep]; i < Math.max(Math.abs(transformer.getCartMaxY()), Math
                    .abs(transformer.getCartMinY())); i += steps[yStep]) {
                Point p = transformer.getScreenCoordinates(new Point2D.Double(0.0, i));
                String tick = MathUtil.round(i, 7) + "";
                
                if (tick.endsWith(".0")) {
                    tick = tick.substring(0, tick.length() - 2);
                }
                
                if (g2.getFontMetrics().stringWidth(tick) > maxWidth) {
                    maxWidth = g2.getFontMetrics().stringWidth(tick);
                }
                
                if (p.y + 0.5 * g2.getFontMetrics().getHeight() > oldY) {
                    ticksFit = false;
                    yStep++;
                    break;
                } else {
                    oldY = p.y - g2.getFontMetrics().getHeight();
                }
            }
            
        } while (yStep < steps.length - 1 && !ticksFit);
        
        final int X0 = 2 * BORDER + maxWidth + TICK_OFFSET;
        
        final Point2D.Double ORIGIN = transformer.getCarthesianCoordinates(new Point(X0, Y0));
        Log.debug("X0 = " + X0 + ", Y0 = " + Y0 + " -> (" + MathUtil.round(ORIGIN.getX(), 7) + ", "
                + MathUtil.round(ORIGIN.getY(), 7) + ").");
        
        do {
            int oldX = Y0;
            ticksFit = true;
            
            for (double i = ORIGIN.getX(); i < Math.max(Math.abs(transformer.getCartMaxX()), Math
                    .abs(transformer.getCartMinX())); i += steps[xStep]) {
                Point p = transformer.getScreenCoordinates(new Point2D.Double(i, 0.0));
                String tick = MathUtil.round(i, 7) + "";
                
                if (tick.endsWith(".0")) {
                    tick = tick.substring(0, tick.length() - 2);
                }
                
                if (p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) < oldX) {
                    ticksFit = false;
                    xStep++;
                    break;
                } else {
                    oldX = p.x + g2.getFontMetrics().stringWidth(tick);
                }
            }
            
        } while (xStep < steps.length - 1 && !ticksFit);
        
        // Draw axes
        g2.drawLine(X0, Y0, transformer.getScreenWidth(), Y0);
        g2.drawLine(X0, Y0, X0, 0);
        
        // Draw arrows
        g2.drawLine(transformer.getScreenWidth(), Y0, transformer.getScreenWidth() - 2
                * ARROW_OFFSET, Y0 - ARROW_OFFSET);
        g2.drawLine(transformer.getScreenWidth(), Y0, transformer.getScreenWidth() - 2
                * ARROW_OFFSET, Y0 + ARROW_OFFSET);
        g2.drawLine(X0, 0, X0 - ARROW_OFFSET, 2 * ARROW_OFFSET);
        g2.drawLine(X0, 0, X0 + ARROW_OFFSET, 2 * ARROW_OFFSET);
        
        // Actually draw the ticks
        for (double i = ORIGIN.getX(); i < Math.max(Math.abs(transformer.getCartMaxX()), Math
                .abs(transformer.getCartMinX())); i += steps[xStep]) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(i, Y0));
            String tick = MathUtil.round(i, 7) + "";
            
            if (tick.endsWith(".0")) {
                tick = tick.substring(0, tick.length() - 2);
            }
            
            if (p.x + 0.5 * g2.getFontMetrics().stringWidth(tick) < transformer.getScreenWidth()
                    - 2 * ARROW_OFFSET
                    && p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) > ARROW_OFFSET) {
                g2.drawLine(p.x, p.y - TICK_OFFSET, p.x, p.y + TICK_OFFSET);
                g2.drawString(tick, (int) (p.x - 0.5 * g2.getFontMetrics().stringWidth(tick)), p.y
                        + g2.getFontMetrics().getHeight() + TICK_OFFSET + 3);
            }
            
            p = transformer.getScreenCoordinates(new Point2D.Double(-i, Y0));
            tick = MathUtil.round(-i, 7) + "";
            
            if (tick.endsWith(".0")) {
                tick = tick.substring(0, tick.length() - 2);
            }
            
            if (p.x + 0.5 * g2.getFontMetrics().stringWidth(tick) < transformer.getScreenWidth()
                    - 2 * ARROW_OFFSET
                    && p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) > ARROW_OFFSET) {
                g2.drawLine(p.x, p.y - TICK_OFFSET, p.x, p.y + TICK_OFFSET);
                g2.drawString(tick, (int) (p.x - 0.5 * g2.getFontMetrics().stringWidth(tick)), p.y
                        + g2.getFontMetrics().getHeight() + TICK_OFFSET + 3);
            }
        }
        
        for (double i = steps[yStep]; i < Math.max(Math.abs(transformer.getCartMaxY()), Math
                .abs(transformer.getCartMinY())); i += steps[yStep]) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(0.0, i));
            
            if (p.y - 0.5 * g2.getFontMetrics().getHeight() > 2 * ARROW_OFFSET
                    && p.y + 0.5 * g2.getFontMetrics().getHeight() < transformer.getScreenHeight()
                            - ARROW_OFFSET) {
                String tick = MathUtil.round(i, 7) + "";
                
                if (tick.endsWith(".0")) {
                    tick = tick.substring(0, tick.length() - 2);
                }
                
                g2.drawLine(p.x - TICK_OFFSET, p.y, p.x + TICK_OFFSET, p.y);
                g2.drawString(tick, p.x - TICK_OFFSET - 2 - maxWidth, (int) (p.y + 0.5
                        * g2.getFontMetrics().getHeight() - g2.getFontMetrics().getDescent()));
            }
            
            p = transformer.getScreenCoordinates(new Point2D.Double(0.0, -i));
            
            if (p.y - 0.5 * g2.getFontMetrics().getHeight() > 2 * ARROW_OFFSET
                    && p.y + 0.5 * g2.getFontMetrics().getHeight() < transformer.getScreenHeight()
                            - ARROW_OFFSET) {
                String tick = MathUtil.round(-i, 7) + "";
                
                if (tick.endsWith(".0")) {
                    tick = tick.substring(0, tick.length() - 2);
                }
                
                g2.drawLine(p.x - TICK_OFFSET, p.y, p.x + TICK_OFFSET, p.y);
                g2.drawString(tick, p.x - TICK_OFFSET - 2 - maxWidth
                        - g2.getFontMetrics().stringWidth("-"), (int) (p.y + 0.5
                        * g2.getFontMetrics().getHeight() - g2.getFontMetrics().getDescent()));
            }
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
        g2.setFont(oldFont);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        TreeSet<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("AXES_STROKE"));
        properties.add(new StylePropertyKey<Color>("AXES_COLOR"));
        properties.add(new StylePropertyKey<Integer>("AXES_TICK_OFFSET"));
        properties.add(new StylePropertyKey<Integer>("AXES_BORDER", "Distance in pixels of the "
                + "tick labels of the axes toward the border of the plot panel."));
        properties.add(new StylePropertyKey<Font>("AXES_TICK_FONT"));
        return properties;
    }
}
