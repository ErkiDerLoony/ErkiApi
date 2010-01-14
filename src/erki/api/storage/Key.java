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

import java.io.Serializable;

/**
 * A unique key that is used in {@link Storage} to identify some data object.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the data object identified by this key.
 */
/*
 * The generic parameter T is not needed by this class but is essential to enforce that only
 * type-fitting pairs of keys and options are stored using the add method in
 * erki.api.storage.Storage.
 */
public class Key<T, E extends Enum<E>> implements Comparable<Key<T, E>>, Serializable {
    
    private static final long serialVersionUID = 8367334391369284855L;
    
    private final E id;
    
    /**
     * Create a new key with a unique identifier.
     * 
     * @param id
     *        The identifier of this key.
     */
    public Key(E id) {
        this.id = id;
    }
    
    /**
     * Access the encapsulated string that uniquely identifies this key.
     * 
     * @return The string that identifies this key.
     */
    public E getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Key<?, ?> && ((Key<?, ?>) other).getId().equals(id);
    }
    
    @Override
    public String toString() {
        return id.toString();
    }
    
    @Override
    public int compareTo(Key<T, E> o) {
        return id.compareTo(o.getId());
    }
}
