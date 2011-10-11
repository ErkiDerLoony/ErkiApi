/*
 * © Copyright 2007–2011 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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

public class CirclePoint extends StyledDrawable {
    
    private double x, y;
    
    public CirclePoint(double x, double y, StyleProvider styleProvider) {
        super(styleProvider);
        this.x = x;
        this.y = y;
    }
    
    public CirclePoint(Point2D.Double point, StyleProvider styleProvider) {
        this(point.getX(), point.getY(), styleProvider);
    }
    
    public Point2D.Double get() {
        return new Point2D.Double(x, y);
    }
    
    public void set(Point2D.Double p) {
        x = p.x;
        y = p.y;
    }
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point p = transformer.getScreenCoordinates(get());
        int size = styleProvider.getProperty(new StylePropertyKey<Integer>("POINT_SIZE"))
                .getProperty();
        
        g2.setStroke(styleProvider.getProperty(new StylePropertyKey<Stroke>("POINT_STROKE"))
                .getProperty());
        g2.setColor(styleProvider.getProperty(new StylePropertyKey<Color>("POINT_COLOR"))
                .getProperty());
        g2.drawArc(p.x - (int) (0.5 * size), p.y - (int) (0.5 * size), size, size, 0, 360);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        TreeSet<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("POINT_STROKE"));
        properties.add(new StylePropertyKey<Integer>("POINT_SIZE"));
        properties.add(new StylePropertyKey<Color>("POINT_COLOR"));
        return properties;
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(getX(), getY(), 0.0, 0.0);
    }
}
