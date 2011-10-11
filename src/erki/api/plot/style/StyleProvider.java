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
package erki.api.plot.style;

import erki.api.plot.drawables.Drawable;
import erki.api.plot.drawables.StyledDrawable;

/**
 * Implemented by all style providing classes.
 * <p>
 * Known subclasses: {@link BasicStyleProvider}.
 * 
 * @author Edgar Kalkowski
 */
public interface StyleProvider {
    
    /**
     * {@link StyledDrawable}s may request style properties via this method.
     * 
     * @param <T>
     *        The type of the property.
     * @param key
     *        A {@link StylePropertyKey} that describes the requested property.
     * @return A {@link StyleProperty} that contains an instance of the requested property.
     */
    <T> StyleProperty<? extends T> getProperty(StylePropertyKey<T> key);
    
    /**
     * This method is used by {@link StyledDrawable#StyledDrawable(StyleProvider)} to check if the
     * current style provider can actually provide all the needed style properties for the new
     * {@link Drawable}.
     * 
     * @param drawable
     *        The {@link StyledDrawable} whose necessities shall be checked.
     */
    void checkProperties(StyledDrawable drawable);
    
}
