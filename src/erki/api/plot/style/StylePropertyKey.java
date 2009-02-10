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

package erki.api.plot.style;

/**
 * Wraps a {@link String} that is used for internal identification of a style
 * property and adds type information about the described style property.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the style property identified by this key.
 */
public class StylePropertyKey<T> implements Comparable<StylePropertyKey<?>> {
    
    private String key, description;
    
    /**
     * Create a new {@code StylePropertyKey}.
     * 
     * @param key
     *        The {@link String} that is internally used to identify the
     *        associated style property.
     * @param description
     *        A description of where this property is used.
     */
    public StylePropertyKey(String key, String description) {
        this.key = key;
        this.description = description;
    }
    
    /**
     * Create a new {@code StylePropertyKey}.
     * 
     * @param key
     *        The {@link String} that is internally used to identify the
     *        associated style property.
     */
    public StylePropertyKey(String key) {
        this(key, null);
    }
    
    public String getKey() {
        return key;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public int compareTo(StylePropertyKey<?> o) {
        return key.compareTo(o.getKey());
    }
    
    @Override
    public String toString() {
        
        if (description != null) {
            return key + " (" + description + ")";
        } else {
            return key;
        }
    }
}
