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

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Plot2D;

/**
 * Implemented by every class that can be drawn on a {@link Plot2D}.
 * 
 * @author Edgar Kalkowski
 */
public interface Drawable {
    
    /**
     * This method is called by {@link Plot2D} if this {@code Drawable} shall
     * redraw itself to the submitted graphics context.
     * 
     * @param g2
     *        The graphics context this drawable shall draw itself on.
     * @param transformer
     *        The {@code CoordinateTransformer} that shall be used for
     *        coordinate transformation from carthesian coordinates to the
     *        screen's pixel coordinates before drawing.
     */
    void draw(Graphics2D g2, CoordinateTransformer transformer);
    
    /**
     * @return A {@code Rectangle2D.Double} that specifies the bounds of this
     *         drawable object in carthesian coordinate space or {@code null} if
     *         this drawable object has no bounds.
     */
    Rectangle2D.Double getBounds();
    
}
