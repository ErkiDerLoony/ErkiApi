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
import java.util.Set;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Plot2D;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

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
     * @param styleProvider
     *        The {@code StyleProvider} that provides for all needed styles for
     *        drawing as specified via {@link #getNecessaryStyleProperties()}.
     */
    void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider);
    
    /**
     * @return A {@code Rectangle2D.Double} that specifies the bounds of this
     *         drawable object in carthesian coordinate space or {@code null} if
     *         this drawable object has no bounds.
     */
    Rectangle2D.Double getBounds();
    
    /**
     * Used by {@link Plot2D} to check if the current {@link StyleProvider} can
     * actually provide for all the needed style properties for this drawable
     * object. No style property that is not contained in the returned
     * collection may be requested from the {@code StyleProvider} in the
     * {@link #draw(Graphics2D, CoordinateTransformer, StyleProvider)} method.
     * <p>
     * The {@code StylePropertyKey}s contained in the returned {@code Set} are
     * used for error messages if any necessary style property cannot be
     * provided by the current {@code StyleProvider} an hence it might be useful
     * if those {@code StylePropertyKey}s contained a description (see
     * {@link StylePropertyKey#StylePropertyKey(String, String)}).
     * 
     * @return A {@link Set} of {@link StylePropertyKey}s that contains all the
     *         needed style properties of this drawable object or {@code null}
     *         if no styles are needed.
     */
    Set<StylePropertyKey<?>> getNecessaryStyleProperties();
    
}
