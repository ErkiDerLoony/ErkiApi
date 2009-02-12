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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import erki.api.plot.drawables.LineAxes;
import erki.api.plot.drawables.ColouredCirclePoint;
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
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            
            private Point2D.Double oldPoint;
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.getButton() == MouseEvent.BUTTON1) {
                    oldPoint = plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY()));
                    
//                    if (oldPoint == null) {
//                        oldPoint = plot.getCoordinateTransformer()
//                                .getCarthesianCoordinates(
//                                        new Point(e.getX(), e.getY()));
//                        plot.addDrawable(new CrossPoint(oldPoint.getX(),
//                                oldPoint.getY()));
//                    } else {
//                        Point2D.Double newPoint = plot
//                                .getCoordinateTransformer()
//                                .getCarthesianCoordinates(
//                                        new Point(e.getX(), e.getY()));
//                        plot.addDrawable(new DrawableLine(oldPoint, newPoint));
//                        plot.addDrawable(new CrossPoint(newPoint.x, newPoint.y,
//                                new Color((int) (Math.random() * 256),
//                                        (int) (Math.random() * 256),
//                                        (int) (Math.random() * 256))));
//                        plot.repaint();
//                        oldPoint = newPoint;
//                    }
                    
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    Point2D.Double p = plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY()));
                    plot.addDrawable(new ColouredCirclePoint(p.x, p.y, new Color(
                            (int) (Math.random() * 256),
                            (int) (Math.random() * 256),
                            (int) (Math.random() * 256))));
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                
                if (oldPoint != null) {
                    Point2D.Double newPoint = plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY()));
                    
                    double diffX = newPoint.getX() - oldPoint.getX();
                    double diffY = newPoint.getY() - oldPoint.getY();
                    
                    plot.setRange(plot.getXMin() - diffX, plot.getXMax()
                            - diffX, plot.getYMin() - diffY, plot.getYMax()
                            - diffY);
                    plot.repaint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                oldPoint = null;
            }
        };
        
        plot.addMouseListener(mouseAdapter);
        plot.addMouseMotionListener(mouseAdapter);
        
        plot.addMouseWheelListener(new MouseWheelListener() {
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                if (e.getWheelRotation() > 0) {
                    plot.setRange(plot.getXMin() * 1.1, plot.getXMax() * 1.1,
                            plot.getYMin() * 1.1, plot.getYMax() * 1.1);
                } else {
                    plot.setRange(plot.getXMin() * 0.9, plot.getXMax() * 0.9,
                            plot.getYMin() * 0.9, plot.getYMax() * 0.9);
                }
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
