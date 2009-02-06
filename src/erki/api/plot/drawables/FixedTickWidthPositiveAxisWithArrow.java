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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Drawable;

public class FixedTickWidthPositiveAxisWithArrow implements Drawable {
    
    /** Offset in pixels to the border of the plot. */
    private static final int OFFSET = 5;
    
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    
    private double tickWidthX, tickWidthY;
    
    private NumberFormat nf;
    
    public FixedTickWidthPositiveAxisWithArrow(double tickWidthX,
            double tickWidthY) {
        this.tickWidthX = tickWidthX;
        this.tickWidthY = tickWidthY;
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Color oldColour = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        Font oldFont = g2.getFont();
        
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.25f));
        g2.setFont(FONT);
        
        // Draw x axis with arrow
        Point origin = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                0.0));
        g2.drawLine(origin.x, origin.y, transformer.getScreenWidth() - OFFSET,
                origin.y);
        g2.drawLine(transformer.getScreenWidth() - OFFSET, origin.y,
                transformer.getScreenWidth() - 4 * OFFSET, origin.y - OFFSET);
        g2.drawLine(transformer.getScreenWidth() - OFFSET, origin.y,
                transformer.getScreenWidth() - 4 * OFFSET, origin.y + OFFSET);
        
        // Draw horizontal ticks and labels
        for (double i = 0.0; i < transformer.getCartMaxX() - tickWidthX; i += tickWidthX) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(i,
                    0.0));
            g2.drawLine(p.x, p.y, p.x, p.y + OFFSET);
            String tick = nf.format(i);
            g2.drawString(tick, (float) (p.x - 0.5 * g2.getFontMetrics()
                    .stringWidth(tick)), (float) (p.y + OFFSET + g2
                    .getFontMetrics().getHeight()));
        }
        
        // Draw y axis with arrow
        g2.drawLine(origin.x, origin.y, origin.x, OFFSET);
        g2.drawLine(origin.x, OFFSET, origin.x - OFFSET, 4 * OFFSET);
        g2.drawLine(origin.x, OFFSET, origin.x + OFFSET, 4 * OFFSET);
        
        // Draw vertical ticks and labels
        for (double i = 0.0; i < transformer.getCartMaxY() - tickWidthY; i += tickWidthY) {
            Point p = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                    i));
            g2.drawLine(p.x, p.y, p.x - OFFSET, p.y);
            String tick = nf.format(i);
            g2.drawString(tick, (float) (p.x - OFFSET - 3 - g2.getFontMetrics()
                    .stringWidth(tickWidthY + "")), (float) (p.y + 0.5
                    * g2.getFontMetrics().getHeight() - g2.getFontMetrics()
                    .getDescent()));
        }
        
        g2.setColor(oldColour);
        g2.setStroke(oldStroke);
        g2.setFont(oldFont);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(0.0, 0.0, 1.5 * tickWidthX,
                1.5 * tickWidthY);
    }
}
