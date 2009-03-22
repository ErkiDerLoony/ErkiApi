package erki.api.util;

/**
 * This exception indicates that an error occurred while parsing the command line arguments of a
 * program (see {@link CommandLineParser#parse(String[])}).
 * 
 * @author Edgar Kalkowski
 */
public class ParseException extends RuntimeException {
    
    private static final long serialVersionUID = 2216941691767737413L;
    
    /**
     * @see RuntimeException#RuntimeException()
     */
    public ParseException() {
        super();
    }
    
    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public ParseException(String message) {
        super(message);
    }
    
    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public ParseException(Throwable cause) {
        super(cause);
    }
    
    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
