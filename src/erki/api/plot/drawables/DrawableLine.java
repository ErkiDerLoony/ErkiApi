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
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.util.TreeSet;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

public class DrawableLine extends StyledDrawable {
    
    private static final long serialVersionUID = 6844062407525196699L;
    
    private Point2D.Double start;
    private Point2D.Double end;
    
    private Color colour;
    
    public DrawableLine(Point2D.Double start, Point2D.Double end, Color colour, StyleProvider styleProvider) {
        super(styleProvider);
        this.start = start;
        this.end = end;
        this.colour = colour;
    }
    
    public DrawableLine(Point2D.Double start, Point2D.Double end, StyleProvider styleProvider) {
        this(start, end, Color.BLACK, styleProvider);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point p = transformer.getScreenCoordinates(start);
        Point q = transformer.getScreenCoordinates(end);
        
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("LINE_STROKE")).getProperty());
        g2.setColor(colour);
        g2.drawLine(p.x, p.y, q.x, q.y);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        Set<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("LINE_STROKE"));
        return properties;
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(Math.min(start.getX(), end.getX()), Math
                .min(start.getY(), end.getY()), Math.abs(start.getX()
                - end.getX()), Math.abs(start.getY() - end.getY()));
    }
}
