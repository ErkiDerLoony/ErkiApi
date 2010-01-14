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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

/**
 * This implementation of {@link Storage} stores data through Java’s serialization mechanism.
 * 
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 * @param <E>
 *        The enum type used as identifiers for the {@link Key}s of this storage facility.
 */
public class JavaObjectStorage<E extends Enum<E>> extends Storage<E> {
    
    /**
     * Create a new JavaObjectStorage that backs it’s data to the specified file. If the given file
     * already exists and contains stored information that information is loaded initially.
     * 
     * @param filename
     *        The filename to which all data shall be stored.
     */
    public JavaObjectStorage(String filename) {
        super(filename);
    }
    
    @SuppressWarnings("unchecked")
    protected void load() {
        
        try {
            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(filename));
            // This cast is ok because #save() only stores TreeMaps.
            data = (TreeMap<Key<?, E>, Object>) fileIn.readObject();
            fileIn.close();
        } catch (FileNotFoundException e) {
            // No stored information found.
        } catch (IOException e) {
            throw new RuntimeException("An i/o error occurred while trying to load data from "
                    + filename + ". This could mean that the user accidentally gave the name of "
                    + "some other (perhaps important?) file! I cannot continue and probably "
                    + "overwrite that file.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("When trying to load data from " + filename
                    + " some class file was not found. This could mean that the file contains "
                    + "serialized Java objects from some different application (which may be "
                    + "important?). I cannot continue and probably overwrite that file.", e);
        }
    }
    
    @Override
    protected void save() {
        
        try {
            ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(filename));
            
            synchronized (data) {
                fileOut.writeObject(data);
            }
            
            fileOut.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not store data!", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not store data!", e);
        }
    }
}
