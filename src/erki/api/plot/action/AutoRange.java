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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import erki.api.plot.Plot2D;

/**
 * Adds a menu item to the popup menu of a {@link Plot2D} that automatically computes the best
 * ranges for the plot’s axes.
 * 
 * @author Edgar Kalkowski
 */
public class AutoRange implements Action {
    
    private JPopupMenu menu;
    
    private JMenuItem item;
    
    /**
     * Create a new {@code AutoRange} that adds it’s functionality to an existing popup menu.
     * 
     * @param menu
     *        The popup menu to which this action’s menu item will be added. It may be obtained by
     *        adding a {@link PopupMenu} to a {@link Plot2D} and then calling
     *        {@link PopupMenu#getPopupMenu()}.
     */
    public AutoRange(JPopupMenu menu) {
        this.menu = menu;
    }
    
    @Override
    public void init(final Plot2D plot) {
        item = new JMenuItem("Auto range");
        menu.add(item);
        
        item.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                plot.autorange();
            }
        });
    }
    
    @Override
    public void destroy(Plot2D plot) {
        menu.remove(item);
    }
}
