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

package erki.api.plot.ba;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.drawables.Drawable;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

public class MixtureInfluenceFunction implements Drawable {
    
    private double o;
    
    public MixtureInfluenceFunction(double o) {
        this.o = o;
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        
        Point p1 = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                f(0.0)));
        Point p2 = transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o)));
        Point p3 = transformer.getScreenCoordinates(new Point2D.Double(1.0,
                f(1.0)));
        
        g2.setColor(Color.RED);
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("LINE_STROKE")).getProperty());
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2.drawLine(p2.x, p2.y, p3.x, p3.y);
        
        Line2D.Double l1 = new Line2D.Double(transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o))), transformer
                .getScreenCoordinates(new Point2D.Double(o, 0.0)));
        Line2D.Double l2 = new Line2D.Double(transformer
                .getScreenCoordinates(new Point2D.Double(o, f(o))), transformer
                .getScreenCoordinates(new Point2D.Double(0.0, f(o))));
        
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("ALTERNATE_LINE_STROKE"))
                .getProperty());
        g2.draw(l1);
        g2.draw(l2);
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
    }
    
    private double f(double x) {
        
        if (x <= o) {
            return x / (2.0 * o);
        } else {
            return x / (2.0 * (1.0 - o)) + 0.5 - o / (2 * (1 - o));
        }
    }
    
    @Override
    public Collection<StylePropertyKey<?>> getNecessaryStyleProperties() {
        LinkedList<StylePropertyKey<?>> properties = new LinkedList<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("LINE_STROKE"));
        properties.add(new StylePropertyKey<Stroke>("ALTERNATE_LINE_STROKE"));
        return properties;
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
    }
}
