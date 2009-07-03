package erki.api.config;

import java.io.File;

public class JavaObjectConfig extends Config {
    
    private JavaObjectConfig(File file) {
        
    }
    
    public static void load(File file) {
        instance = new JavaObjectConfig(file);
        throw new Error("Not yet implemented.");
    }
    
    @Override
    public void save() {
        throw new Error("Not yet implemented.");
    }
}
