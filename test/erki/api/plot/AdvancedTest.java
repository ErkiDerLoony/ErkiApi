/*
 * © Copyright 2007–2010 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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

package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JFrame;

import erki.api.plot.drawables.ColouredCirclePoint;
import erki.api.plot.style.StyleProvider;

public class AdvancedTest {
    
    private static boolean killed = false;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2d plot = new Plot2d(new StyleProvider());
        final SlidingWindow window = new SlidingWindow(50);
        plot.add(window);
        cp.add(plot, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        new Thread() {
            
            @Override
            public void run() {
                super.run();
                
                Random random = new Random();
                int timestamp = 0;
                
                while (!killed) {
                    Point2D.Double newPoint = new Point2D.Double(timestamp,
                            random.nextGaussian() + 5.0);
                    window.add(new ColouredCirclePoint(newPoint, new Color(random.nextInt())));
                    plot.autorange();
                    timestamp++;
                    
                    try {
                        Thread.sleep(333);
                    } catch (InterruptedException e) {
                    }
                }
            }
            
        }.start();
        
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                killed = true;
            }
        });
    }
}
