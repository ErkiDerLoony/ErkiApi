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

package erki.api.lcars;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

/**
 * A simple message box with a red LCARS decoration that indicates an error. Be
 * careful that the message text is not too long as it is displayed in one line
 * by default. However as the message text is internally displayed using an
 * {@link LCARSLabel} which itself extends {@link JLabel} the text may be
 * formatted using HTML as described in the {@code JLabel} documentation.
 * <p>
 * There is only one button labeled “Ok” that hides the message box and does
 * nothing else. If you want other fancy stuff to happen add another
 * {@link ActionListener} via {@link #addActionListener(ActionListener)}.
 * <p>
 * The only thing you have to do after creation of an instance of this class is
 * call {@link #setVisible(boolean)} with {@code true}.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSErrorBox extends LCARSFrame {
    
    private static final long serialVersionUID = 2733029336690128674L;
    
    private boolean blink;
    
    private boolean killed;
    
    private ButtonBarButton button;
    
    /**
     * Create a new {@code LCARSErrorBox}. The messabe box will be centered on
     * the screen as achieved by calling
     * {@link #setLocationRelativeTo(Component)} with {@code null} as parameter.
     * 
     * @param title
     *        The title of this message box.
     * @param message
     *        The text of the message.
     * @param parent
     *        The parent component of this message box relative to which this
     *        message box will be centered (as achieved by calling
     *        {@link #setLocationRelativeTo(Component)} with parameter {@code
     *        parent}).
     * @param blink
     *        Indicates whether or not the LCARS decoration of this window shall
     *        blink.
     */
    public LCARSErrorBox(String title, String message, Component parent,
            final boolean blink) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLCARSColour(LCARSUtil.RED);
        setResizable(false);
        
        LCARSLabel label = new LCARSLabel(message);
        label.setForeground(LCARSUtil.RED);
        add(label);
        
        button = new ButtonBarButton("Ok", LCARSUtil.RED, LCARSUtil.RED_BRIGHT);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        addLCARSButton(button);
        pack();
        setLocationRelativeTo(parent);
        this.blink = blink;
        
        new Thread("BlinkThread") {
            
            @Override
            public void run() {
                super.run();
                
                while (true) {
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    
                    if (killed) {
                        break;
                    }
                    
                    if (blink) {
                        
                        if (getTitleColour().equals(LCARSUtil.RED)) {
                            setTitleColour(Color.BLACK);
                            setLCARSColour(LCARSUtil.RED);
                            button.setColour(LCARSUtil.RED);
                            button.setTextColour(Color.BLACK);
                        } else {
                            setTitleColour(LCARSUtil.RED);
                            setLCARSColour(Color.BLACK);
                            button.setColour(Color.BLACK);
                            button.setTextColour(LCARSUtil.RED);
                        }
                        
                        repaint();
                        
                    } else {
                        
                        if (!getTitleColour().equals(Color.BLACK)) {
                            setTitleColour(Color.BLACK);
                        }
                        
                        if (!getLCARSColour().equals(LCARSUtil.RED)) {
                            setLCARSColour(LCARSUtil.RED);
                        }
                        
                        if (!button.getColour().equals(LCARSUtil.RED)) {
                            button.setColour(LCARSUtil.RED);
                        }
                    }
                }
            }
            
        }.start();
    }
    
    /**
     * Create a new {@code LCARSErrorBox} that will be centered on the screen as
     * achieved by calling {@link #setLocationRelativeTo(Component)} with
     * {@code null} as parameter.
     * 
     * @param title
     *        The title of this message box.
     * @param message
     *        The text of this message box.
     * @param blink
     *        Indicates whether or not the LCARS decoration of this message box
     *        will blink.
     */
    public LCARSErrorBox(String title, String message, boolean blink) {
        this(title, message, null, blink);
    }
    
    /**
     * Create a new {@code LCARSErrorBox} whose LCARS decoration will blink.
     * 
     * @param title
     *        The title of this message box.
     * @param message
     *        The text of the message.
     * @param parent
     *        The {@link Component} relative to which this message box will be
     *        centered (as achieved by calling
     *        {@link #setLocationRelativeTo(Component)} with {@code parent} as
     *        parameter).
     */
    public LCARSErrorBox(String title, String message, Component parent) {
        this(title, message, parent, true);
    }
    
    /**
     * Create a new {@code LCARSErrorBox} that will be centered on the screen as
     * achieved by calling {@link #setLocationRelativeTo(Component)} with
     * {@code null} as parameter and whose LCARS decoration will blink.
     * 
     * @param title
     *        The title of this message box.
     * @param message
     *        The text of the message.
     */
    public LCARSErrorBox(String title, String message) {
        this(title, message, null, true);
    }
    
    /**
     * Create a new {@code LCARSErrorBox} that displays a java exception.
     * 
     * @param title
     *        The title of this message box.
     * @param e
     *        The exception to display.
     * @param parent
     *        The {@link Component} relative to which this message box will be
     *        centered (as achieved by calling
     *        {@link #setLocationRelativeTo(Component)} with {@code parent} as
     *        parameter).
     * @param blink
     *        Indicates whether or not the LCARS decoration of this message box
     *        will blink.
     */
    public LCARSErrorBox(String title, Throwable e, Component parent,
            boolean blink) {
        this(title, getText(e), parent, blink);
    }
    
    /**
     * Create a new {@code LCARSErrorBox} that displays a java exception and
     * whose LCARS decoration blinks.
     * 
     * @param title
     *        The title of this message box.
     * @param e
     *        The exception to display.
     * @param parent
     *        The {@link Component} relative to which this message box will be
     *        centered (as achieved by calling
     *        {@link #setLocationRelativeTo(Component)} with {@code parent} as
     *        parameter).
     */
    public LCARSErrorBox(String title, Throwable e, Component parent) {
        this(title, e, parent, true);
    }
    
    /**
     * Create a new {@code LCARSErrorBox} that displays a java exception. The
     * message box will be displayed centered on the screen as achieved by
     * calling {@link #setLocationRelativeTo(Component)} with {@code null} as
     * parameter.
     * 
     * @param title
     *        The title of this message box.
     * @param e
     *        The exception to display.
     * @param blink
     *        {@code true} if the LCARS decoration shall blink, {@code false}
     *        otherwise.
     */
    public LCARSErrorBox(String title, Throwable e, boolean blink) {
        this(title, e, null, blink);
    }
    
    /**
     * Create a new {@code LCARSErrorBox} that displays a java exception. The
     * message box will be centered on the screen as achieved by calling
     * {@link #setLocationRelativeTo(Component)} with {@code null} as parameter
     * and whose LCARS decoration will blink.
     * 
     * @param title
     *        The title of this message box.
     * @param e
     *        The exception to display.
     */
    public LCARSErrorBox(String title, Throwable e) {
        this(title, e, null, true);
    }
    
    private static String getText(Throwable e) {
        String result = "<html>";
        result += e.toString();
        
        if (e.getStackTrace().length > 0) {
            result += "<br>";
        }
        
        for (int i = 0; i < e.getStackTrace().length; i++) {
            
            if (i < e.getStackTrace().length - 1) {
                result += "&nbsp;&nbsp;&nbsp;&nbsp;at "
                        + e.getStackTrace()[i].toString() + "<br>";
            } else {
                result += "&nbsp;&nbsp;&nbsp;&nbsp;at "
                        + e.getStackTrace()[i].toString();
            }
        }
        
        return appendCause(e.getCause(), result) + "</html>";
    }
    
    private static String appendCause(Throwable cause, String result) {
        
        if (cause != null) {
            result += "<br>";
            result += "Caused by: " + cause.toString();
            
            for (int i = 0; i < cause.getStackTrace().length; i++) {
                
                if (i < cause.getStackTrace().length - 1) {
                    result += "&nbsp;&nbsp;&nbsp;&nbsp;at "
                            + cause.getStackTrace()[i].toString() + "<br>";
                } else {
                    result += "&nbsp;&nbsp;&nbsp;&nbsp;at "
                            + cause.getStackTrace();
                }
            }
            
            result = appendCause(cause.getCause(), result);
        }
        
        return result;
    }
    
    /**
     * Indicates whether or not the LCARS decoration of this message box is
     * blinking.
     * 
     * @return {@code true} if the decoration is blinking, {@code false}
     *         otherwise.
     */
    public boolean isBlinking() {
        return blink;
    }
    
    /**
     * Make the LCARS decoration of this message box blink or stop it blinking.
     * 
     * @param blink
     *        {@code true} makes the decoration blink, {@code false} stops the
     *        blinking.
     */
    public void setBlink(boolean blink) {
        this.blink = blink;
    }
    
    /**
     * Add another {@link ActionListener} to the “Ok“ button of this message
     * box.
     * 
     * @param listener
     *        The {@link ActionListener} to add. It is not copied to be careful
     *        about sideeffects.
     */
    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        killed = true;
    }
}
