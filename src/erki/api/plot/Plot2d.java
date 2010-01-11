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

package erki.api.plot;

import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;

import erki.api.plot.drawables.Drawable;
import erki.api.plot.pdfexport.SophisticatedPdfExport;
import erki.api.plot.style.StyleConstants;
import erki.api.plot.style.StyleKey;
import erki.api.plot.style.StyleProvider;

/**
 * This panel provides a two-dimensional coordinate system and allows for external drawers to draw
 * stuff into that coordinate system. The layout is configured in such way that it adheres to a
 * preferred size (if set via {@link #setPreferredSize(java.awt.Dimension)}). If the window is
 * resized the panel is automatically resized to use all available space.
 * 
 * @author Edgar Kalkowski
 */
public class Plot2d extends JPanel {
    
    private static final long serialVersionUID = 4045642298487773855L;
    
    /**
     * Default values for the coordinate ranges of both axes. They are returned by the getMin and
     * getMax methods if no drawers are present.
     */
    private static final double DEFAULT_MINX = -1.0;
    private static final double DEFAULT_MAXX = 1.0;
    private static final double DEFAULT_MINY = -1.0;
    private static final double DEFAULT_MAXY = 1.0;
    
    private LinkedList<Drawable> drawers = new LinkedList<Drawable>();
    
    private CoordinateTransformer transformer;
    
    private StyleProvider styleProvider;
    
    private RenderingInfoAndAutoRangingXYPlot plot;
    
    protected ExternalDrawerChartPanel chartPanel;
    
    /**
     * Create a new Plot2d without a title.
     * 
     * @param styleProvider
     *        This StyleProvider determines the look of the new plot.
     */
    public Plot2d(StyleProvider styleProvider) {
        this(null, null, styleProvider);
    }
    
    /**
     * Create a new Plot2d with a specific title.
     * 
     * @param drawers
     *        Some drawers that are initially added to this plot or {@code null} or an empty
     *        collection if the plot shall have no initial drawers.
     * @param title
     *        The title of the new plot or {@code null} if the plot shall have no title.
     * @param styleProvider
     *        This StyleProvider determines the look of the new plot. Must not be {@code null}!
     */
    public Plot2d(Collection<? extends Drawable> drawers, String title, StyleProvider styleProvider) {
        
        if (styleProvider == null) {
            throw new IllegalArgumentException("A plot needs to have a StyleProvider!");
        } else {
            this.styleProvider = styleProvider;
        }
        
        if (drawers == null) {
            drawers = new LinkedList<Drawable>();
        }
        
        // create JFreeChart with coordinate axes
        plot = new RenderingInfoAndAutoRangingXYPlot(this);
        NumberAxis domainAxis = new NumberAxis();
        NumberAxis rangeAxis = new NumberAxis();
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        new StandardChartTheme("JFree").apply(chart);
        plot.setBackgroundPaint(styleProvider.get(new StyleKey<Paint>(
                StyleConstants.PLOT_BACKGROUND_COLOR)));
        rangeAxis.setAutoRangeIncludesZero(false);
        domainAxis.setAutoRangeIncludesZero(false);
        
        // add JFreeChart to this panel
        transformer = new CoordinateTransformer(plot.getDomainAxis(), plot.getRangeAxis(), plot);
        chartPanel = new ExternalDrawerChartPanel(chart, transformer, this, styleProvider);
        setLayout(new GridLayout());
        setOpaque(false);
        add(chartPanel);
        
        // this is needed for the gridlines
        plot.setRenderer(new XYLineAndShapeRenderer(true, false));
        
        // make grid (in)visible
        boolean gridLines = styleProvider.get(new StyleKey<Boolean>(StyleConstants.GRID_VISIBLE));
        plot.setDomainGridlinesVisible(gridLines);
        plot.setRangeGridlinesVisible(gridLines);
        
        // adjust color of gridlines
        Paint gridLinesColor = styleProvider.get(new StyleKey<Paint>(StyleConstants.GRID_COLOR));
        plot.setDomainGridlinePaint(gridLinesColor);
        plot.setRangeGridlinePaint(gridLinesColor);
        
        // adjust stroke of gridlines
        Stroke gridStroke = styleProvider.get(new StyleKey<Stroke>(StyleConstants.GRID_STROKE));
        plot.setDomainGridlineStroke(gridStroke);
        plot.setRangeGridlineStroke(gridStroke);
        
        // enable/disable antialiasing
        boolean antialiasing = styleProvider.get(new StyleKey<Boolean>(
                StyleConstants.ANTIALIASING_ENABLED));
        chart.setAntiAlias(antialiasing);
        chart.setTextAntiAlias(antialiasing);
        
        // add pdf export after the “save as png” option
        JPopupMenu popupMenu = chartPanel.getPopupMenu();
        JMenuItem item = new JMenuItem("PDF-Export...");
        item.addActionListener(new SophisticatedPdfExport(this, styleProvider));
        popupMenu.add(item, 4);
        
        // add option to constrain the plot’s axes
        final JCheckBoxMenuItem checkItem = new JCheckBoxMenuItem("Constraint axes");
        
        checkItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (checkItem.isSelected()) {
                    getPlot().setConstrained(true);
                } else {
                    getPlot().setConstrained(false);
                }
            }
        });
        
        chartPanel.getPopupMenu().add(checkItem, chartPanel.getPopupMenu().getComponentCount());
        
        // add initial drawers
        addAll(drawers);
    }
    
    /**
     * Add new drawers to this plot.
     * 
     * @param drawer
     *        The drawer to add.
     */
    public void add(Drawable drawer) {
        
        if (drawer != null) {
            drawers.add(drawer);
            autorange();
        }
    }
    
    /**
     * All a whole collection of drawers to this plot.
     * 
     * @param drawers
     *        The drawers to add.
     */
    public void addAll(Collection<? extends Drawable> drawers) {
        
        if (drawers != null) {
            this.drawers.addAll(drawers);
            autorange();
        }
    }
    
    /**
     * Remove drawers that had been registered before.
     * 
     * @param drawer
     *        The drawer to remove.
     * @return See {@link List#remove(Object)} for the return values of this method.
     */
    public boolean remove(Drawable drawer) {
        boolean result = drawers.remove(drawer);
        autorange();
        return result;
    }
    
    /**
     * Access all drawers currently available to this plot. This is needed e.g. for
     * {@link SophisticatedPdfExport}. The returned list is a shallow copy of the actual list of
     * drawers. If you want to add or remove drawers use the respective methods of Plot2d.
     * 
     * @return A list that contains references to all drawers of this plot.
     */
    public LinkedList<Drawable> getDrawers() {
        LinkedList<Drawable> copy = new LinkedList<Drawable>();
        copy.addAll(drawers);
        return copy;
    }
    
    /** Cause the plot to recalculate the automatic range of the axes and repaint itself. */
    public void autorange() {
        plot.configureDomainAxes();
        plot.configureRangeAxes();
        chartPanel.repaint();
    }
    
    /** @return This plot’s CoordinateTransformer. */
    public CoordinateTransformer getCoordinateTransformer() {
        return transformer;
    }
    
    /** @return This plot’s StyleProvider. */
    public StyleProvider getStyleProvider() {
        return styleProvider;
    }
    
    /**
     * Access the actual {@link RenderingInfoAndAutoRangingXYPlot} this panel contains.
     * 
     * @return The actual plot this panel displays.
     */
    public RenderingInfoAndAutoRangingXYPlot getPlot() {
        return plot;
    }
    
    @Override
    public synchronized void addMouseListener(MouseListener l) {
        chartPanel.addMouseListener(l);
    }
    
    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        chartPanel.addMouseMotionListener(l);
    }
    
    @Override
    public synchronized void addMouseWheelListener(MouseWheelListener l) {
        chartPanel.addMouseWheelListener(l);
    }
    
    /**
     * Change the domain axis used by this plot. This method also updates the coordinate transformer
     * of this plot accordingly.
     * 
     * @param axis
     *        The new axis used as domain axis.
     */
    public void setDomainAxis(ValueAxis axis) {
        plot.setDomainAxis(axis);
        transformer.setDomainAxis(axis);
    }
    
    /**
     * Change the range axis used by this plot. This method also updates the coordinate transformer
     * of this plot accordingly.
     * 
     * @param axis
     *        The new axis used as range axis.
     */
    public void setRangeAxis(ValueAxis axis) {
        plot.setRangeAxis(axis);
        transformer = new CoordinateTransformer(plot.getDomainAxis(), plot.getRangeAxis(), plot);
    }
    
    @Override
    public void repaint() {
        super.repaint();
        
        if (chartPanel != null) {
            chartPanel.repaint();
        }
    }
    
    /** @return The minimum value displayable in x dimension. */
    public double getMinX() {
        Drawable[] dArray = drawers.toArray(new Drawable[0]);
        double min = dArray.length == 0 ? DEFAULT_MINX : Double.MAX_VALUE;
        boolean changed = false;
        
        for (Drawable drawer : dArray) {
            Rectangle2D.Double bounds = drawer.getBounds();
            
            if (bounds != null && bounds.getMinX() < min) {
                min = bounds.getMinX();
                changed = true;
            }
        }
        
        return changed ? min : DEFAULT_MINX;
    }
    
    /** @return The maximum value displayable in x dimension. */
    public double getMaxX() {
        Drawable[] dArray = drawers.toArray(new Drawable[0]);
        double max = dArray.length == 0 ? DEFAULT_MAXX : Double.MIN_VALUE;
        boolean changed = false;
        
        for (Drawable drawer : dArray) {
            Rectangle2D.Double bounds = drawer.getBounds();
            
            if (bounds != null && bounds.getMaxX() > max) {
                max = bounds.getMaxX();
                changed = true;
            }
        }
        
        return changed ? max : DEFAULT_MAXX;
    }
    
    /** @return The minimum value displayable in y dimension. */
    public double getMinY() {
        Drawable[] dArray = drawers.toArray(new Drawable[0]);
        double min = dArray.length == 0 ? DEFAULT_MINY : Double.MAX_VALUE;
        boolean changed = false;
        
        for (Drawable drawer : dArray) {
            Rectangle2D.Double bounds = drawer.getBounds();
            
            if (bounds != null && bounds.getMinY() < min) {
                min = bounds.getMinY();
                changed = true;
            }
        }
        
        return changed ? min : DEFAULT_MINY;
    }
    
    /** @return The maximum value displayable in y dimension. */
    public double getMaxY() {
        Drawable[] dArray = drawers.toArray(new Drawable[0]);
        double max = dArray.length == 0 ? DEFAULT_MAXY : Double.MIN_VALUE;
        boolean changed = false;
        
        for (Drawable drawer : dArray) {
            Rectangle2D.Double bounds = drawer.getBounds();
            
            if (bounds != null && bounds.getMaxY() > max) {
                max = bounds.getMaxY();
                changed = true;
            }
        }
        
        return changed ? max : DEFAULT_MAXY;
    }
    
    /**
     * Adjust the displayed range of this plot.
     * 
     * @param x
     *        The range of the domain axis.
     * @param y
     *        The range of the range axis.
     */
    public void setRange(Range x, Range y) {
        plot.getDomainAxis().setRange(x);
        plot.getRangeAxis().setRange(y);
    }
}
