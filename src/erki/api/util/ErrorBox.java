package erki.api.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * This class uses {@link JOptionPane} to display java exceptions.
 * 
 * @author Edgar Kalkowski
 */
public class ErrorBox {
    
    public static void showExceptionBox(Component parent, Throwable e) {
        String message = e.toString();
        
        for (StackTraceElement s : e.getStackTrace()) {
            message += "\n\tat " + s.toString();
        }
        
        message = appendCause(message, e);
        
        JOptionPane.showMessageDialog(parent, message, "Fatal error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private static String appendCause(String message, Throwable e) {
        
        if (e.getCause() != null) {
            Throwable f = e.getCause();
            message += "\nCaused by: " + f.toString();
            
            for (StackTraceElement s : f.getStackTrace()) {
                message += "\n\tat" + s.toString();
            }
            
            message = appendCause(message, f);
        }
        
        return message;
    }
}
