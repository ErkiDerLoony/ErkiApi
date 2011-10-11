/*
 * © Copyright 2007–2011 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
 * 
 * This file is part of Erki’s API.
 * 
 * Erki’s API is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */
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
        
        JOptionPane.showMessageDialog(parent, message, "Fatal error", JOptionPane.ERROR_MESSAGE);
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
