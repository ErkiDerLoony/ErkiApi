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

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;

/**
 * Extends {@link XYPlot} to make the current {@link PlotRenderingInfo} visible to the public. This
 * is needed for the {@link CoordinateTransformer} (see there). Also extend the autoranging
 * mechanism of XYPlot to include custom external drawers.
 * 
 * @author Edgar Kalkowski
 */
public class RenderingInfoAndAutoRangingXYPlot extends XYPlot {
    
    private static final long serialVersionUID = 3937705514885429316L;
    
    // Minimal ranges that are displayed on both axes.
    private static final double MIN_X_RANGE = 1.0;
    private static final double MIN_Y_RANGE = 1.0;
    
    private PlotRenderingInfo info = null;
    
    private Plot2d plot;
    
    private boolean constrained;
    
    /**
     * Create a new RenderingInfoAndAutoRangingXYPlot that considers the drawers of a specific
     * Plot2d for autoranging.
     * 
     * @param plot
     *        The drawers of this Plot2d instance are considered for autoranging.
     */
    public RenderingInfoAndAutoRangingXYPlot(Plot2d plot) {
        this(plot, false);
    }
    
    /**
     * Create a new RenderingInfoAndAutoRangingXYPlot for a specific Plot2d instance and also
     * specify if the coordinate axes of the plot shall be constrained (i.e. both the domain and
     * range axes display the same range if autoranged).
     * 
     * @param plot
     *        The drawers of this Plot2d instance are considered for autoranging.
     * @param constrained
     *        If {@code true} both the domain and range axes always display the same range.
     */
    public RenderingInfoAndAutoRangingXYPlot(Plot2d plot, boolean constrained) {
        this.plot = plot;
        this.constrained = constrained;
    }
    
    /**
     * Specifies if this plot’s coordinate axes are constrained and thus display the same range if
     * autoranged.
     * 
     * @return {@code true} if the axes are constrained, {@code false} otherwise.
     */
    public boolean isConstrained() {
        return constrained;
    }
    
    /**
     * Change the constraint of this plot’s coordinate axes.
     * 
     * @param constrained
     *        If {@code true} this plot’s axes will display the same range if autoranged, if {@code
     *        false} they won’t.
     */
    public void setConstrained(boolean constrained) {
        this.constrained = constrained;
        getDomainAxis().configure();
        getRangeAxis().configure();
        plot.chartPanel.repaint();
    }
    
    @Override
    public Range getDataRange(ValueAxis axis) {
        Range superXRange = super.getDataRange(getDomainAxis());
        Range superYRange = super.getDataRange(getRangeAxis());
        
        // It must be == here, equals does not work!
        if (axis == getRangeAxis()) {
            
            if (constrained) {
                return Range.combine(getXRange(superXRange), getYRange(superYRange));
            } else {
                return getYRange(superYRange);
            }
            
        } else if (axis == getDomainAxis()) {
            
            if (constrained) {
                return Range.combine(getXRange(superXRange), getYRange(superYRange));
            } else {
                return getXRange(superXRange);
            }
            
        } else {
            return super.getDataRange(axis);
        }
    }
    
    private Range getYRange(Range superRange) {
        double min, max;
        
        if (superRange == null) {
            min = plot.getMinY();
            max = plot.getMaxY();
        } else {
            min = Math.min(plot.getMinY(), superRange.getLowerBound());
            max = Math.max(plot.getMaxY(), superRange.getUpperBound());
        }
        
        // always display a minimal range
        if (max - min < MIN_Y_RANGE) {
            double diff = MIN_Y_RANGE - max + min;
            min -= diff / 2.0;
            max += diff / 2.0;
        }
        
        return new Range(min, max);
    }
    
    private Range getXRange(Range superRange) {
        double min, max;
        
        if (superRange == null) {
            min = plot.getMinX();
            max = plot.getMaxX();
        } else {
            min = Math.min(plot.getMinX(), superRange.getLowerBound());
            max = Math.max(plot.getMaxX(), superRange.getUpperBound());
        }
        
        // always display a minimal range
        if (max - min < MIN_X_RANGE) {
            double diff = MIN_X_RANGE - max + min;
            min -= diff / 2.0;
            max += diff / 2.0;
        }
        
        return new Range(min, max);
    }
    
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState,
            PlotRenderingInfo info) {
        super.draw(g2, area, anchor, parentState, info);
        this.info = info;
    }
    
    /**
     * Access the current {@link PlotRenderingInfo} for this plot.
     * 
     * @return The current PlotRenderingInfo or {@code null} if the info is not yet computed.
     */
    public PlotRenderingInfo getRenderingInfo() {
        return info;
    }
}
