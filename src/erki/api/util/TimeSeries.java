package erki.api.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.TreeMap;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.drawables.Drawable;

public class TimeSeries implements Drawable {
    
    private TreeMap<Double, Double> mapping = new TreeMap<Double, Double>();
    
    private Color lineColour;
    
    private double min, max, mean, std;
    
    private boolean calcMeasures;
    
    public TimeSeries(Color lineColour, boolean calcMeasures) {
        this.lineColour = lineColour;
        this.calcMeasures = calcMeasures;
    }
    
    public void add(double x, double y) {
        
        if (mapping.containsKey(x)) {
            Log.warning("Mapping already contains key " + x + " (overwriting)!");
        }
        
        mapping.put(x, y);
        
        if (calcMeasures) {
            recalculateMeasures();
        }
    }
    
    private void recalculateMeasures() {
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE, sum = 0.0;
        
        for (double x : mapping.keySet()) {
            double y = mapping.get(x);
            sum += y;
            
            if (y < min) {
                min = y;
            }
            
            if (y > max) {
                max = y;
            }
        }
        
        this.min = min;
        this.max = max;
        mean = sum / mapping.keySet().size();
        sum = 0.0;
        
        for (double x : mapping.keySet()) {
            double y = mapping.get(x);
            sum += Math.pow(y - mean, 2.0);
        }
        
        std = Math.sqrt(sum / (mapping.keySet().size() - 1));
    }
    
    public double getMin() {
        return min;
    }
    
    public double getMax() {
        return max;
    }
    
    public double getMean() {
        return mean;
    }
    
    public double getStd() {
        return std;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point old = null;
        
        for (double x : mapping.keySet()) {
            double y = mapping.get(x);
            
            Point p = transformer.getScreenCoordinates(new Point2D.Double(x, y));
            
            if (old != null) {
                g2.setColor(lineColour);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(old.x, old.y, p.x, p.y);
            }
            
            old = p;
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        
        for (double x : mapping.keySet()) {
            double y = mapping.get(x);
            
            if (x < minX) {
                minX = x;
            }
            
            if (x > maxX) {
                maxX = x;
            }
            
            if (y < minY) {
                minY = y;
            }
            
            if (y > maxY) {
                maxY = y;
            }
        }
        
        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }
}
