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

package erki.api.lcars;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * Tests the LCARS api using a {@link FlowLayout} for lying out the test components.
 * 
 * @author Edgar Kalkowski
 */
public class FlowLayoutTest {
    
    /**
     * Starts the test.
     * 
     * @param args
     *        Command line arguments. Not used in this case.
     */
    public static void main(String[] args) {
        LCARSFrame frame = new LCARSFrame("Testfenster");
        frame.setDefaultCloseOperation(LCARSFrame.EXIT_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new FlowLayout());
        
        cp.add(new JButton("Testbutton 1"));
        cp.add(new JButton("Testbutton 2"));
        cp.add(new JTextArea(2, 5));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
