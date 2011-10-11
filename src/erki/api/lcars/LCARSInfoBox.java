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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

/**
 * A message box that displays a message and has only an “Ok” button. Be careful that the message is
 * not too long as it's displayed in one line. However as the string is internally displayed via a
 * {@link LCARSLabel} which extends {@link JLabel} the text may be formatted using HTML as described
 * in the {@code JLabel} documentation.
 * <p>
 * If the “Ok” button is pressed nothing happens except that the message is hidden. If you want
 * other fancy stuff to happen add another {@link ActionListener} via
 * {@link #addActionListener(ActionListener)}.
 * <p>
 * After creation of an instance of this class all you have to do is call
 * {@link #setVisible(boolean)} with {@code true} to display the message.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSInfoBox extends LCARSFrame {
    
    private static final long serialVersionUID = 2023583686902871921L;
    
    private ButtonBarButton button;
    
    /**
     * Create a new {@code LCARSMessageBox}.
     * 
     * @param title
     *        The title of the message.
     * @param message
     *        The text of the message.
     * @param parent
     *        The component relative to which this message box will be centered (as achieved by
     *        calling {@link #setLocationRelativeTo(Component)} with parameter {@code parent}).
     */
    public LCARSInfoBox(String title, String message, Component parent) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        add(new LCARSLabel(message));
        
        button = new ButtonBarButton("Ok");
        addLCARSButton(button);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    /**
     * Create a new {@code LCARSMessageBox} that is centered on the screen (as achieved by calling
     * {@link #setLocationRelativeTo(Component)} with {@code null} as parameter).
     * 
     * @param title
     *        The title of the message.
     * @param message
     *        The text of the message.
     */
    public LCARSInfoBox(String title, String message) {
        this(title, message, null);
    }
    
    /**
     * Add another {@link ActionListener} to the “Ok” button of this message box.
     * 
     * @param listener
     *        The {@link ActionListener} to add.
     */
    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }
}
