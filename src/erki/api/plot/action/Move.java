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
package erki.api.plot.action;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import erki.api.plot.Plot2D;

/**
 * This {@code Action} allows the user to move the plot with one of the mouse buttons.
 * 
 * @author Edgar Kalkowski
 */
public class Move implements Action {
    
    private int button;
    
    private MouseAdapter listener;
    
    /**
     * Create a new {@code Move} action.
     * 
     * @param button
     *        The mouse button that moves the plot when dragged. The values may be one of
     *        {@link MouseEvent#BUTTON1}, {@link MouseEvent#BUTTON2} or {@link MouseEvent#BUTTON3}.
     */
    public Move(int button) {
        this.button = button;
    }
    
    @Override
    public void init(final Plot2D plot) {
        
        listener = new MouseAdapter() {
            
            private Point2D.Double old = null;
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.getButton() == button) {
                    old = plot.getCoordinateTransformer().getCarthesianCoordinates(
                            new Point(e.getX(), e.getY()));
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                
                if (old != null) {
                    Point2D.Double newPoint = plot.getCoordinateTransformer()
                            .getCarthesianCoordinates(new Point(e.getX(), e.getY()));
                    
                    double diffX = newPoint.getX() - old.getX();
                    double diffY = newPoint.getY() - old.getY();
                    
                    plot.setRange(plot.getXMin() - diffX, plot.getXMax() - diffX, plot.getYMin()
                            - diffY, plot.getYMax() - diffY);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                old = null;
            }
        };
        
        plot.addMouseListener(listener);
        plot.addMouseMotionListener(listener);
    }
    
    /**
     * Removes the added move functionality from the delivered {@code Plot2D} object. If this action
     * was not formerly added to the specified plot nothing happens (no exceptions etc.).
     * 
     * @param plot
     *        The plot object from which this action shall be removed.
     */
    @Override
    public void destroy(Plot2D plot) {
        plot.removeMouseListener(listener);
        plot.removeMouseMotionListener(listener);
    }
}
