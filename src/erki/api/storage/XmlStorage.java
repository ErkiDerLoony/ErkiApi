/*
 * © Copyright 2007–2010 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
 * 
 * This file is part of Erki’s API.
 * 
 * Erki’s API is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
                    .fromXML(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
        } catch (FileNotFoundException e) {
            // That’s not too bad because the file will be generated eventually.
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("It seems your system does not support UTF-8 encoding "
                    + "which is required by this application!", e);
        }
    }
    
    @Override
    protected void save() {
        
        try {
            new XStream(new DomDriver()).toXML(data, new OutputStreamWriter(new FileOutputStream(
                    filename), "UTF-8"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not store data!", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("It seems your system does not support UTF-8 encoding "
                    + "which is required by this application!", e);
        }
    }
}
