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

package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import erki.api.plot.drawables.ColouredCirclePoint;
import erki.api.plot.drawables.ColouredLine;
import erki.api.plot.style.StyleProvider;
import erki.api.util.Level;
import erki.api.util.Log;

public class SimpleTest {
    
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setTitle("A simple test of the plot api");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Log.setLevel(Level.DEBUG);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2d plot = new Plot2d(new StyleProvider());
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addMouseListener(new MouseAdapter() {
            
            private Point2D.Double from = null;
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                System.out.println("Mouse pressed.");
                
                if (e.getButton() == MouseEvent.BUTTON2) {
                    Point2D.Double to = plot.getCoordinateTransformer().getMath(
                            new Point(e.getX(), e.getY()));
                    Color colour = new Color((int) (Math.random() * 256),
                            (int) (Math.random() * 256), (int) (Math.random() * 256));
                    plot.add(new ColouredCirclePoint(to, colour));
                    
                    if (from != null) {
                        plot.add(new ColouredLine(from, to, colour));
                    }
                    
                    from = to;
                }
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
