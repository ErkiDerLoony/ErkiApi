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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import erki.api.plot.drawables.StyledDrawable;

/**
 * Provides basic style properties for points, lines and coordinate axes. The style mapping is
 * backed by a {@link TreeMap} so any subclass may override already contained style mappings by
 * simply adding a mapping with the same key but a different value.
 * 
 * @author Edgar Kalkowski
 */
public class BasicStyleProvider implements StyleProvider {
    
    private Map<StylePropertyKey<?>, StyleProperty<?>> mapping = new TreeMap<StylePropertyKey<?>, StyleProperty<?>>();
    
    public BasicStyleProvider() {
        
        // Point properties
        addMapping(new StylePropertyKey<Integer>("POINT_SIZE"), new StyleProperty<Integer>(10));
        addMapping(new StylePropertyKey<Stroke>("POINT_STROKE"), new StyleProperty<BasicStroke>(
                new BasicStroke(1.25f)));
        addMapping(new StylePropertyKey<Color>("POINT_COLOR"),
                new StyleProperty<Color>(Color.BLACK));
        
        // Line properties
        addMapping(new StylePropertyKey<Stroke>("LINE_STROKE"), new StyleProperty<Stroke>(
                new BasicStroke(1.25f)));
        addMapping(new StylePropertyKey<Color>("LINE_COLOR"), new StyleProperty<Color>(Color.RED));
        
        // Coordinate axes properties
        addMapping(new StylePropertyKey<Color>("AXES_COLOR"), new StyleProperty<Color>(Color.BLACK));
        addMapping(new StylePropertyKey<Stroke>("AXES_STROKE"), new StyleProperty<Stroke>(
                new BasicStroke(1.25f)));
        addMapping(new StylePropertyKey<Integer>("AXES_ARROW_OFFSET"), new StyleProperty<Integer>(
                10));
        addMapping(new StylePropertyKey<Integer>("AXES_BORDER"), new StyleProperty<Integer>(3));
        addMapping(new StylePropertyKey<Integer>("AXES_TICK_OFFSET"), new StyleProperty<Integer>(5));
        addMapping(new StylePropertyKey<Font>("AXES_TICK_FONT"), new StyleProperty<Font>(new Font(
                Font.SANS_SERIF, Font.PLAIN, 16)));
    }
    
    /**
     * Add a new style property mapping. This method asserts that the runtime type of keys and
     * values match.
     * 
     * @param <T>
     *        The type of a style property.
     * @param key
     *        The typed key.
     * @param value
     *        The value of the same type.
     */
    public <T> void addMapping(StylePropertyKey<T> key, StyleProperty<? extends T> value) {
        mapping.put(key, value);
    }
    
    @Override
    public void checkProperties(StyledDrawable drawable) {
        
        if (drawable.getNecessaryStyleProperties() == null) {
            return;
        }
        
        for (StylePropertyKey<?> p : drawable.getNecessaryStyleProperties()) {
            
            if (!mapping.containsKey(p)) {
                throw new IllegalStateException(drawable + " needs style property " + p
                        + " but there is no mapping for it!");
            }
        }
    }
    
    /** @return See {@link Map#keySet()}. */
    public Set<StylePropertyKey<?>> getPropertyKeys() {
        return mapping.keySet();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> StyleProperty<? extends T> getProperty(StylePropertyKey<T> key) {
        return (StyleProperty<T>) mapping.get(key);
    }
}
