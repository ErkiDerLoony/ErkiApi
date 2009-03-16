package erki.api.plot.em;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.util.TreeSet;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.drawables.StyledDrawable;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

public class PatternDrawer extends StyledDrawable {
    
    private Color[] colours = { Color.RED, Color.GREEN, Color.BLUE };
    
    private final SlidingWindow<LabeledPattern> window;
    
    public PatternDrawer(SlidingWindow<LabeledPattern> window, StyleProvider styleProvider) {
        super(styleProvider);
        this.window = window;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("POINT_STROKE")).getProperty());
        int size = styleProvider.getProperty(
                new StylePropertyKey<Integer>("POINT_SIZE")).getProperty();
        int i = 0;
        
        for (LabeledPattern point : window) {
            Color c = colours[point.getLabel() % colours.length];
            
            try {
                c = new Color(c.getRed(), c.getGreen(), c.getBlue(),
                        (int) (255.0 * (i / (double) window.size())));
            } catch (IllegalArgumentException e) {
                System.out.println("i = " + i + "; size = " + window.size()
                        + "; alpha = "
                        + (int) (255.0 * (i / (double) window.size())));
                System.exit(-1);
            }
            
            g2.setColor(c);
            Point p = transformer.getScreenCoordinates(point);
            g2.drawArc((int) (p.getX() - 0.5 * size),
                    (int) (p.getY() - 0.5 * size), size, size, 0, 360);
            i = Math.min(i + 1, window.size());
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        
        if (window.size() == 0) {
            return null;
        } else {
            double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
            
            for (Point2D.Double point : window) {
                
                if (point.getX() < minX) {
                    minX = point.getX();
                }
                
                if (point.getX() > maxX) {
                    maxX = point.getX();
                }
                
                if (point.getY() < minY) {
                    minY = point.getY();
                }
                
                if (point.getY() > maxY) {
                    maxY = point.getY();
                }
            }
            
            return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
        }
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        TreeSet<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("POINT_STROKE"));
        properties.add(new StylePropertyKey<Integer>("POINT_SIZE"));
        return properties;
    }
}
