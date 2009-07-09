package erki.api.plot;


/**
 * Implemented by coordinate axes that require {@link Plot2D#autorange()} to leave an empty area at
 * the edge of the plot where only the coordinate axes are drawn.
 * <p>
 * <b>Attention</b>: All the returned values are in screen pixel coodinates!
 * 
 * @author Edgar Kalkowski
 */
public interface CoordinateAxis {
    
    /**
     * The offset required at the left edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getLeftOffset();
    
    /**
     * The offset required at the right edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getRightOffset();
    
    /**
     * The offset at the top edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getTopOffset();
    
    /**
     * The offset at the bottom edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getBottomOffset();
    
}
