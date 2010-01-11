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

package erki.api.plot.style;

import java.util.Map;
import java.util.TreeMap;

import org.jfree.ui.Drawable;

import erki.api.plot.Plot2d;

/**
 * Provides basic styles needed by the basic gui functionality. For special purposes these style may
 * be overridden and additional style constants may be provided.
 * 
 * @author Edgar Kalkowski
 */
public class StyleProvider {
    
    private Map<StyleKey<?>, Object> mapping = new TreeMap<StyleKey<?>, Object>();
    
    /**
     * Create a new StyleProvider that contains basic constants needed by {@link Plot2d}. Everything
     * needed by custom {@link Drawable}s should ge into subclasses.
     */
    public StyleProvider() {
    }
    
    /**
     * Add new style constants to the internal mapping. If the mapping already contains a value for
     * some key that value is overwritten. This makes it easy for subclasses to redefine style
     * mappings from superclasses.
     */
    public <T> void put(StyleKey<T> key, T value) {
        mapping.put(key, value);
    }
    
    /** Access style constants stored in this StyleProvider. */
    @SuppressWarnings("unchecked")
    public <T> T get(StyleKey<T> key) {
        
        if (mapping.containsKey(key)) {
            // This cast is save if elements are only added via #put(StyleKey, Object)
            return (T) mapping.get(key);
        } else {
            throw new IllegalArgumentException(key + " is not defined in this StyleProvider!");
        }
    }
}
