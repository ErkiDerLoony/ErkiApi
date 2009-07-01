package erki.api.config;

public class Entry<T> {
    
    private T value;
    
    public Entry(T value) {
        
    }
    
    public T get() {
        return value;
    }
}
