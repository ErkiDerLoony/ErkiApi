package erki.api.config;

import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;

public class SimpleConfig extends Config {
    
    public SimpleConfig(File file) throws JDOMException, IOException {
        super(file);
    }
    
    @Override
    protected void load(File file) {
    }
    
    @Override
    public boolean save() {
        return false;
    }
}
