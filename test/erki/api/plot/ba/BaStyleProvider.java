package erki.api.plot.ba;

import java.awt.BasicStroke;
import java.awt.Stroke;

import erki.api.plot.style.DefaultStyleProvider;
import erki.api.plot.style.StyleProperty;
import erki.api.plot.style.StylePropertyKey;

public class BaStyleProvider extends DefaultStyleProvider {
    
    public BaStyleProvider() {
        addMapping(new StylePropertyKey<Stroke>("ALTERNATE_LINE_STROKE"),
                new StyleProperty<BasicStroke>(new BasicStroke(1.25f,
                        BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
                        new float[] { 5.0f, 5.0f }, 0.0f)));
    }
}
