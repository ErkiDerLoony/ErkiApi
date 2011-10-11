/*
 * © Copyright 2007–2011 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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

import java.awt.Point;
import java.awt.geom.Point2D;

public class CoordinateTransformer {
    
    private double minXCart;
    private double maxXCart;
    private double minYCart;
    private double maxYCart;
    
    private int screenWidth;
    private int screenHeight;
    
    public CoordinateTransformer(double minXCart, double maxXCart, double minYCart,
            double maxYCart, int screenWidth, int screenHeight) {
        this.minXCart = minXCart;
        this.maxXCart = maxXCart;
        this.minYCart = minYCart;
        this.maxYCart = maxYCart;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public Point getScreenCoordinates(Point2D.Double point) {
        int x = (int) (((point.x - minXCart) / (maxXCart - minXCart)) * screenWidth);
        int y = (int) (((point.y - maxYCart) / (minYCart - maxYCart)) * screenHeight);
        return new Point(x, y);
    }
    
    public Point2D.Double getCarthesianCoordinates(Point point) {
        double x = minXCart + ((point.getX() / screenWidth) * (maxXCart - minXCart));
        double y = maxYCart - ((point.getY() / screenHeight) * (maxYCart - minYCart));
        return new Point2D.Double(x, y);
    }
    
    public void setScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
    
    public void setCarthesianCoordinates(double minX, double maxX, double minY, double maxY) {
        minXCart = minX;
        maxXCart = maxX;
        minYCart = minY;
        maxYCart = maxY;
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
    
    public double getCartMinX() {
        return minXCart;
    }
    
    public double getCartMaxX() {
        return maxXCart;
    }
    
    public double getCartMinY() {
        return minYCart;
    }
    
    public double getCartMaxY() {
        return maxYCart;
    }
}
