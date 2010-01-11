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

package erki.api.config;

/**
 * A wrapper for options stored in configurations.
 * 
 * @author Edgar Kalkowski
 * @param <T>
 *        The type of the option value stored in this instance.
 */
/*
 * The wrapper is needed to ensure that only type-fitting pairs of keys and values are stored in the
 * configuration (see add method in erki.api.config.Config).
 */
public class Option<T> {
    
    private T value;
    
    /**
     * Create a new option wrapper.
     * 
     * @param value
     *        The value stored in this wrapper.
     */
    public Option(T value) {
        this.value = value;
    }
    
    /**
     * Access the wrapped value.
     * 
     * @return The value stored in this wrapper.
     */
    public T get() {
        return value;
    }
}
