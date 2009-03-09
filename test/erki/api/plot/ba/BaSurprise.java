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
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.drawables.LineAxes;

public class BaSurprise {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Skalierung der KL2-Divergenz auf [0,1]");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        Plot2D plot = new Plot2D();
        plot.addZoom();
        plot.addMove();
        plot.setStyleProvider(new BaStyleProvider());
        plot.setPreferredSize(new Dimension(500, 500));
        cp.add(plot, BorderLayout.CENTER);
        
        plot.addDrawable(new LineAxes());
        plot.addDrawable(new SurpriseFunction(0.5, 5.0));
        plot.autorange();
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
