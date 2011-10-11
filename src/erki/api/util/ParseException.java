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
 * This exception indicates that an error occurred while parsing the command line arguments of a
 * program (see {@link CommandLineParser#parse(String[])}).
 * 
 * @author Edgar Kalkowski
 */
public class ParseException extends RuntimeException {
    
    private static final long serialVersionUID = 2216941691767737413L;
    
    /**
     * @see RuntimeException#RuntimeException()
     */
    public ParseException() {
        super();
    }
    
    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public ParseException(String message) {
        super(message);
    }
    
    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public ParseException(Throwable cause) {
        super(cause);
    }
    
    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
