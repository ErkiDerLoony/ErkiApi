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

package erki.api.lcars;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.text.Document;

import erki.api.util.JTextField;

/**
 * Extends {@link JTextField} to look like LCARS.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSTextField extends JTextField {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = -7013267166502921250L;
    
    /** Delegates to {@link JTextField#JTextField()}. */
    public LCARSTextField() {
        super();
        initLCARS();
    }
    
    /** Delegates to {@link JTextField#JTextField(int)}. */
    public LCARSTextField(int columns) {
        super(columns);
        initLCARS();
    }
    
    /** Delegates to {@link JTextField#JTextField(String)}. */
    public LCARSTextField(String text) {
        super(text);
        initLCARS();
    }
    
    /** Delegates to {@link JTextField#JTextField(String, int)}. */
    public LCARSTextField(String text, int columns) {
        super(text, columns);
        initLCARS();
    }
    
    /** Delegates to {@link JTextField#JTextField(Document, String, int)}. */
    public LCARSTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        initLCARS();
    }
    
    /** Initializes LCARS look. */
    private void initLCARS() {
        setBorder(new LineBorder(LCARSUtil.BLUE, 1));
        setBackground(Color.BLACK);
        setForeground(LCARSUtil.BLUE);
    }
}
