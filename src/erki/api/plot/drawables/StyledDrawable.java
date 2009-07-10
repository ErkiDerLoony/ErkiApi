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

import java.awt.Graphics2D;
import java.util.Set;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.Plot2D;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

/**
 * This abstract class extends the {@link Drawable} interface with an additional
 * {@link StyleProvider} whose properties should be used for drawing this object.
 * 
 * @author Edgar Kalkowski
 */
public abstract class StyledDrawable implements Drawable {
    
    protected StyleProvider styleProvider;
    
    /**
     * Creates a new {@code StyledDrawable} that uses a specific {@code StyleProvider}. If the same
     * {@code StyleProvider} is used for multiple {@code StyledDrawables} they will all be drawn in
     * the same style.
     * 
     * @param styleProvider
     */
    public StyledDrawable(StyleProvider styleProvider) {
        this.styleProvider = styleProvider;
        styleProvider.checkProperties(this);
    }
    
    /**
     * Gives access to the currently used {@code StyleProvider}. This method should not be needed
     * under normal circumstances.
     * 
     * @return The {@code StyleProvider} that is currently used for drawing this object.
     */
    public StyleProvider getStyleProvider() {
        return styleProvider;
    }
    
    /**
     * Change the {@code StyleProvider} that is used for drawing this object.
     * 
     * @param styleProvider
     *        The new {@code StyleProvider}.
     */
    public void setStyleProvider(StyleProvider styleProvider) {
        this.styleProvider = styleProvider;
    }
    
    /**
     * Used by {@link Plot2D} to check if the current {@link StyleProvider} can actually provide for
     * all the needed style properties for this drawable object. No style property that is not
     * contained in the returned collection may be requested from the {@code StyleProvider} in the
     * {@link #draw(Graphics2D, CoordinateTransformer, StyleProvider)} method.
     * <p>
     * The {@code StylePropertyKey}s contained in the returned {@code Set} are used for error
     * messages if any necessary style property cannot be provided by the current {@code
     * StyleProvider} an hence it might be useful if those {@code StylePropertyKey}s contained a
     * description (see {@link StylePropertyKey#StylePropertyKey(String, String)}).
     * 
     * @return A {@link Set} of {@link StylePropertyKey}s that contains all the needed style
     *         properties of this drawable object or {@code null} if no styles are needed.
     */
    public abstract Set<StylePropertyKey<?>> getNecessaryStyleProperties();
}
