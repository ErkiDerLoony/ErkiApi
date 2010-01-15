package erki.api.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.TreeMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This storage facility backs it’s data to an xml file using the XStream library.
 * 
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 * @param <E>
 *        The enum class from which the unique identifiers for the stored objects are taken.
 */
public class XmlStorage<E extends Enum<E>> extends Storage<E> {
    
    /**
     * Create a new XmlStorage that backs it’s data to the specified file.
     * 
     * @param filename
     *        The file to which all data is saved.
     */
    public XmlStorage(String filename) {
        super(filename);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void load() {
        
        try {
            // This cast is safe because this is what’s stored in #save().
            data = (TreeMap<Key<?, E>, Object>) new XStream(new DomDriver())
                    .fromXML(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            // That’s not too bad because the file will be generated eventually.
        }
    }
    
    @Override
    protected void save() {
        
        try {
            new XStream(new DomDriver()).toXML(data, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not store data!", e);
        }
    }
}
