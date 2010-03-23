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

package erki.api.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

/**
 * This class provides utility methods that deal with file system paths and to locate files in the
 * file system.
 * 
 * @author Edgar Kalkowski
 */
public class PathUtil {
    
    private static boolean warned = false;
    
    /**
     * Access a file that is located in the program folder. If the program’s root directory could
     * not be determined by {@link #getProgramRoot()} a warning is issued on the {@link Log} the
     * first time that happens and from then on the files are addressed relative to the directory
     * from which the program was started. If you want to suppress that warning at all cost call
     * {@link Log#setLevel(java.util.logging.Level)} with {@link Level#OFF} before calling this
     * method.
     * 
     * @param relativePosition
     *        The position of the file relative to the program folder.
     * @return
     */
    public static File getRessource(String relativePosition) {
        File root = getProgramRoot();
        
        if (warned || root == null) {
            
            if (!warned) {
                Log.warning("Program root directory could not be determined. "
                        + "Using program’s startup directory as fallback.");
                warned = true;
            }
            
            return new File(new File("").getAbsolutePath() + File.separator + relativePosition);
        } else {
            return new File(root.getAbsolutePath() + File.separator + relativePosition);
        }
    }
    
    /**
     * Tries to determine the root directory of the currently running program no matter if it was
     * started directly from a shell or from within a jar file. This is done by a heuristic that may
     * well fail.
     * 
     * @return The root directory of the currently running program or {@code null} if that could not
     *         be determined.
     */
    public static File getProgramRoot() {
        URL url = Localizor.class.getResource("/erki");
        
        if (url.getProtocol().equals("file")) {
            return new File(new File(url.getPath()).getParentFile().getParentFile().getPath());
        } else if (url.getProtocol().equals("jar")) {
            
            String path;
            
            try {
                path = new URL(url.getPath()).getPath();
            } catch (MalformedURLException e) {
                return null;
            }
            
            if (path.contains("!")) {
                path = path.substring(0, path.lastIndexOf("!"));
            }
            
            return new File(new File(path).getParentFile().getPath());
        } else {
            return null;
        }
    }
}
