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

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import erki.api.plot.drawables.Drawable;

public class DefaultStyleProvider implements StyleProvider {
    
    private Map<StylePropertyKey<?>, StyleProperty<?>> mapping = new TreeMap<StylePropertyKey<?>, StyleProperty<?>>();
    
    public DefaultStyleProvider() {
        
        // Point properties
        addMapping(new StylePropertyKey<Integer>("POINT_SIZE"),
                new StyleProperty<Integer>(10));
        addMapping(new StylePropertyKey<Stroke>("POINT_STROKE"),
                new StyleProperty<BasicStroke>(new BasicStroke(1.25f)));
        
        // Line properties
        addMapping(new StylePropertyKey<Stroke>("LINE_STROKE"),
                new StyleProperty<Stroke>(new BasicStroke(1.25f)));
        
        // Coordinate axes properties
        addMapping(new StylePropertyKey<Font>("AXES_TICK_FONT"),
                new StyleProperty<Font>(new Font(Font.SANS_SERIF, Font.PLAIN,
                        16)));
        addMapping(new StylePropertyKey<Stroke>("AXES_STROKE"),
                new StyleProperty<Stroke>(new BasicStroke(1.25f)));
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        addMapping(
                new StylePropertyKey<NumberFormat>("AXES_TICK_NUMBER_FORMAT"),
                new StyleProperty<NumberFormat>(nf));
    }
    
    public <T> void addMapping(StylePropertyKey<T> key,
            StyleProperty<? extends T> value) {
        mapping.put(key, value);
    }
    
    @Override
    public void checkProperties(Drawable drawable) {
        
        for (StylePropertyKey<?> p : drawable.getNecessaryStyleProperties()) {
            
            if (!mapping.containsKey(p)) {
                throw new IllegalStateException(drawable
                        + " need style property " + p
                        + " but there is no mapping for it!");
            }
        }
    }
    
    /** @return See {@link Map#keySet()}. */
    public Collection<StylePropertyKey<?>> getPropertyKeys() {
        return mapping.keySet();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> StyleProperty<? extends T> getProperty(StylePropertyKey<T> key) {
        return (StyleProperty<T>) mapping.get(key);
    }
}
