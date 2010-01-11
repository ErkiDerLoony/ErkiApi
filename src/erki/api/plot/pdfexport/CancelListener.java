package erki.api.plot.pdfexport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This listener is called if the user cancels the pdf export (see {@link GeneralOptionPanel}).
 * 
 * @author Edgar Kalkowski
 */
public class CancelListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        SophisticatedPdfExport.dialog.dispose();
        SophisticatedPdfExport.dialog = null;
    }
}
