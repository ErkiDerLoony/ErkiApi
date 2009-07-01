package erki.api.config;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * An abstract configuration that contains a map of configuration entries and backs them to a file.
 * The actual format the is read and written from/to the file is specified by subclasses. The
 * implementation follows the singleton design pattern to make sure the configuration options are
 * available throughout the entire program.
 * 
 * @author Edgar Kalkowski
 */
public abstract class Config {
    
    /** The one and only instance of this class. */
    private static Config instance = null;
    
    private Map<String, Map<String, Entry<?>>> groups = new TreeMap<String, Map<String, Entry<?>>>();
    
    /** Prevent others from instanciating this class. */
    protected Config(File file) {
        load(file);
    }
    
    protected Config() {
    }
    
    public static Config getInstance() {
        
        if (instance == null) {
            
        }
        
        return instance;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(String group, String option) {
        
        if (groups.containsKey(group)) {
            Map<String, Entry<?>> options = groups.get(group);
            
            if (options.containsKey(option)) {
                return ((Entry<T>) options.get(option)).get();
            } else {
                throw new RuntimeException("There is no option “" + option + "” in group “" + group
                        + "”!");
            }
            
        } else {
            throw new RuntimeException("There is no configuration group “" + group + "”!");
        }
    }
    
    /**
     * This implementation silently overrides already existent values.
     * 
     * @param <T>
     * @param group
     * @param option
     * @param value
     */
    public <T> void addOption(String group, String option, T value) {
        
        if (groups.containsKey(group)) {
            Map<String, Entry<?>> options = groups.get(group);
            options.put(option, new Entry<T>(value));
        }
    }
    
    /**
     * Stores the current configuration values to the file. There is no need to call this method
     * externally because in the current implementation it is called every time something about the
     * configuration is changed to ensure consitency.
     * 
     * @return {@code true} if the configuration was successfully saved and {@code false} if for
     *         some reason the configuration could not be saved.
     */
    public abstract boolean save();
    
    /** Loads the initial configuration entries from the file. */
    protected abstract void load(File file);
}
