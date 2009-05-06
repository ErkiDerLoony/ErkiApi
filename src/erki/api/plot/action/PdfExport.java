/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under the terms of the GNU
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

package erki.api.plot.action;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import erki.api.plot.Plot2D;
import erki.api.util.ErrorBox;

/**
 * Adds a menu item with pdf export to the popup menu of a {@link Plot2D}.
 * 
 * @author Edgar Kalkowski
 */
public class PdfExport implements Action {
    
    private JPopupMenu menu;
    
    private JMenuItem item;
    
    private JFileChooser fileChooser;
    
    /**
     * Create a new {@code PdfExport} action.
     * 
     * @param menu
     *        The {@code JPopupMenu} to which this action's menu item will be added. This may e.g.
     *        be obtained by adding a {@link PopupMenu} action to a {@link Plot2D} and then calling
     *        {@link PopupMenu#getPopupMenu()}.
     */
    public PdfExport(JPopupMenu menu) {
        this.menu = menu;
        fileChooser = new JFileChooser(new File("").getAbsoluteFile());
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
    }
    
    @Override
    public void init(final Plot2D plot) {
        item = new JMenuItem("Export as pdf");
        menu.add(item);
        
        item.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (fileChooser.showSaveDialog(plot) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile().getAbsoluteFile();
                    
                    if (file.exists() && file.isDirectory()) {
                        JOptionPane.showMessageDialog(plot, "The chosen file is a directory!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (file.exists() && file.isFile()) {
                        
                        if (JOptionPane.showConfirmDialog(plot, "The file you selected already "
                                + "exists. Would you like to overwrite it? If you select “No” "
                                + "nothing will be saved.", "Overwrite " + file + "?",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            savePdf(plot, file);
                        }
                        
                    } else {
                        savePdf(plot, file);
                    }
                }
            }
        });
    }
    
    private void savePdf(Plot2D plot, File file) {
        Document doc = new Document(new Rectangle(plot.getWidth(), plot.getHeight()));
        
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tb = cb.createTemplate(plot.getWidth(), plot.getHeight());
            Graphics2D g2 = tb.createGraphics(plot.getWidth(), plot.getHeight(),
                    new DefaultFontMapper());
            plot.print(g2);
            g2.dispose();
            cb.addTemplate(tb, 0, 0);
            JOptionPane.showMessageDialog(plot, "Pdf saved to " + file + ".", "Ok",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e1) {
            ErrorBox.showExceptionBox(plot, e1);
        } catch (DocumentException e1) {
            ErrorBox.showExceptionBox(plot, e1);
        }
        
        doc.close();
    }
    
    @Override
    public void destroy(Plot2D plot) {
        menu.remove(item);
    }
}
