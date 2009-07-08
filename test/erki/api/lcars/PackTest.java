/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import erki.api.lcars.LCARSErrorBox;
import erki.api.lcars.LCARSInfoBox;

public class PackTest {
    
    public static void main(String[] args) {
        final LCARSFrame frame = new LCARSFrame("Testframe");
        frame.setDefaultCloseOperation(LCARSFrame.EXIT_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        ButtonBarButton butt = new ButtonBarButton("Ende", LCARSUtil.RED, LCARSUtil.RED_BRIGHT);
        
        butt.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        
        frame.addLCARSButton(new ButtonBarButton("Dummy 1"));
        frame.addLCARSButton(new ButtonBarButton("Dummy 2"));
        frame.addLCARSButton(butt);
        
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.setPreferredSize(new Dimension(150, 150));
        content.setOpaque(false);
        
        LCARSButton button = new LCARSButton("Show info box");
        content.add(button);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new LCARSInfoBox("Information", "Sie haben den richtigen " + "Knopf gedrückt! :)",
                        frame).setVisible(true);
            }
        });
        
        button = new LCARSButton("Show error box");
        button.setBackground(LCARSUtil.RED);
        content.add(button);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                IOException ex = new IOException("Sie haben den falschen " + "Knopf gedrückt! :(");
                new LCARSErrorBox("Fatal error!", ex).setVisible(true);
            }
        });
        
        cp.add(content, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
