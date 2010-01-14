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

import java.util.TreeMap;

/**
 * An abstract storage facility that contains some data and persistently backs it to a file. The
 * actual format the data is stored in is specified by the subclasses. The data is accessible via
 * unique instances of {@link Key}.
 * 
 * @author Edgar Kalkowski
 * @param <E>
 *        The enum type that contains the identifiers used for the {@link Key}s of this storage
 *        facility.
 */
public abstract class Storage<E extends Enum<E>> {
    
    private static final long serialVersionUID = -1339066671328991003L;
    
    protected TreeMap<Key<?, E>, Object> data = new TreeMap<Key<?, E>, Object>();
    
    protected final String filename;
    
    /**
     * Create a new Storage that backs the contained data to the specified file. If the file already
     * contains stored information that information is loaded initially.
     * 
     * @param filename
     *        The filename that shall contain all the information stored to this storage facility.
     */
    public Storage(String filename) {
        this.filename = filename;
        load();
    }
    
    /**
     * Add information to this storage facility.
     * 
     * @param <T>
     *        The type of the new data.
     * @param key
     *        The key under which the new data shall be stored.
     * @param value
     *        The actual data to store.
     */
    public <T> void add(Key<T, E> key, T value) {
        data.put(key, value);
        save();
    }
    
    /**
     * Access stored information.
     * 
     * @param <T>
     *        The type of data to receive.
     * @param key
     *        The key under which the data is stored.
     * @return The stored data or {@code null} if either {@code key} was mapped to {@code null}
     *         previously or this storage facility does not contain any data stored under the given
     *         key.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Key<T, E> key) {
        /*
         * This cast is safe as data can only be added via #add(Key, Object) which ensures that the
         * types match.
         */
        return (T) data.get(key);
    }
    
    /**
     * Backup all stored data to {@link #filename}. Every time this method is called all data must
     * be written to the file. This is not very performant but I think it’s enough for now.
     * <p>
     * To keep this storage facility thread-safe implementations of subclasses must make sure that
     * all direct accesses to {@link #data} are synchronized using {@code data} as monitor object.
     */
    protected abstract void save();
    
    /**
     * Load stored data from {@link #filename}. This method is called only once from
     * {@link #Storage(String)} and should not be used but only be defined by subclasses.
     */
    protected abstract void load();
}
