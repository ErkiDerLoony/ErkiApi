/*
 * © Copyright 2007–2010 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Plot2d;

/**
 * Interface for all external drawers that can be added to a {@link Plot2d}.
 * 
 * @author Edgar Kalkowski
 */
public interface Drawable {
    
    /**
     * Draw the fancy stuff of this instance to the {@link Graphics2D} object handed in as parameter
     * using a transformation for the coordinates before actually drawing so they fit the jfreechart
     * coordinates.
     * 
     * @param g2
     *        The graphics context to draw upon.
     * @param transformer
     *        The {@link CoordinateTransformer} to be used to transform the coordinates from Java’s
     *        coordinate system to JFreeChart’s coordinates.
     */
    public void draw(Graphics2D g2, CoordinateTransformer transformer);
    
    /**
     * Access the rectangular shape of this drawer’s content. This rectangle is used to calculate
     * the ranges of the coordinate axes. The returned rectangle need not necessarily describe the
     * actual extends of the object the drawer draws (as those might also be infinity) but rather
     * the extends that shall naturally be displayed in an auto-ranged plot.
     * 
     * @return A rectangle that describes the bounds of the thing this drawer draws or {@code null}
     *         if this drawer does not actually draw anything.
     */
    public Rectangle2D.Double getBounds();
    
}
