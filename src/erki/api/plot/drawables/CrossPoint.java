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

package erki.api.plot.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Drawable;

public class CrossPoint extends Point2D.Double implements Drawable {
    
    private static final long serialVersionUID = -7603800844085258749L;
    
    private static final int OFFSET = 5;
    
    private Color colour;
    
    public CrossPoint(double x, double y, Color colour) {
        super(x, y);
        this.colour = colour;
    }
    
    public CrossPoint(double x, double y) {
        this(x, y, Color.BLACK);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        
        Point p = transformer.getScreenCoordinates(this);
        g2.setColor(colour);
        g2.drawLine(p.x - OFFSET, p.y - OFFSET, p.x + OFFSET, p.y + OFFSET);
        g2.drawLine(p.x - OFFSET, p.y + OFFSET, p.x + OFFSET, p.y - OFFSET);
        
        g2.setColor(oldColour);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(getX(), getY(), 0.0, 0.0);
    }
}
