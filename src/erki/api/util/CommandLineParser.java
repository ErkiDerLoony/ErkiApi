/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under the terms of the GNU
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

import java.util.TreeMap;

/**
 * This class represents a parser for command lines. It takes the {@code String[]} of the main
 * method and parses it into a {@link TreeMap} mapping parameters to their values.
 * 
 * @author Edgar Kalkowski
 */
public class CommandLineParser {
    
    /**
     * Parse command line arguments. This method accepts the following option constellations:
     * <ul>
     * <li>Short options with only a key and no value (just a switch), e.g. {@code -b}.
     * <li>Short options with a key that consists of only one character and a value directly
     * attached to the key, e.g. {@code -b233}.
     * <li>Short options with a key and the value in the next argument (separated by a space on the
     * command line), e.g. {@code -b 233}.
     * <li>Long options with only a key and no value (long form of a switch), e.g. {@code --foo-bar}.
     * <li>Long options with a key and a value attached to the key with a equal sign as separator,
     * e.g. {@code --foo-bar=asdf}.
     * <li>Long options with a key and the value in the next argument, e.g. {@code --foo-bar asdf}.
     * </ul>
     * All values are returned as strings regardless if the actual command line options is a number.
     * If an options is a simple switch without a value the corresponding key is mapped to {@code
     * null} in the returned mapping.
     * 
     * @param args
     *        The command line arguments taken directly from the main method.
     * @throws ParseException
     *         If an error occurs while parsing the command line arguments, e.g. the list of
     *         arguments contains a string that does not begin with a dash (“-”) at a position where
     *         a key is expected.
     * @return A {@link TreeMap} containing parameters and their values.
     */
    public static TreeMap<String, String> parse(String[] args) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            
            if (arg.startsWith("--")) {
                
                if (arg.contains("=")) {
                    String k = arg.substring(0, arg.indexOf('='));
                    String v = arg.substring(arg.indexOf('=') + 1);
                    map.put(k, v);
                } else {
                    
                    if (args[i + 1].startsWith("-")) {
                        map.put(arg, null);
                    } else {
                        map.put(arg, args[i + 1]);
                        i++;
                    }
                }
                
            } else if (arg.startsWith("-")) {
                
                if (arg.length() > 2) {
                    String k = arg.substring(0, 2);
                    String v = arg.substring(2);
                    map.put(k, v);
                } else {
                    
                    if (args[i + 1].startsWith("-")) {
                        map.put(arg, null);
                    } else {
                        map.put(arg, args[i + 1]);
                        i++;
                    }
                }
                
            } else {
                throw new ParseException("I expected a key but got " + arg + "!");
            }
        }
        
        return map;
    }
}
