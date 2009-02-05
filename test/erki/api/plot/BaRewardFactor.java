package erki.api.plot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import erki.api.plot.drawables.FixedTickWidthPositiveXAxisWithArrow;
import erki.api.plot.drawables.FixedTickWidthPositiveYAxisWithArrow;

public class BaRewardFactor {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Initialisierung des Belohnungsfaktors");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        Plot2D plot = new Plot2D();
        plot.setPreferredSize(new Dimension(500, 500));
        plot.addDrawable(new FixedTickWidthPositiveXAxisWithArrow(0.1));
        plot.addDrawable(new FixedTickWidthPositiveYAxisWithArrow(0.01));
        plot.addDrawable(new RewardFactorFunction(0.3, 0.1, 0.12));
        plot.autorange();
        cp.add(plot, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
