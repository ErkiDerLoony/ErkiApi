package erki.api.lcars;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * Tests the LCARS api using a {@link FlowLayout} for lying out the test
 * components.
 * 
 * @author Edgar Kalkowski
 */
public class FlowLayoutTest {
    
    /**
     * Starts the test.
     * 
     * @param args
     *        Command line arguments. Not used in this case.
     */
    public static void main(String[] args) {
        LCARSFrame frame = new LCARSFrame("Testfenster");
        frame.setDefaultCloseOperation(LCARSFrame.EXIT_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new FlowLayout());
        
        cp.add(new JButton("Testbutton 1"));
        cp.add(new JButton("Testbutton 2"));
        cp.add(new JTextArea(2, 5));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
