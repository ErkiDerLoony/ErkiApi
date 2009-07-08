/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

package erki.api.plot.mandelbrot;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.action.Move;
import erki.api.plot.action.Zoom;

public class SwingUI extends JFrame {
    
    private static final long serialVersionUID = 7048546481180869995L;
    
    public SwingUI() {
        setTitle("Mandelbrot");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Plot2D plot = new Plot2D(-2, 1, -1, 1);
        plot.add(new Move(MouseEvent.BUTTON1));
        plot.add(new Zoom());
        plot.add(new Mandelbrot());
        getContentPane().add(plot);
        
        pack();
    }
    
    public static void main(String[] args) {
        SwingUI frame = new SwingUI();
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
