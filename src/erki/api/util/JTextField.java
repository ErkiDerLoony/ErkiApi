/*
 * (c) Copyright 2007-2008 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

package erki.api.util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.text.Document;

/**
 * Extends {@link javax.swing.JTextField} to select all the text in it each time
 * it gains the focus.
 * 
 * @author Edgar Kalkowski
 */
public class JTextField extends javax.swing.JTextField {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = -8897977393912703125L;
    
    /** Delegates to {@link javax.swing.JTextField#JTextField()}. */
    public JTextField() {
        super();
        init();
    }
    
    /** Delegates to {@link javax.swing.JTextField#JTextField(String)}. */
    public JTextField(String text) {
        super(text);
        init();
    }
    
    /** Delegates to {@link javax.swing.JTextField#JTextField(int)}. */
    public JTextField(int columns) {
        super(columns);
        init();
    }
    
    /** Delegates to {@link javax.swing.JTextField#JTextField(String, int)}. */
    public JTextField(String text, int columns) {
        super(text, columns);
        init();
    }
    
    /**
     * Delegates to
     * {@link javax.swing.JTextField#JTextField(Document, String, int)}.
     */
    public JTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        init();
    }
    
    /**
     * Adds the focus listener that selects all text each time the text field
     * gains the focus.
     */
    private void init() {
        
        addFocusListener(new FocusAdapter() {
            
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                select(0, getText().length());
            }
        });
    }
}
