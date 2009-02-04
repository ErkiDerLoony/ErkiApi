package erki.api.plot;

import java.awt.Point;
import java.awt.geom.Point2D;

import erki.api.util.Log;

public class CoordinateTransformer {
    
    private double minXCart;
    private double maxXCart;
    private double minYCart;
    private double maxYCart;
    
    private int screenWidth;
    private int screenHeight;
    
    public CoordinateTransformer(double minXCart, double maxXCart,
            double minYCart, double maxYCart, int screenWidth, int screenHeight) {
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
        Log.debug(this, "Transformed (" + point.getX() + ", " + point.getY()
                + ") into screen coordinates (" + x + ", " + y + ").");
        return new Point(x, y);
    }
    
    public Point2D.Double getCarthesianCoordinates(Point point) {
        double x = minXCart
                + ((point.getX() / screenWidth) * (maxXCart - minXCart));
        double y = maxYCart
                - ((point.getY() / screenHeight) * (maxYCart - minYCart));
        Log.debug(this, "Transformed (" + point.x + ", " + point.y
                + ") into carthesian coordinates (" + x + ", " + y + ").");
        return new Point2D.Double(x, y);
    }
    
    public void setScreenSize(int width, int height) {
        Log.debug(this, "Registered new screen size: (" + width + ", " + height
                + ").");
        screenWidth = width;
        screenHeight = height;
    }
    
    public void setCarthesianCoordinates(double minX, double maxX, double minY,
            double maxY) {
        Log.debug(this, "Registered new size of cartisian coordinate system: ("
                + minX + ", " + maxX + "), (" + minY + ", " + maxY + ").");
        minXCart = minX;
        maxXCart = maxX;
        minYCart = minY;
        maxYCart = maxY;
    }
}
