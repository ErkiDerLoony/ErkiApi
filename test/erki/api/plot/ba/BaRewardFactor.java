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
import erki.api.plot.drawables.FixedTickWidthPositiveAxisWithArrow;

public class BaRewardFactor {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Initialisierung des Belohnungsfaktors");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(500, 500));
        plot.addDrawable(new FixedTickWidthPositiveAxisWithArrow(0.1, 0.01));
        plot.addDrawable(new RewardFactorFunction(0.3, 0.1, 0.12));
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
