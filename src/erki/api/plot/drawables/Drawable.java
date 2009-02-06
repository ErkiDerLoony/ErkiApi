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
import java.util.Collection;

import erki.api.plot.CoordinateTransformer;
import erki.api.plot.style.StylePropertyKey;
import erki.api.plot.style.StyleProvider;

public interface Drawable {
    
    void draw(Graphics2D g2, CoordinateTransformer transformer,
            StyleProvider styleProvider);
    
    Rectangle2D.Double getBounds();
    
    Collection<StylePropertyKey<?>> getNecessaryStyleProperties();
    
}
