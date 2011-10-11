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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import erki.api.plot.Plot2D;

/**
 * Adds a menu item to a {@link Plot2D}’s popup menu that allows the moving the origin of the plot
 * to the center of the plot panel.
 * 
 * @author Edgar Kalkowski
 */
public class CenterOnOrigin implements Action {
    
    private JPopupMenu menu;
    
    private JMenuItem item;
    
    /**
     * Create a new {@code CenterOnOrigin} object that adds a {@link JMenuItem} to the specified
     * popup menu.
     * 
     * @param menu
     *        The popup menu to which this action's item will be added. The menu may be obtained by
     *        adding a {@link PopupMenu} action to a {@link Plot2D} and calling
     *        {@link PopupMenu#getPopupMenu()} afterwards.
     */
    public CenterOnOrigin(JPopupMenu menu) {
        this.menu = menu;
    }
    
    @Override
    public void init(final Plot2D plot) {
        item = new JMenuItem("Center on origin");
        menu.add(item);
        
        item.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                double xRange = plot.getXMax() - plot.getXMin();
                double yRange = plot.getYMax() - plot.getYMin();
                plot.setRange(-xRange / 2.0, xRange / 2.0, -yRange / 2.0, yRange / 2.0);
            }
        });
    }
    
    @Override
    public void destroy(Plot2D plot) {
        menu.remove(item);
    }
}
