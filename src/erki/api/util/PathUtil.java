package erki.api.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class provides utility methods that deal with file system paths.
 * 
 * @author Edgar Kalkowski
 */
public class PathUtil {
    
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
