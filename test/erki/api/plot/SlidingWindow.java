package erki.api.plot;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import erki.api.plot.drawables.CirclePoint;
import erki.api.plot.drawables.DrawableLine;
import erki.api.plot.drawables.StyledDrawable;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;
import erki.api.util.MathUtil;

public class SlidingWindow extends StyledDrawable {
    
    private Queue<CirclePoint> window = new ConcurrentLinkedQueue<CirclePoint>();
    
    private int size;
    
    public SlidingWindow(int size, StyleProvider styleProvider) {
        super(styleProvider);
        this.size = size;
    }
    
    public void add(CirclePoint point) {
        window.offer(point);
        
        if (window.size() > size) {
            window.poll();
        }
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        CirclePoint old = null;
        
        for (CirclePoint point : window) {
            point.draw(g2, transformer);
            
            if (old != null) {
                new DrawableLine(new Point2D.Double(old.getX(), old.getY()), new Point2D.Double(
                        point.getX(), point.getY()), styleProvider).draw(g2, transformer);
            }
            
            old = point;
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        LinkedList<Double> x = new LinkedList<Double>();
        LinkedList<Double> y = new LinkedList<Double>();
        
        for (CirclePoint p : window) {
            x.add(p.getX());
            y.add(p.getY());
        }
        
        double minX = MathUtil.getMin(x);
        double maxX = MathUtil.getMax(x);
        double minY = MathUtil.getMin(y);
        double maxY = MathUtil.getMax(y);
        
        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        Point2D.Double point = new Point2D.Double();
        Set<StylePropertyKey<?>> properties = new DrawableLine(point, point, styleProvider)
                .getNecessaryStyleProperties();
        properties.addAll(new CirclePoint(0, 0, styleProvider).getNecessaryStyleProperties());
        return properties;
    }
}
