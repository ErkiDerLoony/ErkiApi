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
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an button in the button bar of an {@link LCARSFrame}.
 * 
 * @author Edgar Kalkowski
 */
public class ButtonBarButton {
    
    /** The text displayed on this button. */
    private String text;
    
    /** The colour of this button. */
    private Color colour;
    
    /** The colour of the text on this button. */
    private Color textColour = Color.BLACK;
    
    /** The colour of this button when the mouse is over it. */
    private Color rolloverColour;
    
    /** The action listeners that are notified if this button is clicked. */
    private List<ActionListener> actionListeners = new LinkedList<ActionListener>();
    
    /** The position and size of this button. */
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    
    /** Indicates wether or not this button is currently enabled. */
    private boolean enabled = true;
    
    /**
     * Creates a new <code>ButtonBarButton</code> with a specific text drawn on it.
     * 
     * @param text
     *        The text of this button.
     */
    public ButtonBarButton(String text) {
        this(text, LCARSUtil.BLUE);
    }
    
    /**
     * Creates a new <code>ButtonBarButton</code> with a specific text drawn on it and a specific
     * colour.
     * 
     * @param text
     *        The text of this button.
     * @param colour
     *        The colour of this button.
     */
    public ButtonBarButton(String text, Color colour) {
        this(text, colour, colour.brighter());
    }
    
    /**
     * Creates a new <code>ButtonBarButton</code> with a specific text drawn on it, a specific
     * colour and a specific colour for the rollover effect.
     * 
     * @param text
     *        The text of this button.
     * @param colour
     *        The colour of this button when the mouse is not over it.
     * @param rolloverColour
     *        The colour of this button when the mouse is rolling over it.
     */
    public ButtonBarButton(String text, Color colour, Color rolloverColour) {
        this.text = text;
        this.colour = colour;
        this.rolloverColour = rolloverColour;
    }
    
    /** @return The text drawn on this button. */
    public String getText() {
        return text;
    }
    
    /**
     * Changes the text drawn on this button.
     * 
     * @param text
     *        The new text.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * @return The colour of this button. The returned instance of {@link Color} is a copy of the
     *         actual colour to prevent sideeffects.
     */
    public Color getColour() {
        return new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());
    }
    
    /**
     * Changes the colour of this button.
     * 
     * @param colour
     *        The new colour. The {@link Color} object is copied to prevent sideeffects.
     */
    public void setColour(Color colour) {
        this.colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(),
                colour.getAlpha());
    }
    
    /**
     * @return The colour of the text on this button. The returned instance of {@link Color} is a
     *         copy of the actual colour to prevent sideeffects.
     */
    public Color getTextColour() {
        return new Color(textColour.getRed(), textColour.getGreen(), textColour.getBlue(),
                textColour.getAlpha());
    }
    
    /**
     * Change the colour of the text displayed on this button.
     * 
     * @param textColour
     *        The new colour of the text on this button. The {@link Color} object is copied to
     *        prevent sideeffects.
     */
    public void setTextColour(Color textColour) {
        this.textColour = new Color(textColour.getRed(), textColour.getGreen(),
                textColour.getBlue(), textColour.getAlpha());
    }
    
    /** @return The colour this button shall have when the mouse is over it. */
    public Color getRolloverColour() {
        return rolloverColour;
    }
    
    /**
     * Changes the colour this button has when the mouse is over it.
     * 
     * @param rolloverColour
     *        The new colour for the rollover effect.
     */
    public void setRolloverColour(Color rolloverColour) {
        this.rolloverColour = rolloverColour;
    }
    
    /**
     * Adds an {@link ActionListener} to this button. The
     * {@link ActionListener#actionPerformed(java.awt.event.ActionEvent)} method is called if this
     * button is clicked.
     * 
     * @param listener
     *        The <code>ActionListener</code> to add to this button.
     */
    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }
    
    /**
     * Removes a previously added {@link ActionListener} from this button.
     * 
     * @param listener
     *        The <code>ActionListener</code> to remove.
     * @return <code>true</code> if the <code>ActionListener</code> was successfully removed.<br />
     *         <code>false</code> if the <code>ActionListener</code> specified was not found.
     */
    public boolean removeActionListener(ActionListener listener) {
        return actionListeners.remove(listener);
    }
    
    /**
     * Indicates wether or not this button can currently be clicked on.
     * 
     * @return <code>true</code> if this button can currently be clicked on.<br />
     *         <code>false</code> if this button cannot currently be clicked on.
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Flips this button between enabled and disabled. Enabled means this button can be clicked on.
     * Disabled means the button is deactivated and cannot be clicked on.
     * 
     * @param enabled
     *        <code>true</code> enables this button.<br />
     *        <code>false</code> disables this button.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * @return All {@link ActionListener}s that belong to this button. The returned list is not a
     *         copy and must not be modified!
     */
    protected List<ActionListener> getActionListeners() {
        return actionListeners;
    }
    
    /**
     * Manipulates the position of this button. The position is stored for recognizing which button
     * was actually clicked and because this way one only has to calculate the position once.
     * 
     * @param minX
     *        The minimal horizontal coordinate that belongs to this button.
     * @param maxX
     *        The maximal horizontal coordinate that belongs to this button.
     * @param minY
     *        The minimal vertical coordinate that belongs to this button.
     * @param maxY
     *        The maximal vertical coordinate that belongs to this button.
     */
    protected void setPosition(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    
    /** @return The minimal horizontal coordinate that belongs to this button. */
    protected int getMinX() {
        return minX;
    }
    
    /** @return The maximal horizontal coordinate that belongs to this button. */
    protected int getMaxX() {
        return maxX;
    }
    
    /** @return The minimal vertical coordinate that belongs to this button. */
    protected int getMinY() {
        return minY;
    }
    
    /** @return The maximal vertical coordinate that belongs to this button. */
    protected int getMaxY() {
        return maxY;
    }
}
