package erki.api.plot.style;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Used by {@link StyleProvider} to store the types of style constants along with them.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the style constant stored under this key.
 */
public class StyleKey<T> implements Comparable<StyleKey<T>> {
    
    private final StyleConstants id;
    
    /**
     * Create a new StyleKey that refers to a specific {@link StyleConstants}.
     * 
     * @param id
     *        The StyleConstant this key belongs to.
     */
    public StyleKey(StyleConstants id) {
        this.id = id;
    }
    
    /** Make keys with the same id actually the same for {@link HashMap}s. */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    /** Make keys with the same id actually the same for {@link TreeMap}s. */
    @Override
    public int compareTo(StyleKey<T> o) {
        return id.compareTo(o.id);
    }
    
    /** Make keys with the same id actually the same for e.g. {@link Map#containsKey(Object)}. */
    @Override
    public boolean equals(Object other) {
        
        if (other instanceof StyleKey<?> && id.equals(((StyleKey<?>) other).id)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return id.toString();
    }
}
