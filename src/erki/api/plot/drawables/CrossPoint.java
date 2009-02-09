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
import java.util.Collection;
import java.util.LinkedList;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

public class CrossPoint extends Point2D.Double implements Drawable {
    
    private static final long serialVersionUID = -7603800844085258749L;
    
    private Color colour;
    
    public CrossPoint(double x, double y, Color colour) {
        super(x, y);
        this.colour = colour;
    }
    
    public CrossPoint(double x, double y) {
        this(x, y, Color.BLACK);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider) {
        Color oldColour = g2.getColor();
        
        Point p = transformer.getScreenCoordinates(this);
        int size = (int) (styleProvider.getProperty(
                new StylePropertyKey<Integer>("POINT_SIZE")).getProperty() / 2.0);
        
        g2.setColor(colour);
        g2.drawLine(p.x - size, p.y - size, p.x + size, p.y + size);
        g2.drawLine(p.x - size, p.y + size, p.x + size, p.y - size);
        
        g2.setColor(oldColour);
    }
    
    @Override
    public Collection<StylePropertyKey<?>> getNecessaryStyleProperties() {
        LinkedList<StylePropertyKey<?>> properties = new LinkedList<StylePropertyKey<?>>();
        return properties;
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(getX(), getY(), 0.0, 0.0);
    }
}
