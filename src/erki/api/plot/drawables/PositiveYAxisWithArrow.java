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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.LinkedList;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;
import erki.api.util.MathUtil;

public class PositiveYAxisWithArrow implements Drawable {
    
    private boolean tickLabels;
    
    public PositiveYAxisWithArrow(boolean ticks, boolean tickLabels) {
        this.tickLabels = tickLabels;
    }
    
    public PositiveYAxisWithArrow(boolean tickLabels) {
        this(true, tickLabels);
    }
    
    public PositiveYAxisWithArrow() {
        this(true, true);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider) {
        Stroke oldStroke = g2.getStroke();
        Color oldColour = g2.getColor();
        Font oldFont = g2.getFont();
        
        g2.setColor(Color.BLACK);
        g2.setStroke(styleProvider.getProperty(
                new StylePropertyKey<Stroke>("AXES_STROKE")).getProperty());
        int offset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_OFFSET")).getProperty();
        
        // Draw the actual axis
        Point origin = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                0.0));
        g2.drawLine(origin.x, origin.y, origin.x, offset);
        
        // Draw the arrow at the end
        g2.drawLine(origin.x, offset, origin.x - offset, 4 * offset);
        g2.drawLine(origin.x, offset, origin.x + offset, 4 * offset);
        
        // Draw the ticks
        int tickLength = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_TICK_LENGTH"))
                .getProperty();
        NumberFormat nf = styleProvider.getProperty(
                new StylePropertyKey<NumberFormat>("AXES_TICK_NUMBER_FORMAT"))
                .getProperty();
        int step = g2.getFontMetrics().stringWidth(
                nf.format(((int) (transformer.getCartMaxX() - transformer
                        .getCartMinX())) + 0.111));
        g2.setFont(styleProvider.getProperty(
                new StylePropertyKey<Font>("AXES_TICK_FONT")).getProperty());
        
        for (int i = origin.y; i > 5 * offset; i -= step) {
            int x = origin.x;
            int y = i;
            g2.drawLine(x, y, x - tickLength, y);
            String tick = nf.format(MathUtil.round(transformer
                    .getCarthesianCoordinates(new Point(x, y)).getY(), 3));
            
            if (tickLabels) {
                g2.drawString(tick, origin.x - step, (int) (y + 0.5
                        * g2.getFontMetrics().getHeight() - g2.getFontMetrics()
                        .getDescent()));
            }
        }
        
        g2.setStroke(oldStroke);
        g2.setColor(oldColour);
        g2.setFont(oldFont);
    }
    
    @Override
    public Collection<StylePropertyKey<?>> getNecessaryStyleProperties() {
        LinkedList<StylePropertyKey<?>> properties = new LinkedList<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("AXES_STROKE"));
        properties.add(new StylePropertyKey<Font>("AXES_TICK_FONT"));
        properties.add(new StylePropertyKey<Integer>("AXES_TICK_LENGTH"));
        properties.add(new StylePropertyKey<NumberFormat>(
                "AXES_TICK_NUMBER_FORMAT"));
        properties.add(new StylePropertyKey<Integer>("AXES_OFFSET", "Offset "
                + "of the coordinate axes from the corner of the plot"));
        return properties;
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        // The axis should have no influence on autorange etc.
        return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
    }
}
