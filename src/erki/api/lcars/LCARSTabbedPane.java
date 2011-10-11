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
package erki.api.lcars;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

/**
 * A tabbed pane that conforms to the LCARS design.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSTabbedPane extends JComponent {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = -2912669865863674009L;
    
    /** Diameter of the circle defining an edge of the border. */
    private static final int CIRCLE_DIAMETER = 30;
    
    /** Radius of the circle defining an edge of the border. */
    private static final int CIRCLE_RADIUS = CIRCLE_DIAMETER / 2;
    
    /** The tab bar containing an LCARSButton for each tab. */
    private JPanel tabBar = new JPanel();
    
    /** The current tab. */
    private JPanel childContent = new JPanel();
    
    /** The title of the current tab. */
    private String currentSelection;
    
    /** The panels for the tabs. */
    private TreeMap<String, JPanel> panels = new TreeMap<String, JPanel>();
    
    /** The buttons for the tab bar. */
    private TreeMap<String, LCARSButton> buttons = new TreeMap<String, LCARSButton>();
    
    /** Indicates wether the just added tab is the first one. */
    private boolean isFirstTab = true;
    
    /** These listeners are notified if the user selects a different tab. */
    private Collection<ActionListener> tabChangeListeners = new LinkedList<ActionListener>();
    
    /** Creates a new <code>LCARSTabbedPane</code>. */
    public LCARSTabbedPane() {
        childContent.setLayout(new BorderLayout());
        childContent.setOpaque(false);
        
        tabBar.setLayout(new FlowLayout());
        tabBar.setOpaque(false);
        
        setLayout(new BorderLayout());
        add(tabBar, BorderLayout.NORTH);
        add(childContent, BorderLayout.CENTER);
        
        addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setSize(getWidth(), getHeight());
                setLocation(0, 0);
                repaint();
            }
        });
        
        childContent.setBorder(new AbstractBorder() {
            
            private static final long serialVersionUID = 5045252819579873202L;
            
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                super.paintBorder(c, g, x, y, width, height);
                Graphics2D g2;
                
                if (g instanceof Graphics2D) {
                    g2 = (Graphics2D) g;
                } else {
                    throw new Error("Fatal drawing error!");
                }
                
                g2.setColor(LCARSUtil.BLUE);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.drawLine(0, 0, childContent.getWidth(), 0);
                g2.drawLine(0, 0, 0, childContent.getHeight() - CIRCLE_RADIUS);
                g2.drawArc(0, childContent.getHeight() - CIRCLE_DIAMETER - 1, CIRCLE_DIAMETER,
                        CIRCLE_DIAMETER, 180, 90);
                g2.drawLine(CIRCLE_RADIUS, childContent.getHeight() - 1, childContent.getWidth()
                        - CIRCLE_RADIUS - 1, childContent.getHeight() - 1);
                g2.drawArc(childContent.getWidth() - CIRCLE_DIAMETER - 1, childContent.getHeight()
                        - CIRCLE_DIAMETER - 1, CIRCLE_DIAMETER, CIRCLE_DIAMETER, 270, 90);
                g2.drawLine(childContent.getWidth() - 1, childContent.getHeight() - CIRCLE_RADIUS,
                        childContent.getWidth() - 1, 0);
            }
        });
        
        tabBar.setBorder(new AbstractBorder() {
            
            private static final long serialVersionUID = 5646375881918861645L;
            
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                super.paintBorder(c, g, x, y, width, height);
                Graphics2D g2;
                
                if (g instanceof Graphics2D) {
                    g2 = (Graphics2D) g;
                } else {
                    throw new Error("Fatal drawing error!");
                }
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(LCARSUtil.BLUE);
                
                g2.drawLine(0, tabBar.getHeight(), 0, CIRCLE_RADIUS);
                g2.drawArc(0, 0, CIRCLE_DIAMETER, CIRCLE_DIAMETER, 90, 90);
                g2.drawLine(CIRCLE_RADIUS, 0, tabBar.getWidth() - CIRCLE_RADIUS - 1, 0);
                g2.drawArc(tabBar.getWidth() - CIRCLE_DIAMETER - 1, 0, CIRCLE_DIAMETER,
                        CIRCLE_DIAMETER, 0, 90);
                g2.drawLine(tabBar.getWidth() - 1, CIRCLE_RADIUS, tabBar.getWidth() - 1,
                        tabBar.getHeight());
            }
        });
    }
    
    /**
     * Adds a tab to this <code>LCARSTabbedPane</code>.
     * 
     * @param content
     *        A {@link JPanel} containing the content of the new tab.
     * @param title
     *        The title of the new tab.
     */
    public void addTab(final JPanel content, final String title) {
        final LCARSButton button = new LCARSButton(title);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                childContent.removeAll();
                childContent.add(content, BorderLayout.CENTER);
                childContent.revalidate();
                childContent.repaint();
                button.setSelected(true);
                currentSelection = title;
                
                for (Component c : tabBar.getComponents()) {
                    
                    if (c instanceof LCARSButton && c != button) {
                        ((LCARSButton) c).setSelected(false);
                    }
                }
                
                for (ActionListener listener : tabChangeListeners) {
                    listener.actionPerformed(new ActionEvent(button, e.getID(), button
                            .getActionCommand()));
                }
            }
        });
        
        if (isFirstTab) {
            isFirstTab = false;
            button.setSelected(true);
            currentSelection = title;
            childContent.add(content, BorderLayout.CENTER);
            childContent.revalidate();
        }
        
        panels.put(title, content);
        buttons.put(title, button);
        refreshTabBar();
    }
    
    /**
     * Removes the tab with the corresponding title.
     * 
     * @param title
     *        The title of the tab to remove.
     */
    public void removeTab(String title) {
        boolean wasSelected = buttons.get(title).isSelected();
        panels.remove(title);
        buttons.remove(title);
        refreshTabBar();
        
        if (wasSelected && buttons.higherKey(title) != null) {
            childContent.removeAll();
            buttons.get(buttons.higherKey(title)).setSelected(true);
            childContent.add(panels.get(panels.higherKey(title)), BorderLayout.CENTER);
            currentSelection = buttons.higherKey(title);
            childContent.revalidate();
            childContent.repaint();
        } else if (wasSelected && buttons.lowerKey(title) != null) {
            childContent.removeAll();
            buttons.get(buttons.lowerKey(title)).setSelected(true);
            childContent.add(panels.get(panels.lowerKey(title)), BorderLayout.CENTER);
            currentSelection = buttons.lowerKey(title);
            childContent.revalidate();
            childContent.repaint();
        }
    }
    
    /** Refreshes the buttons in the tab bar. */
    private void refreshTabBar() {
        tabBar.removeAll();
        
        for (String s : buttons.keySet()) {
            tabBar.add(buttons.get(s));
        }
        
        tabBar.revalidate();
        tabBar.repaint();
    }
    
    /** @return The title of the currently selected tab. */
    public String getSelectedTitle() {
        return currentSelection;
    }
    
    /**
     * Adds an {@link ActionListener} that is notified if the user selects a different tab.
     * 
     * @param listener
     *        The <code>ActionListener</code> to add.
     */
    public void addTabChangeListener(ActionListener listener) {
        tabChangeListeners.add(listener);
    }
}
