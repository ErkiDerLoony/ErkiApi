package erki.api.plot.pdfexport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import erki.api.plot.RenderingInfoAndAutoRangingXYPlot;

/**
 * This listener toggles the visibility of the plot in {@link GeneralOptionPanel}.
 * 
 * @author Edgar Kalkowski
 */
public class TogglePlotListener implements ActionListener {
    
    private RenderingInfoAndAutoRangingXYPlot plot;
    
    private JCheckBox checkBox;
    
    /**
     * Create a new TogglePlotListener for a specific plot.
     * 
     * @param plot
     *        The accumulating plot instance whose visibility shall be toggled.
     * @param checkBox
     *        The check box that triggers the toggling. If this checkbox is selected the plot is
     *        drawn. If it’s not selected the plot is not drawn.
     */
    public TogglePlotListener(RenderingInfoAndAutoRangingXYPlot plot, JCheckBox checkBox) {
        this.plot = plot;
        this.checkBox = checkBox;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (checkBox.isSelected()) {
            plot.setOutlineVisible(true);
            plot.getDomainAxis().setVisible(true);
            plot.getRangeAxis().setVisible(true);
            plot.setDomainGridlinesVisible(true);
            plot.setRangeGridlinesVisible(true);
        } else {
            plot.setOutlineVisible(false);
            plot.getDomainAxis().setVisible(false);
            plot.getRangeAxis().setVisible(false);
            plot.setDomainGridlinesVisible(false);
            plot.setRangeGridlinesVisible(false);
        }
    }
}
