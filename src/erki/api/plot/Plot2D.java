/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

package erki.api.plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import erki.api.plot.action.Action;
import erki.api.plot.drawables.Drawable;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.util.MathUtil;

/**
 * Extends {@link JPanel} to display a 2-dimensional plot of various {@link Drawable} objects.
 * 
 * @author Edgar Kalkowski
 */
public class Plot2D extends JPanel {
    
    private static final long serialVersionUID = 7903143787970361747L;
    
    private final Collection<Drawable> drawables = new LinkedList<Drawable>();
    
    private final CoordinateTransformer transformer;
    
    private final double DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y;
    
    /**
     * Create a new {@code Plot2D} displaying a specific range of carthesian coordinates and using a
     * specific {@code StyleProvider} for the drawing of the contained objects.
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
        
        transformer = new CoordinateTransformer(minX, maxX, minY, maxY, getPreferredSize().width,
                getPreferredSize().height);
        
        addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                transformer.setScreenSize(Plot2D.this.getWidth(), Plot2D.this.getHeight());
                repaint();
            }
        });
    }
    
    /**
     * Create a new {@code Plot2D} that displays the default range of {@code [-1.0, 1.0]} on both
     * axes and uses a {@link BasicStyleProvider}. This constructor may be used especially when
     * calling {@link #autorange()} is planned after adding some {@link Drawable}s.
     */
    public Plot2D() {
        this(-1.0, 1.0, -1.0, 1.0);
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
     * @return The {@code CoordinateTransformer} object that is used for transforming carthesian
     *         coordinates to the screen's pixel coordinates.
     */
    public CoordinateTransformer getCoordinateTransformer() {
        return transformer;
    }
    
    /**
     * Change the displayed range of carthesian coordinates and cause a repaint of the whole plot to
     * make the change actually visible to the user.
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
     * Change the displayed range of carthesian coordinates of the horizontal axis and cause a
     * repaint of the whole plot to make the change actually visible to the user.
     * 
     * @param minX
     *        The minimum displayed value of the horizontal axis.
     * @param maxX
     *        The maximum displayed value of the horizontal axis.
     */
    public void setXRange(double minX, double maxX) {
        transformer.setCarthesianCoordinates(minX, maxX, transformer.getCartMinY(), transformer
                .getCartMaxY());
        repaint();
    }
    
    /**
     * Change the displayed range of carthesian coordinates of the vertical axis and cause a repaint
     * of the whole plot to make the change actually visible to the user.
     * 
     * @param minY
     *        The minimum displayed value of the vertical axis.
     * @param maxY
     *        The maximum displayed value of the vertical axis.
     */
    public void setYRange(double minY, double maxY) {
        transformer.setCarthesianCoordinates(transformer.getCartMinX(), transformer.getCartMaxX(),
                minY, maxY);
        repaint();
    }
    
    /**
     * @return The minimum displayed value of the horizontal axis (
     *         {@link CoordinateTransformer#getCartMinX()}).
     */
    public double getXMin() {
        return transformer.getCartMinX();
    }
    
    /**
     * @return The maximum displayed value of the horizontal axis (
     *         {@link CoordinateTransformer#getCartMaxX()}).
     */
    public double getXMax() {
        return transformer.getCartMaxX();
    }
    
    /**
     * @return The minimum displayed value of the vertical axis (
     *         {@link CoordinateTransformer#getCartMinY()}).
     */
    public double getYMin() {
        return transformer.getCartMinY();
    }
    
    /**
     * @return The maximum displayed value of the vertical axis (
     *         {@link CoordinateTransformer#getCartMaxY()}).
     */
    public double getYMax() {
        return transformer.getCartMaxY();
    }
    
    /**
     * Via this method new {@code Action}s can be added to this plot that provide additional
     * functionality.
     * 
     * @param action
     *        The action to add.
     */
    public void add(Action action) {
        action.init(this);
    }
    
    /**
     * If an {@code Action} with additional functionality was added to this plot, it may again be
     * removed via this method later. If the delivered action was not formerly added to this plot it
     * is not guaranteed that this method throws no exceptions. What happens depends on the actual
     * implementation of the delivered {@code Action}.
     * 
     * @param action
     *        The action to remove.
     */
    public void remove(Action action) {
        action.destroy(this);
    }
    
    /**
     * Automatically adjust the displayed range of this plot in such way that all current
     * {@link Drawable}s are displayed and some additional margin. The bounds of the
     * {@link Drawable}s are determined via the {@link Drawable#getBounds()} methods. If there
     * currently are no drawables to display or the only drawable object is a singular point the
     * default range as specified in the constructor call is displayed.
     */
    public void autorange() {
        
        /*
         * This new thread is needed because autorange has in fact to be run two times (see below)
         * and after the first time has to call SwingUtilities.invokeAndWait() to wait for a repaint
         * which is not allowed from within the awt EventDispatcherThread. The call of this method
         * on the other hand is often executed from within the EventDispatcherThread (e.g. the
         * AutoRange action).
         */
        new Thread("AutorangeThread") {
            
            @Override
            public void run() {
                
                /*
                 * If saving space for coordinate axes the needed space may change after the first
                 * resize because the tick labels of the y-axis may change. I can’t think of another
                 * way than another resize to fix this. The effect could of course happen multiple
                 * times but I think repainting once is enough overhead.
                 */
                for (int i = 0; i < 2; i++) {
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
                    
                    setRange(minX, maxX, minY, maxY);
                    
                    // Respect the offset for coordinate axes (if any).
                    int left = 0, right = 0, top = 0, bottom = 0;
                    
                    for (Drawable d : drawables) {
                        
                        if (d instanceof CoordinateAxis) {
                            CoordinateAxis c = (CoordinateAxis) d;
                            
                            if (c.getLeftOffset() > left) {
                                left = c.getLeftOffset();
                            }
                            
                            if (c.getRightOffset() > right) {
                                right = c.getRightOffset();
                            }
                            
                            if (c.getTopOffset() > top) {
                                top = c.getTopOffset();
                            }
                            
                            if (c.getBottomOffset() > bottom) {
                                bottom = c.getBottomOffset();
                            }
                        }
                    }
                    
                    if (left != 0 || right != 0) {
                        double factor = (left + right)
                                / (double) (transformer.getScreenWidth() - left - right);
                        double range = maxX - minX;
                        maxX += factor * (right / (double) (left + right)) * range;
                        minX -= factor * (left / (double) (left + right)) * range;
                    }
                    
                    if (top != 0 || bottom != 0) {
                        double factor = (top + bottom)
                                / (double) (transformer.getScreenHeight() - top - bottom);
                        double range = maxY - minY;
                        maxY += factor * (top / (double) (top + bottom)) * range;
                        minY -= factor * (bottom / (double) (top + bottom)) * range;
                    }
                    
                    // After the repaint this setRange causes the y-axis may be at a different
                    // position
                    // because of now wider or smaller tick labels. This can only be compensated by
                    // another
                    // resize, can’t it?
                    setRange(minX, maxX, minY, maxY);
                    
                    try {
                        
                        SwingUtilities.invokeAndWait(new Runnable() {
                            
                            @Override
                            public void run() {
                                repaint();
                            }
                        });
                        
                    } catch (InterruptedException e) {
                        throw new Error("Very fatal error!", e);
                    } catch (InvocationTargetException e) {
                        throw new Error("Very fatal error!", e);
                    }
                }
            }
        }.start();
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
        
        // Enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw all the drawables
        synchronized (this) {
            
            for (Drawable drawable : drawables) {
                drawable.draw(g2, transformer);
            }
        }
    }
}
