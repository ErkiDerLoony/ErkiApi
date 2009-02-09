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

package erki.api.plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import erki.api.plot.drawables.Drawable;
import erki.api.plot.style.DefaultStyleProvider;
import erki.api.plot.style.StyleProvider;
import erki.api.util.ErrorBox;
import erki.api.util.MathUtil;

public class Plot2D extends JPanel {
    
    private static final long serialVersionUID = 7903143787970361747L;
    
    private final Collection<Drawable> drawables = new LinkedList<Drawable>();
    
    private String title;
    
    private Font titleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    
    private final CoordinateTransformer transformer;
    
    private final double DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y,
            DEFAULT_MAX_Y;
    
    private StyleProvider styleProvider;
    
    public Plot2D(String title, double minX, double maxX, double minY,
            double maxY, StyleProvider styleProvider) {
        this.title = title;
        this.styleProvider = styleProvider;
        DEFAULT_MIN_X = minX;
        DEFAULT_MAX_X = maxX;
        DEFAULT_MIN_Y = minY;
        DEFAULT_MAX_Y = maxY;
        
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 100));
        
        addContextMenu();
        
        transformer = new CoordinateTransformer(minX, maxX, minY, maxY,
                getPreferredSize().width, getPreferredSize().height);
        
        addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                transformer.setScreenSize(Plot2D.this.getWidth(), Plot2D.this
                        .getHeight());
                repaint();
            }
        });
    }
    
    public Plot2D(double minX, double maxX, double minY, double maxY) {
        this(null, minX, maxX, minY, maxY, new DefaultStyleProvider());
    }
    
    public Plot2D(String title) {
        this(title, -1.0, 1.0, -1.0, 1.0, new DefaultStyleProvider());
    }
    
    public Plot2D() {
        this(null, -1.0, 1.0, -1.0, 1.0, new DefaultStyleProvider());
    }
    
    private void addContextMenu() {
        final JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Auto range");
        menu.add(item);
        
        item.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                autorange();
            }
        });
        
        item = new JMenuItem("Export as pdf");
        menu.add(item);
        
        item.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(new File("")
                        .getAbsoluteFile());
                
                fileChooser.setFileFilter(new FileFilter() {
                    
                    @Override
                    public boolean accept(File f) {
                        
                        if (f.isDirectory()
                                || f.getName().toLowerCase().endsWith(".pdf")) {
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
                
                if (fileChooser.showSaveDialog(Plot2D.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile().getAbsoluteFile();
                    
                    if (file.exists() && file.isFile()) {
                        
                        Document doc = new Document(new Rectangle(getWidth(),
                                getHeight()));
                        
                        try {
                            PdfWriter writer = PdfWriter.getInstance(doc,
                                    new FileOutputStream(file));
                            doc.open();
                            PdfContentByte cb = writer.getDirectContent();
                            PdfTemplate tb = cb.createTemplate(getWidth(),
                                    getHeight());
                            Graphics2D g2 = tb.createGraphics(getWidth(),
                                    getHeight(), new DefaultFontMapper());
                            Plot2D.this.print(g2);
                            g2.dispose();
                            cb.addTemplate(tb, 0, 0);
                            JOptionPane.showMessageDialog(Plot2D.this,
                                    "Pdf saved " + "to " + file + ".", "Ok",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (FileNotFoundException e1) {
                            ErrorBox.showExceptionBox(Plot2D.this, e1);
                        } catch (DocumentException e1) {
                            ErrorBox.showExceptionBox(Plot2D.this, e1);
                        }
                        
                        doc.close();
                    }
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
    
    /** @return See {@link Collection#add(Object)}. */
    public boolean addDrawable(Drawable drawable) {
        styleProvider.checkProperties(drawable);
        boolean result = drawables.add(drawable);
        
        if (result) {
            repaint();
        }
        
        return result;
    }
    
    /** @return See {@link Collection#remove(Object)}. */
    public boolean removeDrawable(Drawable drawable) {
        boolean result = drawables.remove(drawable);
        
        if (result) {
            repaint();
        }
        
        return result;
    }
    
    public CoordinateTransformer getCoordinateTransformer() {
        return transformer;
    }
    
    public void setRange(double minX, double maxX, double minY, double maxY) {
        transformer.setCarthesianCoordinates(minX, maxX, minY, maxY);
        repaint();
    }
    
    public void setXRange(double minX, double maxX) {
        transformer.setCarthesianCoordinates(minX, maxX, transformer
                .getCartMinY(), transformer.getCartMaxY());
        repaint();
    }
    
    public void setYRange(double minY, double maxY) {
        transformer.setCarthesianCoordinates(transformer.getCartMinX(),
                transformer.getCartMaxX(), minY, maxY);
        repaint();
    }
    
    public void autorange() {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        
        // Zoom to default values if there are no drawable objects.
        if (drawables.isEmpty()) {
            setRange(DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y);
            return;
        }
        
        for (Drawable d : drawables) {
            Rectangle2D.Double bounds = d.getBounds();
            
            if (bounds.x < minX) {
                minX = bounds.x;
            }
            
            if (bounds.y < minY) {
                minY = bounds.y;
            }
            
            if (bounds.x + bounds.width > maxX) {
                maxX = bounds.x + bounds.width;
            }
            
            if (bounds.y + bounds.height > maxY) {
                maxY = bounds.y + bounds.height;
            }
        }
        
        // Prevent zooming to one singular point if there is only one item which
        // is a point in the list of drawables.
        if (MathUtil.equals(minX, maxX)) {
            minX = DEFAULT_MIN_X;
            maxX = DEFAULT_MAX_X;
        }
        
        if (MathUtil.equals(minY, maxY)) {
            minY = DEFAULT_MIN_Y;
            maxY = DEFAULT_MAX_Y;
        }
        
        double rangeX = maxX - minX, rangeY = maxY - minY;
        minX -= 0.1 * rangeX;
        maxX += 0.1 * rangeX;
        minY -= 0.1 * rangeY;
        maxY += 0.1 * rangeY;
        
        setRange(minX, maxX, minY, maxY);
        
        // Point screenMin = transformer.getScreenCoordinates(new
        // Point2D.Double(
        // minX, minY));
        // Point screenMax = transformer.getScreenCoordinates(new
        // Point2D.Double(
        // maxX, maxY));
        //        
        // Point2D.Double cartMin = transformer
        // .getCarthesianCoordinates(new Point(screenMin.x - 5,
        // screenMin.y - 5));
        // Point2D.Double cartMax = transformer
        // .getCarthesianCoordinates(new Point(screenMax.x + 5,
        // screenMax.y + 5));
        //        
        // setRange(cartMin.getX(), cartMax.getX(), cartMin.getX(),
        // cartMax.getY());
    }
    
    public void setStyleProvider(StyleProvider styleProvider) {
        this.styleProvider = styleProvider;
        
        for (Drawable drawable : drawables) {
            styleProvider.checkProperties(drawable);
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2;
        
        if (g instanceof Graphics2D) {
            g2 = (Graphics2D) g;
        } else {
            throw new IllegalStateException("Fatal drawing error!");
        }
        
        Font oldFont = g2.getFont();
        
        // Enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw all the drawables
        for (Drawable drawable : drawables) {
            drawable.draw(g2, transformer, styleProvider);
        }
        
        // Draw the title (if any)
        if (title != null) {
            g2.setFont(titleFont);
            float x = (float) (0.5 * getWidth() - 0.5 * g2.getFontMetrics()
                    .stringWidth(title));
            float y = (float) (g2.getFontMetrics().getHeight());
            g2.drawString(title, x, y);
        }
        
        g2.setFont(oldFont);
    }
}
