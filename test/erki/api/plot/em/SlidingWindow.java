package erki.api.plot.em;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindow<T> implements Iterable<T> {
    
    private ConcurrentLinkedQueue<T> patterns = new ConcurrentLinkedQueue<T>();
    
    private int limit;
    
    public SlidingWindow(int limit) {
        this.limit = limit;
    }
    
    public void addPattern(T pattern) {
        patterns.offer(pattern);
        
        if (patterns.size() > limit) {
            patterns.poll();
        }
    }
    
    public int size() {
        return patterns.size();
    }
    
    public void clear() {
        patterns.clear();
    }
    
    @Override
    public Iterator<T> iterator() {
        return patterns.iterator();
    }
}
