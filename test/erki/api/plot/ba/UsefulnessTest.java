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

package erki.api.plot.ba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.drawables.ColouredCirclePoint;
import erki.api.plot.drawables.DrawableLine;
import erki.api.plot.drawables.LineAxes;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.plot.style.StyleProvider;

public class UsefulnessTest {
    
    private static double evaluation, init, time;
    
    private static boolean killed = false;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test der Nützlichkeitsformel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D();
        final StyleProvider styleProvider = new BasicStyleProvider();
        plot.setPreferredSize(new Dimension(500, 500));
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addDrawable(new LineAxes(styleProvider));
        
        plot.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                evaluation += 0.1;
            }
        });
        
        time = 0.1;
        init = 0.5;
        evaluation = init;
        
        new Thread("FadeThread") {
            
            private Point2D.Double oldPoint = null;
            
            @Override
            public void run() {
                super.run();
                
                while (!killed && time < 5.0) {
                    // evaluation = (evaluation - init) * Math.exp(-lambda) +
                    // init;
                    // evaluation = (evaluation - init) * 0.75 + init;
                    evaluation = 0.75 * evaluation + 0.25 * init;
                    Point2D.Double newPoint = new Point2D.Double(time,
                            evaluation);
                    plot.addDrawable(new ColouredCirclePoint(newPoint.getX(), newPoint
                            .getY(), Color.RED, styleProvider));
                    
                    if (oldPoint != null) {
                        plot.addDrawable(new DrawableLine(oldPoint, newPoint,
                                Color.RED, styleProvider));
                    }
                    
                    oldPoint = newPoint;
                    time += 0.1;
                    
                    System.out.println("time = " + time + ", evaluation = "
                            + evaluation);
                    plot.autorange();
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
            
        }.start();
        
        frame.pack();
        plot.autorange();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                killed = true;
            }
        });
    }
}
