/*
 * © Copyright 2007–2010 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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
