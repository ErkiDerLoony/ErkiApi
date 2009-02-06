/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class TanhScalingFunction implements Drawable {
    
    private final double dmin = 0.1;
    private final double dmax = 1.46;
    private final double tmin = 0.001;
    private final double tmax = 0.999;
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point2D.Double oldPoint = null;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(1.25f));
        
        for (int i = 0; i < transformer.getScreenWidth(); i++) {
            Point2D.Double cart = transformer
                    .getCarthesianCoordinates(new Point(i, 0));
            
            if (cart.getX() >= 0.0) {
                
                if (oldPoint == null) {
                    oldPoint = new Point2D.Double(cart.getX(), f(cart.getX()));
                } else {
                    Point2D.Double newPoint = new Point2D.Double(cart.getX(),
                            f(cart.getX()));
                    
                    Point p = transformer.getScreenCoordinates(oldPoint);
                    Point q = transformer.getScreenCoordinates(newPoint);
                    g2.drawLine(p.x, p.y, q.x, q.y);
                    
                    oldPoint = newPoint;
                }
            }
        }
        
        g2
                .setStroke(new BasicStroke(1.25f, BasicStroke.CAP_SQUARE,
                        BasicStroke.JOIN_MITER, 10.0f,
                        new float[] { 5.0f, 5.0f }, 0.0f));
        
        // Point p = transformer.getScreenCoordinates(new Point2D.Double(dmin,
        // f(dmin)));
        // Point q = transformer
        // .getScreenCoordinates(new Point2D.Double(dmin, 0.0));
        // g2.drawLine(p.x, p.y, q.x, q.y);
        //        
        // q = transformer.getScreenCoordinates(new Point2D.Double(0.0,
        // f(dmin)));
        // g2.drawLine(p.x, p.y, q.x, q.y);
        
        Point p = transformer.getScreenCoordinates(new Point2D.Double(dmax,
                f(dmax)));
        Point q = transformer
                .getScreenCoordinates(new Point2D.Double(dmax, 0.0));
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        q = transformer.getScreenCoordinates(new Point2D.Double(0.0, f(dmax)));
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    private double f(double x) {
        double a = (arctanh(tmax / 0.5 - 1) - arctanh(tmin / 0.5 - 1))
                / (dmax - dmin);
        double b = arctanh(tmax / 0.5 - 1) - a * dmax;
        return 0.5 * (Math.tanh(a * x + b) + 1);
    }
    
    private double arctanh(double x) {
        
        if (Math.abs(x) >= 1) {
            x = x % 1.0;
        }
        
        return 0.5 * Math.log((1 + x) / (1 - x));
    }
    
    @Override
    public Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 2, 1.0);
    }
}
