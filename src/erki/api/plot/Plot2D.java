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
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
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
import erki.api.plot.style.BasicStyleProvider;
import erki.api.util.ErrorBox;
import erki.api.util.MathUtil;

/**
 * Extends {@link JPanel} to display a 2-dimensional plot of various
 * {@link Drawable} objects.
 * 
 * @author Edgar Kalkowski
 */
public class Plot2D extends JPanel {
    
    private static final long serialVersionUID = 7903143787970361747L;
    
    private final Collection<Drawable> drawables = new LinkedList<Drawable>();
    
    private final CoordinateTransformer transformer;
    
    private final double DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y,
            DEFAULT_MAX_Y;
    
    /**
     * Create a new {@code Plot2D} displaying a specific range of carthesian
     * coordinates and using a specific {@code StyleProvider} for the drawing of
     * the contained objects.
     * 
     * @param minX
     *        The minimum displayed value of the horizontal axis.
     * @param maxX
     *        The maximum displayed value of the horizontal axis.
     * @param minY
     *        The minimum displayed value of the vertical axis.
     * @param maxY
     *        The maximum displayed value of the vertical axis.
     */
    public Plot2D(double minX, double maxX, double minY, double maxY) {
        DEFAULT_MIN_X = minX;
        DEFAULT_MAX_X = maxX;
        DEFAULT_MIN_Y = minY;
        DEFAULT_MAX_Y = maxY;
        
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 500));
        
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
    
    /**
     * Create a new {@code Plot2D} that displays the default range of {@code
     * [-1.0, 1.0]} on both axes and uses a {@link BasicStyleProvider}. This
     * constructor may be used especially when calling {@link #autorange()} is
     * planned after adding some {@link Drawable}s.
     */
    public Plot2D() {
        this(-1.0, 1.0, -1.0, 1.0);
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
        
        item = new JMenuItem("Center on origin");
        menu.add(item);
        
        item.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                double xRange = getXMax() - getXMin();
                double yRange = getYMax() - getYMin();
                setRange(-xRange/2.0, xRange/2.0, -yRange/2.0, yRange/2.0);
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
                    
                    Document doc = new Document(new Rectangle(getWidth(), getHeight()));
                    
                    try {
                        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
                        doc.open();
                        PdfContentByte cb = writer.getDirectContent();
                        PdfTemplate tb = cb.createTemplate(getWidth(), getHeight());
                        Graphics2D g2 = tb.createGraphics(getWidth(), getHeight(),
                                new DefaultFontMapper());
                        Plot2D.this.print(g2);
                        g2.dispose();
                        cb.addTemplate(tb, 0, 0);
                        JOptionPane.showMessageDialog(Plot2D.this, "Pdf saved to " + file + ".",
                                "Ok", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FileNotFoundException e1) {
                        ErrorBox.showExceptionBox(Plot2D.this, e1);
                    } catch (DocumentException e1) {
                        ErrorBox.showExceptionBox(Plot2D.this, e1);
                    }
                    
                    doc.close();
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
    public synchronized boolean add(Drawable drawable) {
        boolean result = drawables.add(drawable);
        
        if (result) {
            repaint();
        }
        
        return result;
    }
    
    /** @return See {@link Collection#remove(Object)}. */
    public synchronized boolean remove(Drawable drawable) {
        boolean result = drawables.remove(drawable);
        
        if (result) {
            repaint();
        }
        
        return result;
    }
    
    /**
     * @return The {@code CoordinateTransformer} object that is used for
     *         transforming carthesian coordinates to the screen's pixel
     *         coordinates.
     */
    public CoordinateTransformer getCoordinateTransformer() {
        return transformer;
    }
    
    /**
     * Change the displayed range of carthesian coordinates and cause a repaint
     * of the whole plot to make the change actually visible to the user.
     * 
     * @param minX
     *        The minimum displayed value of the horizontal axis.
     * @param maxX
     *        The maximum displayed value of the horizontal axis.
     * @param minY
     *        The minimum displayed value of the vertical axis.
     * @param maxY
     *        The maximum displayed value of the vertical axis.
     */
    public void setRange(double minX, double maxX, double minY, double maxY) {
        transformer.setCarthesianCoordinates(minX, maxX, minY, maxY);
        repaint();
    }
    
    /**
     * Change the displayed range of carthesian coordinates of the horizontal
     * axis and cause a repaint of the whole plot to make the change actually
     * visible to the user.
     * 
     * @param minX
     *        The minimum displayed value of the horizontal axis.
     * @param maxX
     *        The maximum displayed value of the horizontal axis.
     */
    public void setXRange(double minX, double maxX) {
        transformer.setCarthesianCoordinates(minX, maxX, transformer
                .getCartMinY(), transformer.getCartMaxY());
        repaint();
    }
    
    /**
     * Change the displayed range of carthesian coordinates of the vertical axis
     * and cause a repaint of the whole plot to make the change actually visible
     * to the user.
     * 
     * @param minY
     *        The minimum displayed value of the vertical axis.
     * @param maxY
     *        The maximum displayed value of the vertical axis.
     */
    public void setYRange(double minY, double maxY) {
        transformer.setCarthesianCoordinates(transformer.getCartMinX(),
                transformer.getCartMaxX(), minY, maxY);
        repaint();
    }
    
    /**
     * @return The minimum displayed value of the horizontal axis
     *         ({@link CoordinateTransformer#getCartMinX()}).
     */
    public double getXMin() {
        return transformer.getCartMinX();
    }
    
    /**
     * @return The maximum displayed value of the horizontal axis
     *         ({@link CoordinateTransformer#getCartMaxX()}).
     */
    public double getXMax() {
        return transformer.getCartMaxX();
    }
    
    /**
     * @return The minimum displayed value of the vertical axis
     *         ({@link CoordinateTransformer#getCartMinY()}).
     */
    public double getYMin() {
        return transformer.getCartMinY();
    }
    
    /**
     * @return The maximum displayed value of the vertical axis
     *         ({@link CoordinateTransformer#getCartMaxY()}).
     */
    public double getYMax() {
        return transformer.getCartMaxY();
    }
    
    /**
     * This method adds a {@link MouseWheelListener} to this plot to make it
     * zoomable via the mouse wheel. The zoom is relative to the current mouse
     * pointer position.
     */
    public void addZoom() {
        
        addMouseWheelListener(new MouseWheelListener() {
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                Point2D.Double p = getCoordinateTransformer().getCarthesianCoordinates(
                        new Point(e.getX(), e.getY()));
                
                if (e.getWheelRotation() > 0) {
                    setRange((getXMin() - p.getX()) * 1.1 + p.getX(),
                            (getXMax() - p.getX()) * 1.1 + p.getX(),
                            (getYMin() - p.getY()) * 1.1 + p.getY(),
                            (getYMax() - p.getY()) * 1.1 + p.getY());
                } else {
                    setRange((getXMin() - p.getX()) * 0.9 + p.getX(),
                            (getXMax() - p.getX()) * 0.9 + p.getX(),
                            (getYMin() - p.getY()) * 0.9 + p.getY(),
                            (getYMax() - p.getY()) * 0.9 + p.getY());
                }
            }
        });
    }
    
    /**
     * Adds a {@link MouseListener} and a {@link MouseMotionListener} to this
     * plot to make it movable via dragging the first mouse button.
     */
    public void addMove() {
        
        MouseAdapter listener = new MouseAdapter() {
            
            private Point2D.Double old = null;
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                
                if (e.getButton() == MouseEvent.BUTTON1) {
                    old = getCoordinateTransformer().getCarthesianCoordinates(
                            new Point(e.getX(), e.getY()));
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                
                if (old != null) {
                    Point2D.Double newPoint = getCoordinateTransformer()
                            .getCarthesianCoordinates(
                                    new Point(e.getX(), e.getY()));
                    
                    double diffX = newPoint.getX() - old.getX();
                    double diffY = newPoint.getY() - old.getY();
                    
                    setRange(getXMin() - diffX, getXMax() - diffX, getYMin()
                            - diffY, getYMax() - diffY);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                old = null;
            }
        };
        
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }
    
    /**
     * Automatically adjust the displayed range of this plot in such way that
     * all current {@link Drawable}s are displayed and some additional margin.
     * The bounds of the {@link Drawable}s are determined via the
     * {@link Drawable#getBounds()} methods. If there currently are no drawables
     * to display or the only drawable object is a singular point the default
     * range as specified in the constructor call is displayed.
     * <p>
     * This method calls a complete repaint of the plot.
     */
    public void autorange() {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        
        // Zoom to default values if there are no drawable objects.
        if (drawables.isEmpty()) {
            setRange(DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y);
            return;
        }
        
        for (Drawable d : drawables) {
            Rectangle2D.Double bounds = d.getBounds();
            
            if (bounds == null) {
                continue;
            }
            
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
        
        if (MathUtil.equals(minX, Double.MAX_VALUE)) {
            minX = DEFAULT_MIN_X;
        }
        
        if (MathUtil.equals(maxX, Double.MIN_VALUE)) {
            maxX = DEFAULT_MAX_X;
        }
        
        if (MathUtil.equals(minY, Double.MAX_VALUE)) {
            minY = DEFAULT_MIN_Y;
        }
        
        if (MathUtil.equals(maxY, Double.MIN_VALUE)) {
            maxY = DEFAULT_MAX_Y;
        }
        
        // Prevent zooming to one singular point if there is only one point in
        // the list of drawables.
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
        synchronized (this) {
            
            for (Drawable drawable : drawables) {
                drawable.draw(g2, transformer);
            }
        }
        
        g2.setFont(oldFont);
    }
}
