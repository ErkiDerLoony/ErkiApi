package erki.api.plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Plot2D extends JPanel {
    
    private static final long serialVersionUID = 7903143787970361747L;
    
    private final Collection<Drawable> drawables = new LinkedList<Drawable>();
    
    // private double minX, maxX, minY, maxY;
    
    private final CoordinateTransformer transformer;
    
    public Plot2D(String title, double minX, double maxX, double minY,
            double maxY) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 100));
        // this.minX = minX;
        // this.maxX = maxX;
        // this.minY = minY;
        // this.maxY = maxY;
        transformer = new CoordinateTransformer(minX, maxX, minY, maxY,
                getPreferredSize().width, getPreferredSize().height);
        
        addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                transformer.setScreenSize(Plot2D.this.getWidth(), Plot2D.this
                        .getHeight());
            }
        });
    }
    
    public Plot2D(String title) {
        this(title, -1.0, 1.0, -1.0, 1.0);
    }
    
    public Plot2D() {
        this("", -1.0, 1.0, -1.0, 1.0);
    }
    
    /** @return See {@link Collection#add(Object)}. */
    public boolean addDrawable(Drawable drawable) {
        return drawables.add(drawable);
    }
    
    /** @return See {@link Collection#remove(Object)}. */
    public boolean removeDrawable(Drawable drawable) {
        return drawables.remove(drawable);
    }
    
    public CoordinateTransformer getCoordinateTransformer() {
        return transformer;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2;
        
        if (g instanceof Graphics2D) {
            g2 = (Graphics2D) g;
        } else {
            throw new IllegalStateException("Fatal drawing error!");
        }
        
        for (Drawable drawable : drawables) {
            drawable.draw(g2, transformer);
        }
    }
}
