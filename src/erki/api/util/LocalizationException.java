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

package erki.api.util;

/**
 * This exception indicates that an error occurred while parsing files that contain localized
 * strings.
 * 
 * @author Edgar Kalkowski
 */
public class LocalizationException extends RuntimeException {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = 7472689261506022430L;
    
    /**
     * Creates a new {@code LocaleNotFoundException} with no detailed message.
     */
    public LocalizationException() {
        super();
    }
    
    /**
     * Creates a new {@code LocaleNotFoundException} with {@code message} as it's detailed message.
     * 
     * @param message
     *        The detailed message of this exception.
     */
    public LocalizationException(String message) {
        super(message);
    }
    
    /**
     * Creates a new {@code LocaleNotFoundException} with {@code cause} being the cause of the
     * exception. This cause can later be retrieved by the {@link Throwable#getCause()} method.
     * 
     * @param cause
     *        The cause of this exception.
     */
    public LocalizationException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Creates a new {@code LocaleNotFoundException} with a detailed message and a cause later to be
     * examined via {@link Throwable#getCause()}.
     * 
     * @param message
     *        The detailed message of this exception.
     * @param cause
     *        The cause of this exception.
     */
    public LocalizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
