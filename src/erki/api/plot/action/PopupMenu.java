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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import erki.api.plot.Plot2D;

/**
 * This {@code Action} adds a popup menu to a {@link Plot2D}. After this {@code Action} was added to
 * a plot the popup menu may be obtained via {@link #getPopupMenu()} and handed to other actions
 * that require it.
 * 
 * @author Edgar Kalkowski
 */
public class PopupMenu implements Action {
    
    private MouseAdapter listener;
    
    private JPopupMenu menu;
    
    @Override
    public void init(Plot2D plot) {
        menu = new JPopupMenu();
        
        listener = new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };
        
        plot.addMouseListener(listener);
    }
    
    /**
     * Obtain the popup menu for usage with other actions that add items to it.
     * 
     * @return The popup menu of the plot this {@code Action} was added to or {@code null} if this
     *         action was not yet added to a plot.
     */
    public JPopupMenu getPopupMenu() {
        return menu;
    }
    
    /**
     * Remove the popup menu from a plot it was formerly added to. If this {@code Action} was never
     * added to a plot object this method does nothing and especially does not throw an exception.
     * 
     * @param plot
     *        The plot from which this popup menu shall be removed.
     */
    @Override
    public void destroy(Plot2D plot) {
        plot.removeMouseListener(listener);
    }
}
