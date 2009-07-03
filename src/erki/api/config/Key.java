package erki.api.config;

/**
 * A key that can be used to identify a configuration option.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the option identified by this key.
 */
/*
 * The generic parameter is not needed by this class but is essential to enforce that only
 * type-fitting pairs of keys and options are stored using the add method in erki.api.config.Config.
 */
public final class Key<T> implements Comparable<Key<T>> {
    
    private final String id;
    
    /**
     * Create a new key with a unique string identifier.
     * 
     * @param id
     *        The string that uniquely identifies this key.
     */
    public Key(String id) {
        this.id = id;
    }
    
    /**
     * Access the encapsulated string that uniquely identifies this key.
     * 
     * @return The string that identifies this key.
     */
    public String getKey() {
        return id;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object other) {
        
        if (other instanceof Key && ((Key) other).id.equals(id)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return id;
    }
    
    @Override
    public int compareTo(Key<T> o) {
        return id.compareTo(o.getKey());
    }
}
