/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under the terms of the GNU
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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import erki.api.plot.Plot2D;

/**
 * This action makes a {@link Plot2D} object zoomable via the mouse wheel.
 * 
 * @author Edgar Kalkowski
 */
public class Zoom implements Action {
    
    private MouseWheelListener listener;
    
    @Override
    public void init(final Plot2D plot) {
        
        listener = new MouseWheelListener() {
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                Point2D.Double p = plot.getCoordinateTransformer().getCarthesianCoordinates(
                        new Point(e.getX(), e.getY()));
                
                if (e.getWheelRotation() > 0) {
                    plot.setRange((plot.getXMin() - p.getX()) * 1.1 + p.getX(), (plot.getXMax() - p
                            .getX())
                            * 1.1 + p.getX(), (plot.getYMin() - p.getY()) * 1.1 + p.getY(), (plot
                            .getYMax() - p.getY())
                            * 1.1 + p.getY());
                } else {
                    plot.setRange((plot.getXMin() - p.getX()) * 0.9 + p.getX(), (plot.getXMax() - p
                            .getX())
                            * 0.9 + p.getX(), (plot.getYMin() - p.getY()) * 0.9 + p.getY(), (plot
                            .getYMax() - p.getY())
                            * 0.9 + p.getY());
                }
            }
        };
        
        plot.addMouseWheelListener(listener);
    }
    
    @Override
    public void destroy(Plot2D plot) {
        plot.removeMouseWheelListener(listener);
    }
}
