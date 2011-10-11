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

import java.util.Comparator;
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
     * <p>
     * All keys in the returned mapping are <emph>not</emph> stripped of their leading one or two
     * dashes.
     * <p>
     * A key may be given multiple times on the command line, e.g. to specify multiple files. In
     * this case the returned mapping will contain <emph>one</emph> key and all the multiple values
     * in one string separated by null bytes. Thus the multiple values can easily be obtained by
     * {@code map.get("option").split("\0")}.
     * <p>
     * After all options a list of e.g. filenames may follow. So if a command line argument does not
     * start with a dash (“-”) where a key is expected the algorithm assumes that the list of
     * options is over a a final list of strings (e.g. filenames) follows. These final list of
     * strings is returned as the value of the special key {@code null} as one string with null
     * bytes separating the list entries. Thus the list can easily be obtained e.g. via {@code
     * map.get(null).split("\0")}. If the last option shall be without a value then “--” may be
     * specified to explicitely end the options and parse everything into the final list from there
     * on.
     * 
     * @param args
     *        The command line arguments taken directly from the main method.
     * @return A {@link TreeMap} containing parameters and their values.
     */
    public static TreeMap<String, String> parse(String[] args) {
        
        // Create a mapping that allows for null keys.
        TreeMap<String, String> map = new TreeMap<String, String>(new Comparator<String>() {
            
            @Override
            public int compare(String o1, String o2) {
                
                if (o1 == null && o2 == null) {
                    return 0;
                }
                
                if (o1 == null) {
                    return Integer.MIN_VALUE;
                }
                
                if (o2 == null) {
                    return Integer.MAX_VALUE;
                }
                
                return o1.compareTo(o2);
            }
        });
        
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            
            if (arg.equals("--")) {
                continue;
            }
            
            if (arg.startsWith("--")) {
                // Parse long options.
                
                if (arg.contains("=")) {
                    String k = arg.substring(0, arg.indexOf('='));
                    String v = arg.substring(arg.indexOf('=') + 1);
                    put(map, k, v);
                } else {
                    
                    if (args.length == i + 1 || args[i + 1].startsWith("-")
                            || args[i + 1].equals("--")) {
                        put(map, arg, null);
                    } else {
                        put(map, arg, args[i + 1]);
                        i++;
                    }
                }
                
            } else if (arg.startsWith("-")) {
                // Parse short options.
                
                if (arg.length() > 2) {
                    String k = arg.substring(0, 2);
                    String v = arg.substring(2);
                    put(map, k, v);
                } else {
                    
                    if (args.length == i + 1 || args[i + 1].startsWith("-")
                            || args[i + 1].equals("--")) {
                        put(map, arg, null);
                    } else {
                        put(map, arg, args[i + 1]);
                        i++;
                    }
                }
                
            } else {
                // Parse final list of strings (e.g. filenames).
                
                String list = "";
                
                for (int j = i; j < args.length; j++) {
                    list += args[j] + "\0";
                }
                
                list = list.substring(0, list.length() - 1);
                put(map, null, list);
                break;
            }
        }
        
        return map;
    }
    
    /**
     * Checks if {@code key} already exists in the map. If so the value is appended to {@code
     * map.get(key)} (separated by {@code \0}). Otherwise the mapping {@code key} -> {@code value}
     * is added to the map.
     */
    private static void put(TreeMap<String, String> map, String key, String value) {
        
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + "\0" + value);
        }
    }
}
