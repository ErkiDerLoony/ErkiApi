/*
 * © Copyright 2007–2011 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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

package erki.api.config;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
    protected static Config instance = null;
    
    protected Map<String, Map<Key<?>, Option<?>>> groups = new TreeMap<String, Map<Key<?>, Option<?>>>();
    
    /** Prevent others from instanciating this class. */
    protected Config() {
    }
    
    public static Config getInstance() {
        
        if (instance == null) {
            throw new IllegalStateException("The configuration has not been initialized! You "
                    + "have to call the static load(File) method of one of the subclasses of "
                    + "Config!");
        }
        
        return instance;
    }
    
    /**
     * Check if this configuration contains a specific option.
     * 
     * @param group
     *        The group of the option to query.
     * @param key
     *        The key of the option to query.
     * @return {@code true} if this configuration contains the specified option, {@code false}
     *         otherwise.
     */
    public boolean contains(String group, Key<?> key) {
        return groups.containsKey(group) && groups.get(group).containsKey(key);
    }
    
    /**
     * Retrieve options from this configuration.
     * 
     * @param <T>
     *        The data type of the option.
     * @param group
     *        The group the option belongs to.
     * @param key
     *        The key under which the option is stored.
     * @return The value of the option described by the given parameters.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String group, Key<T> key) {
        
        if (groups.containsKey(group)) {
            Map<Key<?>, Option<?>> options = groups.get(group);
            
            if (options.containsKey(key)) {
                return (T) options.get(key).get();
            } else {
                throw new RuntimeException("There is no option “" + key + "” in group “" + group
                        + "”!");
            }
            
        } else {
            throw new RuntimeException("There is no configuration group “" + group + "”!");
        }
    }
    
    /**
     * Add new options to this configuration. This implementation silently overrides already
     * existent values.
     * 
     * @param <T>
     *        The type of option that shall be stored.
     * @param group
     *        The group the option belongs to.
     * @param key
     *        The key the option is stored under.
     * @param option
     *        The value of the option.
     * @throws UnsupportedEncodingException
     *         If “UTF-8” is not supported on your system (which is required as the config file is
     *         stored in that encoding).
     * @throws FileNotFoundException
     *         If the configuration could not be updated in the file because the file was not found
     *         and could not be created.
     */
    /*
     * The generic typing of this method enforces that only type-fitting pairs of keys and values
     * are stored to the option map.
     */
    public <T> void add(String group, Key<T> key, T option) throws FileNotFoundException,
            UnsupportedEncodingException {
        
        if (!groups.containsKey(group)) {
            Map<Key<?>, Option<?>> map = new TreeMap<Key<?>, Option<?>>();
            groups.put(group, map);
        }
        
        Map<Key<?>, Option<?>> options = groups.get(group);
        options.put(key, new Option<T>(option));
        save();
    }
    
    /**
     * Delete an option from a group. If the group does not contain an option with the specified key
     * or the configuration does not contain the specified group nothing happens.
     * 
     * @param <T>
     * @param group
     * @param key
     */
    public void remove(String group, Key<?> key) {
        
        if (groups.containsKey(group)) {
            groups.get(group).remove(key);
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
    public abstract void save() throws FileNotFoundException, UnsupportedEncodingException;
}
