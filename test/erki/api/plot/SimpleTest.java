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
import erki.api.plot.drawables.CrossPoint;
import erki.api.plot.drawables.DrawableLine;
import erki.api.plot.drawables.LineAxes;
import erki.api.util.Log;

public class SimpleTest {
    
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setTitle("A simple test of the plot api");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Log.setDebug(true);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D(0.0, 1.0, 0.0, 1.0);
        plot.addDrawable(new LineAxes());
        plot.addZoom();
        plot.addMove();
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addMouseListener(new MouseAdapter() {
            
            private Point2D.Double old = null;
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.getButton() == MouseEvent.BUTTON1) {
                    
                    if (old == null) {
                        old = plot.getCoordinateTransformer()
                                .getCarthesianCoordinates(
                                        new Point(e.getX(), e.getY()));
                        plot.addDrawable(new CrossPoint(old));
                    } else {
                        Point newPoint = new Point(e.getX(), e.getY());
                        Point2D.Double newCart = plot
                                .getCoordinateTransformer()
                                .getCarthesianCoordinates(newPoint);
                        plot.addDrawable(new CrossPoint(newCart));
                        plot.addDrawable(new DrawableLine(old, newCart));
                    }
                    
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    plot.addDrawable(new ColouredCirclePoint(plot
                            .getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY())), new Color(
                            (int) (Math.random() * 256),
                            (int) (Math.random() * 256),
                            (int) (Math.random() * 256))));
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                
                if (e.getButton() == MouseEvent.BUTTON1) {
                    old = plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY()));
                }
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
