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

public class RewardFactorFunction implements Drawable {
    
    private double o;
    private double rmin;
    private double rmax;
    
    public RewardFactorFunction(double o, double rmin, double rmax) {
        this.o = o;
        this.rmin = rmin;
        this.rmax = rmax;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point p1 = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                f(0.0)));
        Point p2 = transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o)));
        Point p3 = transformer.getScreenCoordinates(new Point2D.Double(1.0,
                f(1.0)));
        
        g2.setColor(Color.RED);
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2.drawLine(p2.x, p2.y, p3.x, p3.y);
        
        Point t = transformer.getScreenCoordinates(new Point2D.Double(o, f(o)));
        Point t1 = transformer.getScreenCoordinates(new Point2D.Double(o, 0.0));
        Point t2 = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                f(o)));
        
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f, 10.0f },
                0.0f));
        g2.drawLine(t.x, t.y, t1.x, t1.y);
        g2.drawLine(t.x, t.y, t2.x, t2.y);
        
        g2.setStroke(oldStroke);
        g2.setColor(oldColour);
    }
    
    private double f(double x) {
        double dy = 0.5 * (rmax + rmin) - rmax;
        
        if (x <= o) {
            return -(x * dy) / o + rmin;
        } else {
            return -(x * dy) / (1 - o) + 0.5 * (rmax + rmin) + (dy * o)
                    / (1 - o);
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, rmin, 1.0, 3.0 * (rmax - rmin));
    }
}
