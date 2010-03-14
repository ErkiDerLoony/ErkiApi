package erki.api.plot.style;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.axis.DateAxis;

/**
 * Extends {@link DateAxis} to use certain style properties defined by {@link StyleProvider} (or
 * subclasses).
 * 
 * @author Edgar Kalkowski
 */
public class StyledDateAxis extends DateAxis {
    
    private static final long serialVersionUID = 958360884300014268L;
    
    /**
     * Create a new DefaultDateAxis.
     * 
     * @param styleProvider
     *        The style provider that determines how the new date axis shall look.
     */
    public StyledDateAxis(StyleProvider styleProvider) {
        super();
        setTickLabelFont(styleProvider.get(new StyleKey<Font>(StyleConstants.AXES_TICK_FONT)));
        setTickLabelPaint(styleProvider
                .get(new StyleKey<Color>(StyleConstants.AXES_TICK_FONT_COLOR)));
        setAxisLinePaint(styleProvider.get(new StyleKey<Color>(StyleConstants.AXES_COLOR)));
    }
}
