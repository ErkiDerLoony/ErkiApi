package erki.api.plot.em;

import java.awt.geom.Point2D;

public class LabeledPattern extends Point2D.Double {
    
    private static final long serialVersionUID = 8880527065626852954L;
    
    private int label;
    
    public LabeledPattern(double x, double y, int label) {
        super(x, y);
        this.label = label;
    }
    
    public int getLabel() {
        return label;
    }
}
