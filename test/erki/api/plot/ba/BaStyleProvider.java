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

package erki.api.plot.ba;

import java.awt.BasicStroke;
import java.awt.Stroke;

import erki.api.plot.style.DefaultStyleProvider;
import erki.api.plot.style.StyleProperty;
import erki.api.plot.style.StylePropertyKey;

public class BaStyleProvider extends DefaultStyleProvider {
    
    public BaStyleProvider() {
        super();
        addMapping(new StylePropertyKey<Stroke>("ALTERNATE_LINE_STROKE"),
                new StyleProperty<BasicStroke>(new BasicStroke(1.25f,
                        BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
                        new float[] { 5.0f, 5.0f }, 0.0f)));
    }
}
