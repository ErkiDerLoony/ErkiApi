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

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Extends {@link JLabel} to look like LCARS.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSLabel extends JLabel {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = -5034648905957939016L;
    
    /** Delegates to {@link JLabel#JLabel()}. */
    public LCARSLabel() {
        super();
        initLCARS();
    }
    
    /** Delegates to {@link JLabel#JLabel(String)}. */
    public LCARSLabel(String text) {
        super(text);
        initLCARS();
    }
    
    /** Delegates to {@link JLabel#JLabel(Icon)}. */
    public LCARSLabel(Icon image) {
        super(image);
        initLCARS();
    }
    
    /** Delegates to {@link JLabel#JLabel(String, int)}. */
    public LCARSLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        initLCARS();
    }
    
    /** Delegates to {@link JLabel#JLabel(Icon, int)}. */
    public LCARSLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        initLCARS();
    }
    
    /** Delegates to {@link JLabel#JLabel(String, Icon, int)}. */
    public LCARSLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        initLCARS();
    }
    
    /** Initializes LCARS look. */
    private void initLCARS() {
        setForeground(LCARSUtil.BLUE);
    }
}
