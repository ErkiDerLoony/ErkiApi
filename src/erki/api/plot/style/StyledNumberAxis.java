package erki.api.plot.style;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.axis.NumberAxis;

import erki.api.plot.Plot2d;

/**
 * The default axis used as both domain and range axis by {@link Plot2d}.
 * 
 * @author Edgar Kalkowski
 */
public class StyledNumberAxis extends NumberAxis {
    
    private static final long serialVersionUID = -6508522350620494981L;
    
    /**
     * Create a new DefaultNumberAxis.
     * 
     * @param styleProvider
     *        The style provider that defines how the new axis shall look.
     */
    public StyledNumberAxis(StyleProvider styleProvider) {
        super();
        setAutoRangeIncludesZero(false);
        setTickLabelFont(styleProvider.get(new StyleKey<Font>(StyleConstants.AXES_TICK_FONT)));
        setTickLabelPaint(styleProvider
                .get(new StyleKey<Color>(StyleConstants.AXES_TICK_FONT_COLOR)));
        setAxisLinePaint(styleProvider.get(new StyleKey<Color>(StyleConstants.AXES_COLOR)));
    }
}
