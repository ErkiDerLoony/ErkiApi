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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.DefaultButtonModel;

/**
 * A button in LCARS style.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSButton extends AbstractButton {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = -2107747236216272025L;
    
    /** The diameter of the circle at the edges of the button. */
    private int diameter = 20;
    
    /** The radius of the circle at the edges of the button. */
    private int radius = diameter / 2;
    
    /** Indicates wether or not the mouse currently is over the button. */
    private boolean rollover;
    
    /** Creates a new <code>LCARSButton</code>. */
    public LCARSButton() {
        this(null);
    }
    
    /**
     * Creates a new <code>LCARSButton</code>.
     * 
     * @param text
     *        The text written on the button.
     */
    public LCARSButton(String text) {
        text = text == null ? "" : text;
        setText(text);
        setRolloverEnabled(true);
        setBackground(LCARSUtil.BLUE);
        setModel(new DefaultButtonModel());
        
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                
                if (isEnabled()) {
                    
                    for (ActionListener listener : getActionListeners()) {
                        listener.actionPerformed(new ActionEvent(e.getSource(), e.getID(),
                                getActionCommand()));
                    }
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                rollover = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                rollover = false;
                repaint();
            }
        });
    }
    
    @Override
    public Dimension getMinimumSize() {
        Dimension minimumSize = super.getMinimumSize();
        int minWidth = Math.max(Math.max(getGraphics().getFontMetrics().stringWidth(getText())
                + diameter, 2 * diameter), minimumSize.width);
        int minHeight = Math.max(Math.max(diameter, getGraphics().getFontMetrics().getHeight()),
                minimumSize.height);
        return new Dimension(minWidth, minHeight);
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        int width = Math.max(getMinimumSize().width, preferredSize.width);
        int height = Math.max(getMinimumSize().height, preferredSize.height);
        return new Dimension(width, height);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2;
        
        if (g instanceof Graphics2D) {
            g2 = (Graphics2D) g;
        } else {
            throw new Error("Fatal drawing error!");
        }
        
        // Activate Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw edges
        if (isEnabled()) {
            
            if ((isRolloverEnabled() && rollover) || isSelected()) {
                
                if (getBackground().equals(LCARSUtil.BLUE)) {
                    g2.setColor(LCARSUtil.BLUE_BRIGHT);
                } else if (getBackground().equals(LCARSUtil.GREEN)) {
                    g2.setColor(LCARSUtil.GREEN_BRIGHT);
                } else if (getBackground().equals(LCARSUtil.RED)) {
                    g2.setColor(LCARSUtil.RED_BRIGHT);
                } else if (getBackground().equals(LCARSUtil.YELLOW)) {
                    g2.setColor(LCARSUtil.YELLOW_BRIGHT);
                } else {
                    g2.setColor(getBackground().brighter());
                }
                
            } else {
                g2.setColor(getBackground());
            }
            
        } else {
            g2.setColor(Color.GRAY);
        }
        
        g2.fillArc(0, 0, diameter, diameter, 0, 360);
        g2.fillArc(getWidth() - diameter, 0, diameter, diameter, 0, 360);
        g2.fillArc(0, getHeight() - diameter, diameter, diameter, 0, 360);
        g2.fillArc(getWidth() - diameter, getHeight() - diameter, diameter, diameter, 0, 360);
        
        // Fill body
        g2.fillRect(radius, 0, getWidth() - diameter, getHeight());
        g2.fillRect(0, radius, getWidth(), getHeight() - diameter);
        
        // Draw text
        g2.setColor(Color.BLACK);
        g2.drawString(getText(), getWidth() / 2 - g2.getFontMetrics().stringWidth(getText()) / 2,
                getHeight() / 2 + g2.getFontMetrics().getHeight() / 2
                        - g2.getFontMetrics().getDescent());
    }
}
