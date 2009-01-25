package erki.api.lcars;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PackTest {
    
    public static void main(String[] args) {
        LCARSFrame frame = new LCARSFrame("Testframe");
        frame.setDefaultCloseOperation(LCARSFrame.EXIT_ON_CLOSE);
        
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        
        frame.addLCARSButton(new ButtonBarButton("Start"));
        frame.addLCARSButton(new ButtonBarButton("Pause"));
        frame.addLCARSButton(new ButtonBarButton("Ende", LCARSUtil.RED,
                LCARSUtil.RED_BRIGHT));
        
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.setPreferredSize(new Dimension(150, 150));
        content.setOpaque(false);
        
        LCARSButton button = new LCARSButton();
        content.add(button);
        button = new LCARSButton("Knopf Nr. 2");
        button.setBackground(LCARSUtil.RED);
        content.add(button);
        
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MARK!!");
            }
        });
        
        cp.add(content, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
