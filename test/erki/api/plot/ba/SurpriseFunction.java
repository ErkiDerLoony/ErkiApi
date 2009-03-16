/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under the terms of the GNU
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

package erki.api.plot.ba;

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

public class SurpriseFunction extends StyledDrawable {
    
    private final double umin, umax;
    
    public SurpriseFunction(double umin, double umax, StyleProvider styleProvider) {
        super(styleProvider);
        this.umin = umin;
        this.umax = umax;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        g2.setColor(Color.RED);
        g2.setStroke(styleProvider.getProperty(new StylePropertyKey<Stroke>("LINE_STROKE"))
                .getProperty());
        
        Point p = transformer.getScreenCoordinates(new Point2D.Double(transformer.getCartMinX(),
                0.0));
        Point q = transformer.getScreenCoordinates(new Point2D.Double(umin, 0.0));
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        p = transformer.getScreenCoordinates(new Point2D.Double(umax, 1.0));
        g2.drawLine(q.x, q.y, p.x, p.y);
        
        q = transformer.getScreenCoordinates(new Point2D.Double(transformer.getCartMaxX(), 1.0));
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("ALTERNATE_LINE_STROKE")).getProperty());
        
        p = transformer.getScreenCoordinates(new Point2D.Double(umax, 0.0));
        q = transformer.getScreenCoordinates(new Point2D.Double(umax, 1.0));
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        p = transformer.getScreenCoordinates(new Point2D.Double(0.0, 1.0));
        q = transformer.getScreenCoordinates(new Point2D.Double(umax, 1.0));
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        Set<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("LINE_STROKE"));
        properties.add(new StylePropertyKey<Stroke>("ALTERNATE_LINE_STROKE"));
        return properties;
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 5.0, 1.0);
    }
}
