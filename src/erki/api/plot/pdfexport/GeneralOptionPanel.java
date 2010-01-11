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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import erki.api.plot.Plot2d;

/**
 * The general options to be added to the right column of {@link SophisticatedPdfExport}.
 * 
 * @author Edgar Kalkowski
 */
public class GeneralOptionPanel extends JPanel {
    
    private static final long serialVersionUID = 670665839126494726L;
    
    /**
     * Create a new GeneralOptionPanel for a specific plot.
     * 
     * @param plot
     *        The plot that shall be exported to pdf.
     * @param parent
     *        The component that shall be used as parent component for dialog boxes (actually not
     *        used here directly but handed on to {@link SavePdfListener} from here).
     */
    public GeneralOptionPanel(Plot2d plot, Component parent) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new TitledBorder("General options"));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JCheckBox checkBox = new JCheckBox("Display plot");
        checkBox.setSelected(true);
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.addActionListener(new TogglePlotListener(plot.getPlot(), checkBox));
        add(checkBox);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        add(buttonPanel);
        
        JButton button = new JButton("Ok");
        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        button.addActionListener(new SavePdfListener(plot, parent));
        
        button = new JButton("Cancel");
        buttonPanel.add(button);
        button.addActionListener(new CancelListener());
    }
}
