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
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import erki.api.plot.Plot2d;

/**
 * This listener is called if the user clicks the “Ok” button in {@link GeneralOptionPanel}. It
 * displayes a {@link JFileChooser} to request a filename and then saves the pdf to that file.
 * 
 * @author Edgar Kalkowski
 */
public class SavePdfListener implements ActionListener {
    
    private Plot2d plot;
    
    private Component parent;
    
    /**
     * Create a new SavePdfListener for a specific plot.
     * 
     * @param plot
     *        The plot that shall be exported to the pdf file.
     * @param parent
     *        This component is used as parent component for displayed message boxes.
     */
    public SavePdfListener(Plot2d plot, Component parent) {
        this.plot = plot;
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        
        fileChooser.setFileFilter(new FileFilter() {
            
            @Override
            public boolean accept(File f) {
                
                if (f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf")) {
                    return true;
                } else {
                    return false;
                }
            }
            
            @Override
            public String getDescription() {
                return "Portable Document Files (PDFs)";
            }
        });
        
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            if (!file.getName().endsWith(".pdf")) {
                file = new File(file.getAbsolutePath() + ".pdf");
            }
            
            if (file.isDirectory()) {
                JOptionPane.showMessageDialog(parent,
                        "The file you selected is actually a directory!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!file.exists()
                    || (file.exists() && JOptionPane.showConfirmDialog(parent, "The file " + file
                            + " already exists. Do you want to overwrite it?", "Question",
                            JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)) {
                savePdf(parent, plot, file.getAbsolutePath());
                SophisticatedPdfExport.dialog.dispose();
                SophisticatedPdfExport.dialog = null;
            }
        }
    }
    
    /**
     * Store the contents of a specific JComponent to a pdf file.
     * 
     * @param parent
     *        This component is used as parent component for displayed message boxes.
     * @param component
     *        The component whose contents shall be exported to the pdf file.
     * @param filename
     *        The filename to use
     */
    public static void savePdf(Component parent, JComponent component, String filename) {
        int width = component.getWidth();
        int height = component.getHeight();
        
        Document document = new Document(new Rectangle(width, height));
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(width, height);
            Graphics2D g2d = tp.createGraphics(width, height, new DefaultFontMapper());
            component.print(g2d);
            g2d.dispose();
            cb.addTemplate(tp, 0, 0);
            document.close();
            JOptionPane.showMessageDialog(parent, "Pdf saved to " + filename + ".", "Ok",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(parent,
                    "An error occurred while trying to save the pdf: "
                            + e.getClass().getSimpleName() + "!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parent, "The file you selected could not be opened!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
