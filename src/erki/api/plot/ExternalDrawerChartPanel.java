package erki.api.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Locale;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.ResourceBundleWrapper;

import erki.api.plot.drawables.Drawable;
import erki.api.plot.style.StyleConstants;
import erki.api.plot.style.StyleKey;
import erki.api.plot.style.StyleProvider;

/**
 * Customized version of JFreeCharts {@link ChartPanel} that allows to integrate custom external
 * drawers.
 * 
 * @author Edgar Kalkowski
 */
public class ExternalDrawerChartPanel extends ChartPanel {
    
    private static final long serialVersionUID = -3599901722130990810L;
    
    private StyleProvider styleProvider;
    
    private CoordinateTransformer transformer;
    
    private Plot2d plot;
    
    /**
     * Create a new ExternalDrawerChartPanel for a specific {@link JFreeChart}.
     * 
     * @param chart
     *        The chart that shall be displayed in this panel.
     * @param transformer
     *        The coordinate transformer handed to the external drawers that allows them to
     *        transform coordinates into and from JFreeChart’s coordinate system.
     * @param plot
     *        A reference to the Plot2d instance this panel belongs to. The external drawers that
     *        are displayed on this panel are actually retrieved from the Plot2d instance.
     * @param styleProvider
     *        The StyleProvider handed to the external drawers.
     */
    public ExternalDrawerChartPanel(JFreeChart chart, CoordinateTransformer transformer,
            Plot2d plot, StyleProvider styleProvider) {
        /*
         * Do not use a drawing buffer because otherwise the chart stuff (like coordinate axes etc.)
         * would not be exported to pdf as vector graphics but rather as raster graphics!
         */
        super(chart, false);
        this.styleProvider = styleProvider;
        this.transformer = transformer;
        this.plot = plot;
        
        // Make menus appear in English.
        // This is a little bit of a hack but why ever getBundle(String, Locale) does not work ...
        Locale.setDefault(Locale.ENGLISH);
        localizationResources = ResourceBundleWrapper
                .getBundle("org.jfree.chart.LocalizationBundle");
        System.out.println(localizationResources.getLocale());
        setPopupMenu(createPopupMenu(true, true, true, true, true));
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        /*
         * This check and cast is done here because 1. in practice g always is a Graphics2D instance
         * and 2. Graphics2D is much more convenient for drawing and we will need this cast to be
         * done eventually so why not do it here at a central place.
         */
        if (!(g instanceof Graphics2D)) {
            throw new IllegalArgumentException("Fatal drawing error!");
        }
        
        Graphics2D g2 = (Graphics2D) g;
        
        // enable/disable antialiasing for all the external drawers
        boolean antialiasing = styleProvider.get(new StyleKey<Boolean>(
                StyleConstants.ANTIALIASING_ENABLED));
        
        if (antialiasing) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            g2
                    .setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_OFF);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        
        /*
         * As access to the field drawers is not (and should not be) synchronized this is needed to
         * prevent ModificationExceptions. This might mean that a drawer is called once again after
         * it already had been removed from the drawer list but this is acceptable I think.
         */
        Drawable[] dArray = plot.getDrawers().toArray(new Drawable[0]);
        
        for (Drawable drawer : dArray) {
            drawer.draw(g2, transformer);
        }
    }
}
