/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.util.TreeSet;

import erki.api.plot.CoordinateAxis;
import erki.api.plot.CoordinateTransformer;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;
import erki.api.util.MathUtil;

/**
 * These axes are always visible as a box on the edge of the drawing area.
 * 
 * @author Edgar Kalkowski
 */
public class BoxAxes extends StyledDrawable implements CoordinateAxis {
    
    // Step size of the axes’ ticks.
    private static final double[] steps = { 0.00001, 0.000025, 0.00005, 0.0001, 0.00025, 0.0005,
            0.001, 0.0025, 0.005, 0.01, 0.025, 0.05, 0.1, 0.25, 0.5, 1.0, 2.5, 5.0, 10.0, 25.0,
            50.0, 100.0, 250.0, 500.0, 1000.0, 2500.0, 5000.0, 10000.0 };
    
    // Border between axes’ tick labels and the edge of the plot.
    private static final int BORDER = 3;
    
    private int leftOffset = 0, bottomOffset = 0;
    
    public BoxAxes(StyleProvider styleProvider) {
        super(styleProvider);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Stroke oldStroke = g2.getStroke();
        Color oldColour = g2.getColor();
        Font oldFont = g2.getFont();
        
        g2.setStroke(styleProvider.getProperty(new StylePropertyKey<Stroke>("AXES_STROKE"))
                .getProperty());
        g2.setColor(styleProvider.getProperty(new StylePropertyKey<Color>("AXES_COLOR"))
                .getProperty());
        g2.setFont(styleProvider.getProperty(new StylePropertyKey<Font>("AXES_TICK_FONT"))
                .getProperty());
        
        // Estimate tick steps
        int xStep = 0, yStep = 0;
        boolean ticksFit = true;
        
        int maxWidth = 0;
        
        do {
            int oldX = transformer.getScreenCoordinates(new Point2D.Double(MathUtil.round(
                    transformer.getCarthesianCoordinates(new Point(BORDER, BORDER)).getX(),
                    steps[xStep]), 0.0)).x;
            ticksFit = true;
            
            for (double i = MathUtil.round(transformer.getCarthesianCoordinates(
                    new Point(BORDER, BORDER)).getX(), steps[xStep])
                    + steps[xStep]; i < transformer.getCartMaxX(); i += steps[xStep]) {
                Point p = transformer.getScreenCoordinates(new Point2D.Double(i, 0.0));
                String tick = MathUtil.round(i, 7) + "";
                
                if (tick.endsWith(".0")) {
                    tick = tick.substring(0, tick.length() - 2);
                }
                
                if (p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) < oldX) {
                    ticksFit = false;
                    xStep++;
                    break;
                } else {
                    oldX = p.x + g2.getFontMetrics().stringWidth(tick);
                }
            }
            
        } while (xStep < steps.length - 1 && !ticksFit);
        
        int tickOffset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_TICK_OFFSET")).getProperty();
        int boxOffset = styleProvider.getProperty(
                new StylePropertyKey<Integer>("AXES_ARROW_OFFSET")).getProperty();
        
        do {
            Point2D.Double j = new Point2D.Double(0.0, MathUtil.round(transformer
                    .getCarthesianCoordinates(new Point(BORDER, 2 * boxOffset)).getY(),
                    steps[yStep]));
            int oldY = transformer.getScreenCoordinates(j).y;
            
            // Do not consider the tick if it’s not actually draw later because it is hidden by the
            // toparrow of the y-axis.
            int counter = 1;
            while (oldY + 0.5 * g2.getFontMetrics().getHeight() < 2 * boxOffset) {
                oldY = transformer.getScreenCoordinates(new Point2D.Double(0.0, MathUtil.round(
                        transformer.getCarthesianCoordinates(new Point(BORDER, 2 * boxOffset))
                                .getY(), steps[yStep])
                        - counter++ * steps[yStep])).y;
            }
            
            ticksFit = true;
            String tick = MathUtil.round(j.getY(), 7) + "";
            
            if (tick.endsWith(".0")) {
                tick = tick.substring(0, tick.length() - 2);
            }
            
            maxWidth = g2.getFontMetrics().stringWidth(tick);
            
            for (double i = MathUtil.round(transformer.getCarthesianCoordinates(
                    new Point(BORDER, 2 * boxOffset)).getY(), steps[yStep])
                    - steps[yStep]; i > transformer.getCartMinY(); i -= steps[yStep]) {
                Point p = transformer.getScreenCoordinates(new Point2D.Double(0.0, i));
                
                /*
                 * Do not consider ticks that are not actually drawn later because they are hidden
                 * by the x-axis. This is only possible for one axis because one has to know the
                 * extends of the other axis to know which ticks are actually drawn later. Because
                 * for the y-axis this has a greater visual impact I chose to first estimate the
                 * ticks for the x-axis and use this to better estimate the y-axis.
                 */
                if (p.y > transformer.getScreenHeight() - BORDER - tickOffset
                        - g2.getFontMetrics().getHeight()) {
                    continue;
                }
                
                tick = MathUtil.round(i, 7) + "";
                
                if (tick.endsWith(".0")) {
                    tick = tick.substring(0, tick.length() - 2);
                }
                
                if (g2.getFontMetrics().stringWidth(tick) > maxWidth) {
                    maxWidth = g2.getFontMetrics().stringWidth(tick);
                }
                
                if (p.y - 0.5 * g2.getFontMetrics().getHeight() < oldY) {
                    ticksFit = false;
                    yStep++;
                    break;
                } else {
                    oldY = p.y + g2.getFontMetrics().getHeight();
                }
            }
            
        } while (yStep < steps.length - 1 && !ticksFit);
        
        Point origin = new Point(BORDER + 2 * tickOffset + maxWidth, transformer.getScreenHeight()
                - BORDER - tickOffset - g2.getFontMetrics().getHeight());
        leftOffset = BORDER + 2 * tickOffset + maxWidth;
        bottomOffset = BORDER + tickOffset + g2.getFontMetrics().getHeight();
        
        // Draw axes
        g2.drawLine(origin.x, origin.y, transformer.getScreenWidth() - 2 * boxOffset, origin.y);
        g2.drawLine(origin.x, 2 * boxOffset, origin.x, origin.y);
        g2.drawLine(transformer.getScreenWidth() - 2 * boxOffset, origin.y, transformer
                .getScreenWidth()
                - 2 * boxOffset, 2 * boxOffset);
        g2.drawLine(origin.x, 2 * boxOffset, transformer.getScreenWidth() - 2 * boxOffset,
                2 * boxOffset);
        
        // Actually draw the ticks
        for (double i = MathUtil.round(transformer.getCarthesianCoordinates(origin).getX(),
                steps[xStep]); i < transformer.getCartMaxX(); i += steps[xStep]) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(i, transformer
                    .getCarthesianCoordinates(origin).getY()));
            String tick = MathUtil.round(i, 7) + "";
            
            if (tick.endsWith(".0")) {
                tick = tick.substring(0, tick.length() - 2);
            }
            
            if (p.x + 0.5 * g2.getFontMetrics().stringWidth(tick) < transformer.getScreenWidth()
                    - BORDER
                    && p.x < transformer.getScreenWidth() - 2 * boxOffset
                    && p.x > origin.x
                    && p.x - 0.5 * g2.getFontMetrics().stringWidth(tick) > BORDER) {
                g2.setColor(styleProvider.getProperty(
                        new StylePropertyKey<Color>("AXES_FAINT_COLOR")).getProperty());
                g2.drawLine(p.x, p.y, p.x, 2 * boxOffset);
                g2.setColor(styleProvider.getProperty(new StylePropertyKey<Color>("AXES_COLOR"))
                        .getProperty());
                g2.drawLine(p.x, p.y - tickOffset, p.x, p.y + tickOffset);
                g2.drawLine(p.x, 2 * boxOffset - tickOffset, p.x, 2 * boxOffset + tickOffset);
                g2.drawString(tick, (int) (p.x - 0.5 * g2.getFontMetrics().stringWidth(tick)), p.y
                        + g2.getFontMetrics().getHeight() + tickOffset);
            }
        }
        
        for (double i = MathUtil.round(transformer.getCarthesianCoordinates(origin).getY(),
                steps[yStep]); i < transformer.getCartMaxY(); i += steps[yStep]) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(transformer
                    .getCarthesianCoordinates(origin).getX(), i));
            String tick = MathUtil.round(i, 7) + "";
            
            if (tick.endsWith(".0")) {
                tick = tick.substring(0, tick.length() - 2);
            }
            
            if (p.y - 0.5 * g2.getFontMetrics().getHeight() > BORDER && p.y > 2 * boxOffset
                    && p.y < origin.y) {
                g2.setColor(styleProvider.getProperty(
                        new StylePropertyKey<Color>("AXES_FAINT_COLOR")).getProperty());
                g2.drawLine(p.x, p.y, transformer.getScreenWidth() - 2 * boxOffset, p.y);
                g2.setColor(styleProvider.getProperty(new StylePropertyKey<Color>("AXES_COLOR"))
                        .getProperty());
                g2.drawLine(p.x - tickOffset, p.y, p.x + tickOffset, p.y);
                g2.drawLine(transformer.getScreenWidth() - 2 * boxOffset - tickOffset, p.y,
                        transformer.getScreenWidth() - 2 * boxOffset + tickOffset, p.y);
                g2.drawString(tick, p.x - 2 * tickOffset - maxWidth, (int) (p.y + 0.5
                        * g2.getFontMetrics().getHeight() - g2.getFontMetrics().getDescent()));
            }
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
        g2.setFont(oldFont);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
    
    @Override
    public Set<StylePropertyKey<?>> getNecessaryStyleProperties() {
        TreeSet<StylePropertyKey<?>> properties = new TreeSet<StylePropertyKey<?>>();
        properties.add(new StylePropertyKey<Stroke>("AXES_STROKE"));
        properties.add(new StylePropertyKey<Color>("AXES_COLOR"));
        properties.add(new StylePropertyKey<Color>("AXES_FAINT_COLOR"));
        properties.add(new StylePropertyKey<Integer>("AXES_TICK_OFFSET"));
        properties.add(new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"));
        properties.add(new StylePropertyKey<Font>("AXES_TICK_FONT"));
        return properties;
    }
    
    @Override
    public int getBottomOffset() {
        return bottomOffset;
    }
    
    @Override
    public int getLeftOffset() {
        return leftOffset;
    }
    
    @Override
    public int getRightOffset() {
        return 2 * styleProvider.getProperty(new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"))
                .getProperty();
    }
    
    @Override
    public int getTopOffset() {
        return 2 * styleProvider.getProperty(new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"))
                .getProperty();
    }
}
