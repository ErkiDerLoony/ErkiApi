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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Realizes an LCARS version of {@link JFrame}.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSFrame extends JFrame {
    
    private static final long serialVersionUID = 1349252076688290590L;
    
    // Constants for the LCARS button and title bars
    private static final int BORDER = 10;
    private static final int BUTTON_BORDER = 3;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_BAR_WIDTH = 150;
    private static final int CIRCLE_DIAMETER = 30;
    private static final int CIRCLE_RADIUS = CIRCLE_DIAMETER / 2;
    private static final int TITLE_BAR_HEIGHT = CIRCLE_DIAMETER;
    
    private ContentPanel contentPane = new ContentPanel();
    
    private Container childContent = new JPanel();
    
    private Color titleColour = Color.BLACK;
    
    // LCARS colour defaults to LCARSUtil.BLUE but may be changed via
    // #setLCARSColour(Color)
    private Color lcarsColour = LCARSUtil.BLUE;
    
    private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    
    /** Creates a new <code>LCARSFrame</code>. */
    public LCARSFrame() {
        super();
        initLCARS();
    }
    
    /**
     * Creates a new <code>LCARSFrame</code> using a specific {@link GraphicsConfiguration}.
     * 
     * @param gc
     *        The <code>GraphicsConfiguration</code> to use.
     */
    public LCARSFrame(GraphicsConfiguration gc) {
        super(gc);
        initLCARS();
    }
    
    /**
     * Creates a new <code>LCARSFrame</code> with a specific title.
     * 
     * @param title
     *        The title of the new <code>LCARSFrame</code>.
     */
    public LCARSFrame(String title) {
        super(title);
        initLCARS();
    }
    
    /**
     * Creates a new <code>LCARSFrame</code> with a specific title an {@link GraphicsConfiguration}.
     * 
     * @param title
     *        The title of the new <code>LCARSFrame</code>.
     * @param gc
     *        The <code>GraphicsConfiguration</code> to use for the new <code>LCARSFrame</code>.
     */
    public LCARSFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        initLCARS();
    }
    
    /**
     * Called from each constructor to initialize LCARS specific design features.
     */
    private void initLCARS() {
        setBackground(Color.BLACK);
        childContent.setBackground(Color.BLACK);
        contentPane.setBackground(Color.BLACK);
        contentPane.setLayout(null);
        contentPane.add(childContent);
        super.setContentPane(contentPane);
        
        // Recalculates size and position of the content if the frame is
        // resized.
        addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension size = contentPane.getSize();
                childContent.setLocation(2 * BORDER + BUTTON_BAR_WIDTH, 2 * BORDER
                        + TITLE_BAR_HEIGHT);
                childContent.setSize(size.width - 3 * BORDER - BUTTON_BAR_WIDTH, size.height - 3
                        * BORDER - TITLE_BAR_HEIGHT);
                contentPane.setSize(size.width, size.height);
                contentPane.setLocation(0, 0);
                validate();
                
                if (getGraphics() != null) {
                    LCARSFrame.this.setMinimumSize(new Dimension(2 * BORDER + BUTTON_BAR_WIDTH
                            + CIRCLE_DIAMETER + getInsets().left + getInsets().right
                            + getGraphics().getFontMetrics(TITLE_FONT).stringWidth(getTitle()), 2
                            * BORDER + TITLE_BAR_HEIGHT + CIRCLE_DIAMETER + getInsets().top
                            + getInsets().bottom + contentPane.getButtonCount()
                            * (BUTTON_HEIGHT + BUTTON_BORDER) + BUTTON_BORDER));
                }
            }
        });
        
        contentPane.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                
                for (ButtonBarButton button : contentPane.buttons) {
                    
                    if (button.isEnabled() && e.getX() > button.getMinX()
                            && e.getX() < button.getMaxX() && e.getY() > button.getMinY()
                            && e.getY() < button.getMaxY()) {
                        
                        for (ActionListener listener : button.getActionListeners()) {
                            listener.actionPerformed(new ActionEvent(e.getSource(), e.getID(),
                                    button.getText(), System.currentTimeMillis(), e.getModifiers()));
                        }
                    }
                }
            }
        });
    }
    
    /**
     * @return The colour of the title of this {@code LCARSFrame}. The returned {@link Color} object
     *         is a copy of the actual colour to prevent external changes.
     */
    public Color getTitleColour() {
        return new Color(titleColour.getRed(), titleColour.getGreen(), titleColour.getBlue(),
                titleColour.getAlpha());
    }
    
    /**
     * Changes the colour of the title of this {@code LCARSFrame}. The title colour defaults to
     * black.
     * 
     * @param colour
     *        The new colour of the title. The {@link Color} object is copied to prevent
     *        sideeffects.
     */
    public void setTitleColour(Color colour) {
        titleColour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(),
                colour.getAlpha());
    }
    
    /**
     * @return The colour of the LCARS components of this {@code LCARSFrame}. The returned
     *         {@link Color} object is a copy of the actual colour to prevent sideeffects.
     */
    public Color getLCARSColour() {
        return new Color(lcarsColour.getRed(), lcarsColour.getGreen(), lcarsColour.getBlue(),
                lcarsColour.getAlpha());
    }
    
    /**
     * Change the default colour of the LCARS components.
     * 
     * @param colour
     *        The new colour to be used instead of {@link LCARSUtil#BLUE}. The {@link Color} object
     *        is copied to prevent external changes.
     */
    public void setLCARSColour(Color colour) {
        lcarsColour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(),
                colour.getAlpha());
    }
    
    /**
     * Adds a button to this frame. The buttons are displayed in the order they are added.
     * 
     * @param button
     *        The {@link ButtonBarButton} to add.
     */
    public void addLCARSButton(ButtonBarButton button) {
        contentPane.addLCARSButton(button);
    }
    
    @Override
    public Container getContentPane() {
        return childContent;
    }
    
    @Override
    public void setContentPane(Container contentPane) {
        childContent = contentPane;
    }
    
    @Override
    public void setLayout(LayoutManager manager) {
        
        if (isRootPaneCheckingEnabled()) {
            childContent.setLayout(manager);
        } else {
            super.setLayout(manager);
        }
    }
    
    @Override
    public Component add(Component comp) {
        
        if (isRootPaneCheckingEnabled()) {
            return childContent.add(comp);
        } else {
            return super.add(comp);
        }
    }
    
    @Override
    public Component add(Component comp, int index) {
        
        if (isRootPaneCheckingEnabled()) {
            return childContent.add(comp, index);
        } else {
            return super.add(comp, index);
        }
    }
    
    @Override
    public void add(Component comp, Object constraints) {
        
        if (isRootPaneCheckingEnabled()) {
            childContent.add(comp, constraints);
        } else {
            super.add(comp, constraints);
        }
    }
    
    @Override
    public void add(Component comp, Object constraints, int index) {
        
        if (isRootPaneCheckingEnabled()) {
            childContent.add(comp, constraints, index);
        } else {
            super.add(comp, constraints, index);
        }
    }
    
    @Override
    public Component add(String name, Component comp) {
        
        if (isRootPaneCheckingEnabled()) {
            return childContent.add(name, comp);
        } else {
            return super.add(name, comp);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        // Adds the size of the button and title bars to the preferred size.
        Dimension superSize = super.getPreferredSize();
        Dimension preferredSize = new Dimension(superSize.width + 3 * BORDER + BUTTON_BAR_WIDTH,
                superSize.height + 3 * BORDER + TITLE_BAR_HEIGHT);
        return preferredSize;
    }
    
    @Override
    public void pack() {
        super.pack();
        Dimension size = getPreferredSize();
        Dimension childSize = childContent.getLayout() == null ? childContent.getPreferredSize()
                : childContent.getLayout().preferredLayoutSize(contentPane);
        Dimension newSize = new Dimension(size.width + childSize.width, size.height
                + childSize.height);
        setSize(newSize.width, newSize.height);
    }
    
    /**
     * The {@link JPanel} on which the LCARS button and title bars are plotted and that later
     * contains the content pane for child components added by the user.
     * 
     * @author Edgar Kalkowski
     */
    private class ContentPanel extends JPanel {
        
        private static final long serialVersionUID = -8573075814675261951L;
        
        // Used for rollover effects on the buttons.
        private int mouseX, mouseY;
        
        // Used to detect if rollover status changed and trigger repaint then.
        // If a repaint is triggered every time the mouse moves (as it was in
        // legacy implementation) it eats up the cpu.
        private int hover = -1;
        
        private List<ButtonBarButton> buttons = new LinkedList<ButtonBarButton>();
        
        /**
         * Creates a new <code>ContentPanel</code>. Initializes the {@link MouseMotionListener} that
         * is responsible for the buttons' rollover effects.
         */
        public ContentPanel() {
            
            addMouseMotionListener(new MouseMotionAdapter() {
                
                @Override
                public void mouseMoved(MouseEvent e) {
                    super.mouseMoved(e);
                    mouseX = e.getX();
                    mouseY = e.getY();
                    boolean hoverNow = false;
                    
                    for (int i = 0; i < buttons.size(); i++) {
                        
                        if (isHover(getButtonsStart(), i)) {
                            hoverNow = true;
                            
                            if (hover != i) {
                                hover = i;
                                repaint(getButtonShape(i));
                                
                                if (i > 0) {
                                    repaint(getButtonShape(i - 1));
                                }
                                
                                if (i < buttons.size() - 1) {
                                    repaint(getButtonShape(i + 1));
                                }
                            }
                            
                            // As the mouse can only be above one button, we can
                            break;
                        }
                    }
                    
                    if (!hoverNow && hover != -1) {
                        hover = -1;
                        Rectangle first = getButtonShape(0);
                        Rectangle last = getButtonShape(getButtonCount() - 1);
                        repaint(first.x, first.y, first.width, last.y + last.height);
                    }
                }
            });
        }
        
        /**
         * Adds a button to this panel.
         * 
         * @param button
         *        The {@link ButtonBarButton} to add.
         */
        public void addLCARSButton(ButtonBarButton button) {
            buttons.add(button);
        }
        
        /** @return The number of buttons displayed in this panel. */
        public int getButtonCount() {
            return buttons.size();
        }
        
        /** Draws the LCARS components. */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2;
            
            // Save the old colour and font for later reset.
            final Color oldColor = g.getColor();
            final Font oldFont = g.getFont();
            
            if (g instanceof Graphics2D) {
                g2 = (Graphics2D) g;
            } else {
                throw new Error("Fatal drawing error!");
            }
            
            // Change to LCARS color and enable antialiasing.
            g2.setColor(lcarsColour);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Plot the button bar.
            g2.fillArc(BORDER, getHeight() - BORDER - CIRCLE_DIAMETER, CIRCLE_DIAMETER,
                    CIRCLE_DIAMETER, 0, 360);
            g2.fillArc(BORDER + BUTTON_BAR_WIDTH - CIRCLE_DIAMETER, getHeight() - BORDER
                    - CIRCLE_DIAMETER, CIRCLE_DIAMETER, CIRCLE_DIAMETER, 0, 360);
            g2.fillRect(BORDER + CIRCLE_RADIUS, getHeight() - BORDER - CIRCLE_DIAMETER,
                    BUTTON_BAR_WIDTH - CIRCLE_DIAMETER, CIRCLE_DIAMETER);
            g2.fillRect(BORDER, BORDER + CIRCLE_RADIUS, BUTTON_BAR_WIDTH, getHeight() - 2 * BORDER
                    - CIRCLE_DIAMETER);
            g2.fillRect(BORDER + BUTTON_BAR_WIDTH, BORDER + TITLE_BAR_HEIGHT, CIRCLE_RADIUS,
                    CIRCLE_RADIUS);
            g2.setColor(Color.BLACK);
            g2.fillArc(BORDER + BUTTON_BAR_WIDTH, BORDER + TITLE_BAR_HEIGHT, CIRCLE_DIAMETER,
                    CIRCLE_DIAMETER, 0, 360);
            g2.setColor(lcarsColour);
            
            // Plot the buttons
            final int BUTTONS_START = getButtonsStart();
            
            if (getButtonCount() > 0) {
                
                for (int i = 0; i < buttons.size(); i++) {
                    
                    if (buttons.get(i).isEnabled()) {
                        
                        if (isHover(BUTTONS_START, i)) {
                            g2.setColor(buttons.get(i).getRolloverColour());
                        } else {
                            g2.setColor(buttons.get(i).getColour());
                        }
                        
                    } else {
                        g2.setColor(Color.LIGHT_GRAY);
                    }
                    
                    g2.fill(getButtonShape(i));
                    buttons.get(i).setPosition(
                            BORDER,
                            BORDER + BUTTON_BAR_WIDTH,
                            BUTTONS_START + i * (BUTTON_HEIGHT + BUTTON_BORDER) + BUTTON_BORDER,
                            BUTTONS_START + (i + 1) * (BUTTON_HEIGHT + BUTTON_BORDER)
                                    + BUTTON_BORDER);
                }
                
                Stroke oldStroke = g2.getStroke();
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(BUTTON_BORDER));
                g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                g2.drawLine(BORDER, BUTTONS_START + BUTTON_BORDER, BORDER + BUTTON_BAR_WIDTH,
                        BUTTONS_START + BUTTON_BORDER);
                
                for (int i = 0; i < getButtonCount(); i++) {
                    g2.setColor(buttons.get(i).getTextColour());
                    g2.drawString(buttons.get(i).getText(), BORDER + BUTTON_BAR_WIDTH / 2
                            - g2.getFontMetrics().stringWidth(buttons.get(i).getText()) / 2,
                            BUTTONS_START + i * BUTTON_HEIGHT + i * BUTTON_BORDER + BUTTON_HEIGHT
                                    / 2 + g2.getFontMetrics().getHeight() / 2
                                    + g2.getFontMetrics().getDescent() / 2);
                    g2.setColor(Color.BLACK);
                    g2.drawLine(BORDER, BUTTONS_START + (i + 1) * (BUTTON_HEIGHT + BUTTON_BORDER)
                            + BUTTON_BORDER, BORDER + BUTTON_BAR_WIDTH, BUTTONS_START + (i + 1)
                            * (BUTTON_HEIGHT + BUTTON_BORDER) + BUTTON_BORDER);
                }
                
                g2.setColor(lcarsColour);
                g2.setStroke(oldStroke);
            }
            
            // Plot the title bar and print the title.
            g2.fillArc(BORDER, BORDER, CIRCLE_DIAMETER, CIRCLE_DIAMETER, 0, 360);
            g2.fillRect(BORDER + CIRCLE_RADIUS, BORDER, getWidth() - 2 * BORDER - CIRCLE_DIAMETER,
                    TITLE_BAR_HEIGHT);
            g2.fillArc(getWidth() - BORDER - CIRCLE_DIAMETER, BORDER, CIRCLE_DIAMETER,
                    CIRCLE_DIAMETER, 0, 360);
            g2.setColor(titleColour);
            g2.setFont(TITLE_FONT);
            g2.drawString(
                    LCARSFrame.this.getTitle(),
                    BORDER
                            + BUTTON_BAR_WIDTH
                            + CIRCLE_RADIUS
                            + (getWidth() - CIRCLE_RADIUS - 2 * BORDER - BUTTON_BAR_WIDTH - CIRCLE_RADIUS)
                            / 2 - g2.getFontMetrics().stringWidth(LCARSFrame.this.getTitle()) / 2,
                    BORDER
                            + g2.getFontMetrics().getHeight()
                            - 2
                            * g2.getFontMetrics().getDescent()
                            + Math.abs(g2.getFontMetrics().getHeight() - 2
                                    * g2.getFontMetrics().getDescent() - TITLE_BAR_HEIGHT) / 2);
            g2.setColor(lcarsColour);
            
            // Reset colour and font to original values.
            g.setColor(oldColor);
            g.setFont(oldFont);
        }
        
        private Rectangle getButtonShape(int index) {
            Rectangle rect = new Rectangle(BORDER, getButtonsStart() + index
                    * (BUTTON_HEIGHT + BUTTON_BORDER) + BUTTON_BORDER, BUTTON_BAR_WIDTH,
                    BUTTON_HEIGHT + BUTTON_BORDER);
            return rect;
        }
        
        private int getButtonsStart() {
            return CIRCLE_RADIUS + (getHeight() / 2)
                    - (getButtonCount() * (BUTTON_HEIGHT + BUTTON_BORDER) + BUTTON_BORDER) / 2;
        }
        
        private boolean isHover(int buttonsStart, int index) {
            
            if (mouseX > BORDER
                    && mouseX < BORDER + BUTTON_BAR_WIDTH
                    && mouseY > buttonsStart + index * (BUTTON_HEIGHT + BUTTON_BORDER)
                            + BUTTON_BORDER
                    && mouseY < buttonsStart + (index + 1) * (BUTTON_HEIGHT + BUTTON_BORDER)
                            + BUTTON_BORDER) {
                return true;
            } else {
                return false;
            }
        }
    };
}
