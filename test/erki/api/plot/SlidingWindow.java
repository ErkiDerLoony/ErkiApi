/*
 * © Copyright 2007–2010 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
 * 
 * This file is part of Erki’s API.
 * 
 * Erki’s API is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.plot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import erki.api.plot.drawables.ColouredCirclePoint;
import erki.api.plot.drawables.ColouredLine;
import erki.api.plot.drawables.Drawable;
import erki.api.util.MathUtil;

public class SlidingWindow implements Drawable {
    
    private Queue<ColouredCirclePoint> window = new ConcurrentLinkedQueue<ColouredCirclePoint>();
    
    private int size;
    
    public SlidingWindow(int size) {
        this.size = size;
    }
    
    public void add(ColouredCirclePoint point) {
        window.offer(point);
        
        if (window.size() > size) {
            window.poll();
        }
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        ColouredCirclePoint old = null;
        
        for (ColouredCirclePoint point : window) {
            point.draw(g2, transformer);
            
            if (old != null) {
                new ColouredLine(new Point2D.Double(old.getX(), old.getY()), new Point2D.Double(
                        point.getX(), point.getY()), Color.BLACK).draw(g2, transformer);
            }
            
            old = point;
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        
        if (window.isEmpty()) {
            return null;
        } else {
            LinkedList<Double> x = new LinkedList<Double>();
            LinkedList<Double> y = new LinkedList<Double>();
            
            for (ColouredCirclePoint p : window) {
                x.add(p.getX());
                y.add(p.getY());
            }
            
            double minX = MathUtil.getMin(x);
            double maxX = MathUtil.getMax(x);
            double minY = MathUtil.getMin(y);
            double maxY = MathUtil.getMax(y);
            
            return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
        }
    }
}
