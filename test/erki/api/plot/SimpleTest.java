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

package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

import javax.swing.JFrame;

import erki.api.plot.action.AutoRange;
import erki.api.plot.action.Move;
import erki.api.plot.action.PopupMenu;
import erki.api.plot.action.Zoom;
import erki.api.plot.drawables.BoxAxes;
import erki.api.plot.drawables.ColouredCirclePoint;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.plot.style.StyleProvider;
import erki.api.util.Log;

public class SimpleTest {
    
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setTitle("A simple test of the plot api");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Log.setLevel(Level.FINE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D(0.0, 1.0, 0.0, 1.0);
        final StyleProvider styleProvider = new BasicStyleProvider();
        plot.add(new BoxAxes(styleProvider));
        plot.add(new Move(MouseEvent.BUTTON1));
        plot.add(new Zoom());
        PopupMenu menu = new PopupMenu();
        plot.add(menu);
        plot.add(new AutoRange(menu.getPopupMenu()));
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.getButton() == MouseEvent.BUTTON2) {
                    plot.add(new ColouredCirclePoint(plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(new Point(e.getX(), e.getY())), new Color(
                            (int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math
                                    .random() * 256)), styleProvider));
                }
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
