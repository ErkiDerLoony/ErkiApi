package erki.api.plot.em;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import erki.api.plot.Plot2D;
import erki.api.plot.drawables.LineAxes;
import erki.api.plot.style.BasicStyleProvider;
import erki.api.plot.style.StyleProvider;

public class AnotherTest extends JFrame {
    
    private static final long serialVersionUID = -4233652568256122383L;
    
    private SlidingWindow<LabeledPattern> window = new SlidingWindow<LabeledPattern>(
            1000);
    
    private boolean killed = false;
    
    public AnotherTest() {
        setTitle("Test of the plot api");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        final Plot2D plot = new Plot2D();
        StyleProvider styleProvider = new BasicStyleProvider();
        plot.addZoom();
        plot.addMove();
        plot.add(new LineAxes(styleProvider));
        plot.add(new PatternDrawer(window, styleProvider));
        cp.add(plot, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel();
        cp.add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setLayout(new GridBagLayout());
        
        final JButton button = new JButton("Start");
        controlPanel.add(button);
        
        button.addActionListener(new ActionListener() {
            
            private Random rnd = new Random();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (button.getText().equals("Start")) {
                    killed = false;
                    button.setText("Stop");
                    
                    new Thread("Pattern Thread") {
                        
                        public void run() {
                            
                            while (!killed) {
                                
                                window.addPattern(new LabeledPattern(rnd
                                        .nextGaussian() + 5.0, rnd
                                        .nextGaussian() + 5.0, 2));
                                plot.repaint();
                                
                                try {
                                    Thread.sleep(3);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                        
                    }.start();
                    
                } else if (button.getText().equals("Stop")) {
                    button.setText("Start");
                    killed = true;
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                killed = true;
            }
        });
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        new AnotherTest().setVisible(true);
    }
}
