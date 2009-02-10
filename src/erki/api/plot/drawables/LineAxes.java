package erki.api.plot.drawables;

import java.awt.Color;
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
    
    private static final int ROUND_RESOLUTION = 7;
    
    private double[] steps = { 0.00001, 0.000025, 0.00005, 0.0001, 0.00025,
            0.0005, 0.001, 0.0025, 0.005, 0.01, 0.025, 0.05, 0.1, 0.25, 0.5,
            1.0, 2.5, 5.0, 10.0, 25.0, 50.0, 100.0, 250.0, 500.0, 1000.0,
            2500.0, 5000.0, 10000.0 };
    
    private int xStep = 4, yStep = 4;
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        g2.setColor(styleProvider.getProperty(
                new StylePropertyKey<Color>("AXES_COLOR")).getProperty());
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("AXES_STROKE")).getProperty());
        
        Point origin = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                0.0));
        
        // Draw axes lines
        g2.drawLine(0, origin.y, transformer.getScreenWidth(), origin.y);
        g2.drawLine(origin.x, 0, origin.x, transformer.getScreenHeight());
        
        // Draw arrows
        Integer offset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"))
                .getProperty();
        g2.drawLine(transformer.getScreenWidth(), origin.y, transformer
                .getScreenWidth()
                - 2 * offset, origin.y + offset);
        g2.drawLine(transformer.getScreenWidth(), origin.y, transformer
                .getScreenWidth()
                - 2 * offset, origin.y - offset);
        g2.drawLine(origin.x, 0, origin.x + offset, 2 * offset);
        g2.drawLine(origin.x, 0, origin.x - offset, 2 * offset);
        
        // Recalculate tick steps if necessary
        int xTicks = 0;
        boolean resIncreasedLock = false;
        offset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_TICK_OFFSET"))
                .getProperty();
        
        while (true) {
            int oldTickPos = origin.x;
            boolean resIncreased = false;
            xTicks = 0;
            
            for (double i = steps[xStep]; i < Math.max(Math.abs(transformer
                    .getCartMaxX()), Math.abs(transformer.getCartMinX())); i += steps[xStep]) {
                
                // Translate point to screen coordinates
                Point2D.Double pCart = new Point2D.Double(i, 0.0);
                Point pScreen = transformer.getScreenCoordinates(pCart);
                
                // Calculate tick position and eventually raise xStep
                String tick = MathUtil.round(pCart.getX(), ROUND_RESOLUTION)
                        + "";
                int xPos = (int) (pScreen.x - 0.5 * g2.getFontMetrics()
                        .stringWidth(tick));
                
                if (xPos <= oldTickPos && xStep < steps.length - 1) {
                    xStep++;
                    resIncreased = true;
                    break;
                } else {
                    oldTickPos = (int) (pScreen.x + g2.getFontMetrics()
                            .stringWidth(tick));
                }
                
                xTicks++;
            }
            
            if (resIncreased == true) {
                resIncreasedLock = true;
                continue;
            }
            
            // Reduce xStep if less than 5 ticks were drawn unless it's already
            // on maximum
            if (xTicks < 5 && xStep > 0 && !resIncreasedLock) {
                xStep--;
            } else {
                break;
            }
        }
        
        // Actually draw the ticks
        for (double i = steps[xStep]; i < Math.max(Math.abs(transformer
                .getCartMaxX()), Math.abs(transformer.getCartMinX())); i += steps[xStep]) {
            
            if (i < transformer.getCartMaxX() - 0.5 * steps[xStep]
                    && i > transformer.getCartMinX() + 0.5 * steps[xStep]) {
                Point pScreen = transformer
                        .getScreenCoordinates(new Point2D.Double(i, 0.0));
                g2.drawLine(pScreen.x, pScreen.y - offset, pScreen.x, pScreen.y
                        + offset);
                
                String tick = MathUtil.round(i, ROUND_RESOLUTION) + "";
                int xPos = (int) (pScreen.x - 0.5 * g2.getFontMetrics()
                        .stringWidth(tick));
                g2.drawString(tick, xPos, pScreen.y + 3 + offset
                        + g2.getFontMetrics().getHeight());
            }
            
            if (-i < transformer.getCartMaxX() - 0.5 * steps[xStep]
                    && -i > transformer.getCartMinX() + 0.5 * steps[xStep]) {
                Point pScreen = transformer
                        .getScreenCoordinates(new Point2D.Double(-i, 0.0));
                g2.drawLine(pScreen.x, pScreen.y - offset, pScreen.x, pScreen.y
                        + offset);
                
                String tick = MathUtil.round(-i, ROUND_RESOLUTION) + "";
                int xPos = (int) (pScreen.x - 0.5 * g2.getFontMetrics()
                        .stringWidth(tick));
                g2.drawString(tick, xPos, pScreen.y + 3 + offset
                        + g2.getFontMetrics().getHeight());
            }
        }
        
        int yTicks = 0;
        resIncreasedLock = false;
        
        while (true) {
            int oldTickPos = origin.y;
            boolean resIncreased = false;
            yTicks = 0;
            
            for (double i = steps[yStep]; i < Math.max(Math.abs(transformer
                    .getCartMaxY()), Math.abs(transformer.getCartMinY())); i += steps[yStep]) {
                
                // Translate point to screen coordinates
                Point2D.Double pCart = new Point2D.Double(0.0, i);
                Point pScreen = transformer.getScreenCoordinates(pCart);
                
                // Calculate tick position and eventually raise yStep
                int yPos = (int) (pScreen.y + 0.5 * g2.getFontMetrics()
                        .getHeight());
                
                if (yPos >= oldTickPos && yStep < steps.length - 1) {
                    yStep++;
                    resIncreased = true;
                    break;
                } else {
                    oldTickPos = (int) (pScreen.y - g2.getFontMetrics()
                            .getHeight());
                }
                
                yTicks++;
            }
            
            if (resIncreased == true) {
                resIncreasedLock = true;
                continue;
            }
            
            // Reduce yStep if less than 5 ticks were drawn unless it's already
            // on maximum
            if (yTicks < 5 && yStep > 0 && !resIncreasedLock) {
                yStep--;
            } else {
                break;
            }
        }
        
        // Actually draw the ticks
        for (double i = steps[yStep]; i < Math.max(Math.abs(transformer
                .getCartMaxY()), Math.abs(transformer.getCartMinY())); i += steps[yStep]) {
            
            if (i < transformer.getCartMaxY() - 0.5 * steps[yStep]
                    && i > transformer.getCartMinY() + 0.5 * steps[yStep]) {
                Point pScreen = transformer
                        .getScreenCoordinates(new Point2D.Double(0.0, i));
                g2.drawLine(pScreen.x - offset, pScreen.y, pScreen.x + offset,
                        pScreen.y);
                
                String tick = MathUtil.round(i, ROUND_RESOLUTION) + "";
                int yPos = (int) (pScreen.y + 0.5 * g2.getFontMetrics()
                        .getHeight());
                g2.drawString(tick, pScreen.x - offset - 2
                        - g2.getFontMetrics().stringWidth(steps[yStep] + ""),
                        yPos);
            }
            
            if (-i < transformer.getCartMaxY() - 0.5 * steps[yStep]
                    && -i > transformer.getCartMinY() + 0.5 * steps[yStep]) {
                Point pScreen = transformer
                        .getScreenCoordinates(new Point2D.Double(0.0, -i));
                g2.drawLine(pScreen.x - offset, pScreen.y, pScreen.x + offset,
                        pScreen.y);
                
                String tick = MathUtil.round(-i, ROUND_RESOLUTION) + "";
                int yPos = (int) (pScreen.y + 0.5 * g2.getFontMetrics()
                        .getHeight());
                g2.drawString(tick, pScreen.x - offset - 2
                        - g2.getFontMetrics().stringWidth("-" + steps[yStep]),
                        yPos);
            }
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        Set<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Color>("AXES_COLOR"));
        properties.add(new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"));
        properties.add(new StylePropertyKey<Integer>("AXES_TICK_OFFSET"));
        properties.add(new StylePropertyKey<Stroke>("AXES_STROKE"));
        return properties;
    }
}
