package erki.api.plot.style;

import java.awt.Color;
import java.awt.Stroke;

/**
 * Identifiers for the different style constants defined in the {@link StyleProvider} hierarchy. As
 * the user does only implicitly know about the type of the style constants returned for these keys
 * it might be wise to include the type in the javadoc description.
 * 
 * @author Edgar Kalkowski
 */
public enum StyleConstants {
    
    // BEGIN: Basic constants needed by Plot2d.
    
    /** This instance of {@link Color} defines the background colour of the plot. */
    PLOT_BACKGROUND_COLOR,

    /**
     * This instance of {@link Boolean} indicates whether or not gridlines shall be displayed by the
     * plot.
     */
    GRID_VISIBLE,

    /**
     * This instance of {@link Color} defines the colour of the gridlines. Note that the gridlines
     * are only shown if {@link #GRID_VISIBLE} is set to {@code true}.
     */
    GRID_COLOR,

    /**
     * This instance of {@link Stroke} defines the stroke used for the gridlines. Note that the
     * gridlines are only shown if {@link #GRID_VISIBLE} is set to {@code true}.
     */
    GRID_STROKE,

    /**
     * This instance of {@link Boolean} indicates whether or not the plot shall be drawn
     * antialiased. This setting also works for the external drawers that can be added to the plot.
     */
    ANTIALIASING_ENABLED
    
    // END: Basic constants needed by Plot2d.
}
