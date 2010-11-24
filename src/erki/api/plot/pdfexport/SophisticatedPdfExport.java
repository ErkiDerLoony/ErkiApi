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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.axis.ValueAxis;

import erki.api.plot.Plot2d;
import erki.api.plot.style.StyleProvider;

/**
 * This listener implements the sophisticated pdf export mechanism for {@link Plot2d}.
 * <p>
 * The first time this listener is called it displays an additional dialog that enables the user to
 * choose which components of the selected plot he wants to export. If the listener is called
 * additional times the contents of the selected plot are added to the already displayed content.
 * This allows the user to mix the contents of multiple plots and then export the result to a pdf
 * file.
 * 
 * @author Edgar Kalkowski
 */
public class SophisticatedPdfExport implements ActionListener {
    
    private static final long serialVersionUID = -7533151605417797603L;
    
    static JDialog dialog = null;
    
    private static Plot2d accumulationPlot;
    
    private static JPanel left;
    
    private StyleProvider styleProvider;
    
    private Plot2d plot;
    
    /**
     * Create a new SophisticatedPdfExport for a specific Plot2d.
     * 
     * @param plot
     *        The plot whose drawers shall be added to the pdf export dialog.
     * @param styleProvider
     *        The StyleProvider that is used for the plot that accumulates all the drawers to
     *        export.
     */
    public SophisticatedPdfExport(Plot2d plot, StyleProvider styleProvider) {
        this.styleProvider = styleProvider;
        this.plot = plot;
    }
    
    private void init() {
        dialog = new JDialog();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setTitle("PDF-Export");
        dialog.setAlwaysOnTop(true);
        
        Container cp = dialog.getContentPane();
        cp.setLayout(new BorderLayout());
        
        accumulationPlot = new Plot2d(styleProvider);
        
        try {
            accumulationPlot.setDomainAxis((ValueAxis) plot.getPlot().getDomainAxis().clone());
            accumulationPlot.setRangeAxis((ValueAxis) plot.getPlot().getRangeAxis().clone());
        } catch (CloneNotSupportedException e) {
            throw new Error("FATAL: Bad coordinate axis!");
        }
        
        cp.add(accumulationPlot, BorderLayout.CENTER);
        
        left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        left.setBorder(new EmptyBorder(10, 10, 10, 10));
        left.add(new GeneralOptionPanel(accumulationPlot, dialog));
        
        JScrollPane scrollPane = new JScrollPane(left);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        cp.add(scrollPane, BorderLayout.WEST);
        
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        
        dialog.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dialog = null;
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (dialog == null) {
            init();
        }
        
        left.add(Box.createVerticalStrut(10));
        left.add(new PlotOptionPanel(left, accumulationPlot, plot.getDrawers()));
        dialog.validate();
        
        accumulationPlot.getPlot().configureDomainAxes();
        accumulationPlot.getPlot().configureRangeAxes();
        accumulationPlot.repaint();
    }
}
