package erki.api.plot.style;

import java.util.Map;
import java.util.TreeMap;

import org.jfree.ui.Drawable;

import erki.api.plot.Plot2d;

/**
 * Provides basic styles needed by the basic gui functionality. For special purposes these style may
 * be overridden and additional style constants may be provided.
 * 
 * @author Edgar Kalkowski
 */
public class StyleProvider {
    
    private Map<StyleKey<?>, Object> mapping = new TreeMap<StyleKey<?>, Object>();
    
    /**
     * Create a new StyleProvider that contains basic constants needed by {@link Plot2d}. Everything
     * needed by custom {@link Drawable}s should ge into subclasses.
     */
    public StyleProvider() {
    }
    
    /**
     * Add new style constants to the internal mapping. If the mapping already contains a value for
     * some key that value is overwritten. This makes it easy for subclasses to redefine style
     * mappings from superclasses.
     */
    public <T> void put(StyleKey<T> key, T value) {
        mapping.put(key, value);
    }
    
    /** Access style constants stored in this StyleProvider. */
    @SuppressWarnings("unchecked")
    public <T> T get(StyleKey<T> key) {
        
        if (mapping.containsKey(key)) {
            // This cast is save if elements are only added via #put(StyleKey, Object)
            return (T) mapping.get(key);
        } else {
            throw new IllegalArgumentException(key + " is not defined in this StyleProvider!");
        }
    }
}
