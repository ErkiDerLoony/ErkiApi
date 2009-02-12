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
import erki.api.util.MathUtil;

public class LineAxes implements Drawable {
    
    private double[] steps = { 0.00001, 0.000025, 0.00005, 0.0001, 0.00025,
            0.0005, 0.001, 0.0025, 0.005, 0.01, 0.025, 0.05, 0.1, 0.25, 0.5,
            1.0, 2.5, 5.0, 10.0, 25.0, 50.0, 100.0, 250.0, 500.0, 1000.0,
            2500.0, 5000.0, 10000.0 };
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider) {
        Stroke oldStroke = g2.getStroke();
        Color oldColour = g2.getColor();
        Font oldFont = g2.getFont();
        
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("AXES_STROKE")).getProperty());
        g2.setColor(styleProvider.getProperty(
                new StylePropertyKey<Color>("AXES_COLOR")).getProperty());
        g2.setFont(styleProvider.getProperty(
                new StylePropertyKey<Font>("AXES_TICK_FONT")).getProperty());
        
        Point origin = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                0.0));
        
        // Draw axes
        int arrowOffset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"))
                .getProperty();
        g2.drawLine(0, origin.y, transformer.getScreenWidth(), origin.y);
        g2.drawLine(origin.x, 0, origin.x, transformer.getScreenHeight());
        
        // Draw arrows
        g2.drawLine(transformer.getScreenWidth(), origin.y, transformer
                .getScreenWidth()
                - 2 * arrowOffset, origin.y - arrowOffset);
        g2.drawLine(transformer.getScreenWidth(), origin.y, transformer
                .getScreenWidth()
                - 2 * arrowOffset, origin.y + arrowOffset);
        g2.drawLine(origin.x, 0, origin.x - arrowOffset, 2 * arrowOffset);
        g2.drawLine(origin.x, 0, origin.x + arrowOffset, 2 * arrowOffset);
        
        // Estimate tick steps
        int xStep = 0, yStep = 0;
        boolean ticksFit = true;
        
        do {
            int oldX = origin.x;
            ticksFit = true;
            
            for (double i = steps[xStep]; i < Math.max(Math.abs(transformer
                    .getCartMaxX()), Math.abs(transformer.getCartMinX())); i += steps[xStep]) {
                Point p = transformer.getScreenCoordinates(new Point2D.Double(
                        i, 0.0));
                String tick = MathUtil.round(i, 7) + "";
                
                if (p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) < oldX) {
                    ticksFit = false;
                    xStep++;
                    break;
                } else {
                    oldX = p.x + g2.getFontMetrics().stringWidth(tick);
                }
            }
            
        } while (xStep < steps.length - 1 && !ticksFit);
        
        int maxWidth = 0;
        
        do {
            int oldY = origin.y;
            ticksFit = true;
            maxWidth = 0;
            
            for (double i = steps[yStep]; i < Math.max(Math.abs(transformer
                    .getCartMaxY()), Math.abs(transformer.getCartMinY())); i += steps[yStep]) {
                Point p = transformer.getScreenCoordinates(new Point2D.Double(
                        0.0, i));
                String tick = MathUtil.round(i, 7) + "";
                
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
        
        // Actually draw the ticks
        int tickOffset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_TICK_OFFSET"))
                .getProperty();
        
        for (double i = steps[xStep]; i < Math.max(Math.abs(transformer
                .getCartMaxX()), Math.abs(transformer.getCartMinX())); i += steps[xStep]) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(i,
                    0.0));
            String tick = MathUtil.round(i, 7) + "";
            
            if (p.x + 0.5 * g2.getFontMetrics().stringWidth(tick) < transformer
                    .getScreenWidth()
                    - 2 * arrowOffset
                    && p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) > arrowOffset) {
                g2.drawLine(p.x, p.y - tickOffset, p.x, p.y + tickOffset);
                g2.drawString(tick, (int) (p.x - 0.5 * g2.getFontMetrics()
                        .stringWidth(tick)), p.y
                        + g2.getFontMetrics().getHeight() + tickOffset + 3);
            }
            
            p = transformer.getScreenCoordinates(new Point2D.Double(-i, 0.0));
            tick = MathUtil.round(-i, 7) + "";
            
            if (p.x + 0.5 * g2.getFontMetrics().stringWidth(tick) < transformer
                    .getScreenWidth()
                    - 2 * arrowOffset
                    && p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) > arrowOffset) {
                g2.drawLine(p.x, p.y - tickOffset, p.x, p.y + tickOffset);
                g2.drawString(tick, (int) (p.x - 0.5 * g2.getFontMetrics()
                        .stringWidth(tick)), p.y
                        + g2.getFontMetrics().getHeight() + tickOffset + 3);
            }
        }
        
        for (double i = steps[yStep]; i < Math.max(Math.abs(transformer
                .getCartMaxY()), Math.abs(transformer.getCartMinY())); i += steps[yStep]) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                    i));
            
            if (p.y - 0.5 * g2.getFontMetrics().getHeight() > 2 * arrowOffset
                    && p.y + 0.5 * g2.getFontMetrics().getHeight() < transformer
                            .getScreenHeight()
                            - arrowOffset) {
                String tick = MathUtil.round(i, 7) + "";
                g2.drawLine(p.x - tickOffset, p.y, p.x + tickOffset, p.y);
                g2.drawString(tick, p.x - tickOffset - 2 - maxWidth, (int) (p.y
                        + 0.5 * g2.getFontMetrics().getHeight() - g2
                        .getFontMetrics().getDescent()));
            }
            
            p = transformer.getScreenCoordinates(new Point2D.Double(0.0, -i));
            
            if (p.y - 0.5 * g2.getFontMetrics().getHeight() > 2 * arrowOffset
                    && p.y + 0.5 * g2.getFontMetrics().getHeight() < transformer
                            .getScreenHeight()
                            - arrowOffset) {
                String tick = MathUtil.round(-i, 7) + "";
                g2.drawLine(p.x - tickOffset, p.y, p.x + tickOffset, p.y);
                g2.drawString(tick, p.x - tickOffset - 2 - maxWidth
                        - g2.getFontMetrics().stringWidth("-"), (int) (p.y
                        + 0.5 * g2.getFontMetrics().getHeight() - g2
                        .getFontMetrics().getDescent()));
            }
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
        g2.setFont(oldFont);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        TreeSet<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("AXES_STROKE"));
        properties.add(new StylePropertyKey<Color>("AXES_COLOR"));
        properties.add(new StylePropertyKey<Integer>("AXES_TICK_OFFSET"));
        properties.add(new StylePropertyKey<Font>("AXES_TICK_FONT"));
        return properties;
    }
}
