package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import erki.api.plot.drawables.FixedTickWidthPositiveXAxisWithArrow;
import erki.api.plot.drawables.FixedTickWidthPositiveYAxisWithArrow;

public class BaMixture {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Einfluss des Mischkoeffizienten");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(500, 500));
        plot.addDrawable(new FixedTickWidthPositiveXAxisWithArrow(0.1));
        plot.addDrawable(new FixedTickWidthPositiveYAxisWithArrow(0.1));
        plot.addDrawable(new MixtureInfluenceFunction(0.2));
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
