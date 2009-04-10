package erki.api.plot.mandelbrot;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import erki.api.plot.Plot2D;
import erki.api.plot.action.Move;
import erki.api.plot.action.Zoom;

public class SwingUI extends JFrame {
    
    private static final long serialVersionUID = 7048546481180869995L;
    
    public SwingUI() {
        setTitle("Mandelbrot");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Plot2D plot = new Plot2D(-2, 1, -1, 1);
        plot.add(new Move(MouseEvent.BUTTON1));
        plot.add(new Zoom());
        plot.add(new Mandelbrot());
        getContentPane().add(plot);
        
        pack();
    }
    
    public static void main(String[] args) {
        SwingUI frame = new SwingUI();
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
