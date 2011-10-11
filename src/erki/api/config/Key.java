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

/**
 * A key that can be used to identify a configuration option.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the option identified by this key.
 */
/*
 * The generic parameter is not needed by this class but is essential to enforce that only
 * type-fitting pairs of keys and options are stored using the add method in erki.api.config.Config.
 */
public final class Key<T> implements Comparable<Key<T>> {
    
    private final String id;
    
    /**
     * Create a new key with a unique string identifier.
     * 
     * @param id
     *        The string that uniquely identifies this key.
     */
    public Key(String id) {
        this.id = id;
    }
    
    /**
     * Access the encapsulated string that uniquely identifies this key.
     * 
     * @return The string that identifies this key.
     */
    public String getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Key<?> && ((Key<?>) other).getId().equals(id);
    }
    
    @Override
    public String toString() {
        return id;
    }
    
    @Override
    public int compareTo(Key<T> o) {
        return id.compareTo(o.getId());
    }
}
