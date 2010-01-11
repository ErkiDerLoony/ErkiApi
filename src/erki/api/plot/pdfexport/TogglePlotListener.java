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

package erki.api.plot.pdfexport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import erki.api.plot.RenderingInfoAndAutoRangingXYPlot;

/**
 * This listener toggles the visibility of the plot in {@link GeneralOptionPanel}.
 * 
 * @author Edgar Kalkowski
 */
public class TogglePlotListener implements ActionListener {
    
    private RenderingInfoAndAutoRangingXYPlot plot;
    
    private JCheckBox checkBox;
    
    /**
     * Create a new TogglePlotListener for a specific plot.
     * 
     * @param plot
     *        The accumulating plot instance whose visibility shall be toggled.
     * @param checkBox
     *        The check box that triggers the toggling. If this checkbox is selected the plot is
     *        drawn. If it’s not selected the plot is not drawn.
     */
    public TogglePlotListener(RenderingInfoAndAutoRangingXYPlot plot, JCheckBox checkBox) {
        this.plot = plot;
        this.checkBox = checkBox;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (checkBox.isSelected()) {
            plot.setOutlineVisible(true);
            plot.getDomainAxis().setVisible(true);
            plot.getRangeAxis().setVisible(true);
            plot.setDomainGridlinesVisible(true);
            plot.setRangeGridlinesVisible(true);
        } else {
            plot.setOutlineVisible(false);
            plot.getDomainAxis().setVisible(false);
            plot.getRangeAxis().setVisible(false);
            plot.setDomainGridlinesVisible(false);
            plot.setRangeGridlinesVisible(false);
        }
    }
}
