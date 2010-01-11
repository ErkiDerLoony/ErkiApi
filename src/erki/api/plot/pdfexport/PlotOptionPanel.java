package erki.api.plot.pdfexport;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import erki.api.plot.Plot2d;
import erki.api.plot.drawables.Drawable;

/**
 * This panel contains the options for one Plot2d whose drawers were added to
 * {@link SophisticatedPdfExport}.
 * 
 * @author Edgar Kalkowski
 */
public class PlotOptionPanel extends JPanel implements ActionListener {
    
    private static final long serialVersionUID = -6632199360899078914L;
    
    private JPanel parentPanel;
    
    /*
     * Store the currently active drawers separately so we can only remove the active drawers from
     * the accumulating plot if all drawers of a plot are removed at once using the “Remove” button.
     * Otherwise we might accidentally remove a drawer of some other plot (if one plot was added
     * multiple times to the pdf export).
     */
    private LinkedList<Drawable> active = new LinkedList<Drawable>();
    
    private Plot2d accumulationPlot;
    
    /**
     * Create a new PlotOptionPanel for a specific plot.
     * 
     * @param parentPanel
     *        The panel that contains this option panel (the left JPanel of
     *        {@link SophisticatedPdfExport}). It is needed to remove this PlotOptionPanel from it
     *        if the user removes all drawers of the plot this panel belongs to.
     * @param accumulationPlot
     *        The Plot2d that accumulates all drawers for pdf export. Drawers are dynamically added
     *        to and removed from this plot as the user checks or unchecks the corresponding
     *        checkboxes.
     * @param drawers
     *        A list of all available drawers of the plot this panel belongs to. For each drawer in
     *        this list a checkbox will be displayed that allows the user to enable or disable that
     *        drawer.
     */
    public PlotOptionPanel(JPanel parentPanel, final Plot2d accumulationPlot,
            LinkedList<Drawable> drawers) {
        this.parentPanel = parentPanel;
        this.accumulationPlot = accumulationPlot;
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new TitledBorder("Plot options"));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel boxPanel = new JPanel();
        boxPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));
        boxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(boxPanel);
        
        for (final Drawable drawer : drawers) {
            final JCheckBox checkBox = new JCheckBox(drawer.getClass().getSimpleName());
            checkBox.setSelected(true);
            accumulationPlot.add(drawer);
            active.add(drawer);
            boxPanel.add(checkBox);
            
            checkBox.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    if (checkBox.isSelected()) {
                        accumulationPlot.add(drawer);
                        active.add(drawer);
                    } else {
                        accumulationPlot.remove(drawer);
                        active.remove(drawer);
                    }
                }
            });
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(buttonPanel);
        
        JButton button = new JButton("Remove");
        buttonPanel.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
    }
    
    // Remove all active drawers from the accumulationPlot.
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Delete this panel and the little space above.
        for (int i = 0; i < parentPanel.getComponentCount(); i++) {
            Component c = parentPanel.getComponent(i);
            
            if (c.equals(this)) {
                parentPanel.remove(i - 1);
                parentPanel.remove(i - 1);
            }
        }
        
        parentPanel.revalidate();
        parentPanel.repaint();
        
        for (Drawable drawer : active) {
            accumulationPlot.remove(drawer);
        }
    }
}
