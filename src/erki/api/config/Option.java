package erki.api.config;

/**
 * A wrapper for options stored in configurations.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the option value stored in this instance.
 */
/*
 * The wrapper is needed to ensure that only type-fitting pairs of keys and values are stored in the
 * configuration (see add method in erki.api.config.Config).
 */
public class Option<T> {
    
    private T value;
    
    /**
     * Create a new option wrapper.
     * 
     * @param value
     *        The value stored in this wrapper.
     */
    public Option(T value) {
        this.value = value;
    }
    
    /**
     * Access the wrapped value.
     * 
     * @return The value stored in this wrapper.
     */
    public T get() {
        return value;
    }
}
