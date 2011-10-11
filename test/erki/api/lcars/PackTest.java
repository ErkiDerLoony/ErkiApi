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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PackTest {
    
    public static void main(String[] args) {
        LCARSFrame frame = new LCARSFrame("Testframe");
        frame.setDefaultCloseOperation(LCARSFrame.EXIT_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        frame.addLCARSButton(new ButtonBarButton("Start"));
        frame.addLCARSButton(new ButtonBarButton("Pause"));
        frame.addLCARSButton(new ButtonBarButton("Ende", LCARSUtil.RED, LCARSUtil.RED_BRIGHT));
        
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.setPreferredSize(new Dimension(150, 150));
        content.setOpaque(false);
        
        LCARSButton button = new LCARSButton();
        content.add(button);
        button = new LCARSButton("Knopf Nr. 2");
        button.setBackground(LCARSUtil.RED);
        content.add(button);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MARK!!");
            }
        });
        
        cp.add(content, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
