/*
 * (c) Copyright 2007-2008 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.lcars;

import java.awt.Color;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * Extends {@link JComboBox} to look like LCARS.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSComboBox extends JComboBox {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = 7682715065346648537L;
    
    /** Delegates to {@link JComboBox#JComboBox()}. */
    public LCARSComboBox() {
        super();
        initLCARS();
    }
    
    /** Delegates to {@link JComboBox#JComboBox(ComboBoxModel)}. */
    public LCARSComboBox(ComboBoxModel model) {
        super(model);
        initLCARS();
    }
    
    /** Delegates to {@link JComboBox#JComboBox(Object[])}. */
    public LCARSComboBox(Object[] items) {
        super(items);
        initLCARS();
    }
    
    /** Delegates to {@link JComboBox#JComboBox(Vector)}. */
    public LCARSComboBox(Vector<?> items) {
        super(items);
    }
    
    /** Initializes the LCARS design. */
    private void initLCARS() {
        setForeground(LCARSUtil.BLUE);
        setBackground(Color.BLACK);
    }
}
