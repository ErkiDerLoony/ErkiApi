package erki.api.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class XmlConfig extends Config {
    
    private XmlConfig(File file) {
        
    }
    
    public static void load(File file) {
        instance = new XmlConfig(file);
        throw new Error("Not yet implemented.");
    }
    
    @Override
    public void save() throws FileNotFoundException, UnsupportedEncodingException {
        throw new Error("Not yet implemented.");
    }
}
