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
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.ui.RectangleEdge;

import erki.api.util.MathUtil;

/**
 * Transforms between Java’s pixel coordinates and JFreeChart’s carthesian (mathematical) coordinate
 * system.
 * 
 * @author Edgar Kalkowski
 */
public class CoordinateTransformer {
    
    private final ValueAxis domainAxis, rangeAxis;
    
    private final RenderingInfoAndAutoRangingXYPlot renderingInfoPlot;
    
    /**
     * Create a new CoordinateTransformer for a specific plot.
     * 
     * @param plot
     *        The plot whose axes shall be used for the coordinate transformations.
     */
    public CoordinateTransformer(ValueAxis domainAxis, ValueAxis rangeAxis,
            RenderingInfoAndAutoRangingXYPlot renderingInfoPlot) {
        this.domainAxis = domainAxis;
        this.rangeAxis = rangeAxis;
        this.renderingInfoPlot = renderingInfoPlot;
    }
    
    private int convertLengthX(double xLength) {
        return (int) domainAxis.lengthToJava2D(xLength, dataArea(), RectangleEdge.BOTTOM);
    }
    
    private int convertLengthY(double yLength) {
        return (int) rangeAxis.lengthToJava2D(yLength, dataArea(), RectangleEdge.LEFT);
    }
    
    private int convertPointX(double x) {
        return (int) domainAxis.valueToJava2D(x, dataArea(), RectangleEdge.BOTTOM);
    }
    
    private int convertPointY(double y) {
        return (int) rangeAxis.valueToJava2D(y, dataArea(), RectangleEdge.LEFT);
    }
    
    /**
     * Transform a Shape from math coordinates to JFreeChart’s screen coordinates.
     * 
     * @param s
     *        The Shape that shall be transformed.
     * @return The transformed shape ready to be drawn to a {@link Graphics2D} instance.
     */
    public Shape getTransformedShape(Shape s) {
        Rectangle2D r = s.getBounds2D();
        
        double width = r.getWidth();
        
        if (MathUtil.isZero(width)) {
            width = 1.0;
        }
        
        double height = r.getHeight();
        
        if (MathUtil.isZero(height)) {
            height = 1.0;
        }
        
        double sx = convertLengthX(width);
        double sy = convertLengthY(height);
        double tx = convertPointX(r.getCenterX());
        double ty = convertPointY(r.getCenterY());
        
        // first we have to shift to zero and normalize (in math coordinate system) and then scale
        // and sift in graph coordinates.
        AffineTransform translate = AffineTransform.getTranslateInstance(tx, ty);
        // flip y coordinate as y in Graphics increases top to bottom
        AffineTransform scale = AffineTransform.getScaleInstance(sx, -sy);
        AffineTransform normalize = AffineTransform.getScaleInstance(1 / width, 1 / height);
        AffineTransform zero = AffineTransform.getTranslateInstance(-r.getCenterX(), -r
                .getCenterY());
        
        normalize.concatenate(zero);
        scale.concatenate(normalize);
        translate.concatenate(scale);
        
        Shape shape = translate.createTransformedShape(s);
        
        return shape;
    }
    
    private Rectangle2D dataArea() {
        return renderingInfoPlot.getRenderingInfo().getDataArea();
    }
    
    /**
     * Convert java screen coordinates to the math coordinate system.
     * 
     * @param java
     *        The java screen coordinates (e.g. taken from a mouse listener).
     * @return The converted math coordinates.
     */
    public Point2D.Double getMath(Point java) {
        PlotRenderingInfo renderingInfo = renderingInfoPlot.getRenderingInfo();
        
        if (renderingInfo != null && rangeAxis != null && domainAxis != null) {
            double newX = domainAxis.java2DToValue(java.x, renderingInfo.getDataArea(),
                    RectangleEdge.BOTTOM);
            double newY = rangeAxis.java2DToValue(java.y, renderingInfo.getDataArea(),
                    RectangleEdge.LEFT);
            return new Point2D.Double(newX, newY);
        } else {
            /*
             * TODO: What shall be do here? Someone requested transformed coordinates but the
             * transformer is not yet ready (see above). The way it is now it’s likely that bugs are
             * shadowed by this.
             */
            return new Point2D.Double();
        }
    }
    
    /**
     * Convert coordinates from math coordinates to java screen coordinates.
     * 
     * @param jfreechart
     *        The coordinates to convert.
     * @return The converted java screen coordinates (ready to be drawn to a graphics context).
     */
    public Point getJava(Point2D.Double jfreechart) {
        PlotRenderingInfo renderingInfo = renderingInfoPlot.getRenderingInfo();
        
        if (renderingInfo != null && rangeAxis != null && domainAxis != null) {
            int newX = (int) domainAxis.valueToJava2D(jfreechart.x, renderingInfo.getDataArea(),
                    RectangleEdge.BOTTOM);
            int newY = (int) rangeAxis.valueToJava2D(jfreechart.y, renderingInfo.getDataArea(),
                    RectangleEdge.LEFT);
            return new Point(newX, newY);
        } else {
            /*
             * TODO: What shall be do here? Someone requested transformed coordinates but the
             * transformer is not yet ready (see above). The way it is now it’s likely that bugs are
             * shadowed by this.
             */
            return new Point(0, 0);
        }
    }
}
