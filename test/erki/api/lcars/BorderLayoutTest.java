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
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Tests the LCARS api using a {@link BorderLayout} for lying out the test components.
 * 
 * @author Edgar Kalkowski
 */
public class BorderLayoutTest {
    
    /**
     * Starts the test.
     * 
     * @param args
     *        Command line arguments. Not used in this case.
     */
    public static void main(String[] args) {
        final LCARSFrame frame = new LCARSFrame("Testfenster");
        frame.setDefaultCloseOperation(LCARSFrame.EXIT_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        JTextArea textArea = new JTextArea(10, 10);
        cp.add(textArea, BorderLayout.NORTH);
        
        JTextField textField = new JTextField("Blablabla");
        cp.add(textField, BorderLayout.CENTER);
        
        JButton button = new JButton("Testknopf");
        cp.add(button, BorderLayout.SOUTH);
        
        JLabel leftLabel = new JLabel("Links");
        cp.add(leftLabel, BorderLayout.WEST);
        
        JLabel rightLabel = new JLabel("Rechts");
        cp.add(rightLabel, BorderLayout.EAST);
        
        for (int i = 0; i < 10; i++) {
            
            if (i == 3) {
                ButtonBarButton buttonBarButton = new ButtonBarButton("Testknopf Nr. " + i,
                        LCARSUtil.GREEN, LCARSUtil.GREEN_BRIGHT);
                frame.addLCARSButton(buttonBarButton);
                
                buttonBarButton.addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Button »" + e.getActionCommand()
                                + "« pressed.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                
            } else if (i == 4 || i == 5 || i == 6) {
                frame.addLCARSButton(new ButtonBarButton("Testknopf Nr. " + i, LCARSUtil.YELLOW,
                        LCARSUtil.YELLOW_BRIGHT));
            } else if (i == 7) {
                frame.addLCARSButton(new ButtonBarButton("Testknopf Nr. " + i, LCARSUtil.RED,
                        LCARSUtil.RED_BRIGHT));
            } else {
                frame.addLCARSButton(new ButtonBarButton("Testknopf Nr. " + i));
            }
        }
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
