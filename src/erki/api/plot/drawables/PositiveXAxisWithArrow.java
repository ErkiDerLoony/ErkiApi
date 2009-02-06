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
import erki.api.util.MathUtil;

public class PositiveXAxisWithArrow implements Drawable {
    
    private static final BasicStroke STROKE = new BasicStroke(1.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    
    /** Offset of the axis' arrow to the right border of the plot panel. */
    private static final int OFFSET = 7;
    
    private static final Font TICK_LABEL_FONT = new Font(Font.SANS_SERIF,
            Font.PLAIN, 10);
    
    private static final int TICK_LENGTH = 5;
    
    private NumberFormat nf;
    
    private boolean tickLabels;
    
    // private double height = 0.0;
    
    public PositiveXAxisWithArrow(boolean ticks, boolean tickLabels) {
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        this.tickLabels = tickLabels;
    }
    
    public PositiveXAxisWithArrow(boolean tickLabels) {
        this(true, tickLabels);
    }
    
    public PositiveXAxisWithArrow() {
        this(true, true);
    }
    
    @Override
    public void draw(Graphics2D g2, CoordinateTransformer transformer) {
        Stroke oldStroke = g2.getStroke();
        Color oldColour = g2.getColor();
        Font oldFont = g2.getFont();
        
        g2.setColor(Color.BLACK);
        g2.setStroke(STROKE);
        
        // Draw the actual axis
        Point origin = transformer.getScreenCoordinates(new Point2D.Double(0.0,
                0.0));
        g2.drawLine(origin.x, origin.y, transformer.getScreenWidth() - OFFSET,
                origin.y);
        
        // Draw the arrow at the end
        g2.drawLine(transformer.getScreenWidth() - OFFSET, origin.y,
                transformer.getScreenWidth() - 4 * OFFSET, origin.y - OFFSET);
        g2.drawLine(transformer.getScreenWidth() - OFFSET, origin.y,
                transformer.getScreenWidth() - 4 * OFFSET, origin.y + OFFSET);
        
        // Draw the ticks
        int step = g2.getFontMetrics().stringWidth(
                nf.format(((int) (transformer.getCartMaxX() - transformer
                        .getCartMinX())) + 0.111));
        g2.setFont(TICK_LABEL_FONT);
        
        for (int i = origin.x; i < transformer.getScreenWidth() - 5 * OFFSET; i += step) {
            int x = i;
            int y = origin.y;
            g2.drawLine(x, y, x, y + TICK_LENGTH);
            // height = -transformer.getCarthesianCoordinates(
            // new Point(0, y + Math.max(TICK_LENGTH + 3, OFFSET + 3)))
            // .getY();
            String tick = nf.format(MathUtil.round(transformer
                    .getCarthesianCoordinates(new Point(x, y)).getX(), 3));
            
            if (tickLabels) {
                g2.drawString(tick, (int) (x - 0.5 * g2.getFontMetrics()
                        .stringWidth(tick)), y + TICK_LENGTH + 3
                        + g2.getFontMetrics().getHeight());
                // height = -transformer.getCarthesianCoordinates(
                // new Point(0, y + TICK_LENGTH + 3
                // + g2.getFontMetrics().getHeight()
                // + g2.getFontMetrics().getDescent())).getY();
            }
        }
        
        g2.setStroke(oldStroke);
        g2.setColor(oldColour);
        g2.setFont(oldFont);
    }
    
    @Override
    public Rectangle2D.Double getBounds() {
        // The axis should have no influence on autorange etc.
        return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
    }
}
