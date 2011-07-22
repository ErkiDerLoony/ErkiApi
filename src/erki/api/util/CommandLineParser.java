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

package erki.api.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class parses command line arguments. Accepted formats are:
 * <ul>
 * <li>Short options with only a key and no value (just a switch), e.g. {@code -b}.
 * <li>Short options with a key that consists of only one character and a value directly attached to
 * the key, e.g. {@code -b233}.
 * <li>Short options with a key and the value in the next argument (separated by a space on the
 * command line), e.g. {@code -b 233}.
 * <li>Long options with only a key and no value (long form of a switch), e.g. {@code --foo-bar}.
 * <li>Long options with a key and a value attached to the key with a equal sign as separator, e.g.
 * {@code --foo-bar=asdf}.
 * <li>Long options with a key and the value in the next argument, e.g. {@code --foo-bar asdf}.
 * </ul>
 * This class provides convenience methods to easily access the option switches given on the command
 * line. If an option switch is present multiple times in the command line the latter value
 * overrides the previous ones.
 * <p>
 * After the last option switch a list of e.g. filenames may follow which can be accessed by
 * {@link #getList()}. To prevent the algorithm from taking the first list item as the value of the
 * last option switch a “ -- ” may be provided to clearly separate the option switches from the list
 * of following arguments.
 * 
 * @author Edgar Kalkowski
 */
public class CommandLineParser {
    
    private HashMap<String, String> map = new HashMap<String, String>();
    
    private LinkedList<String> list = new LinkedList<String>();
    
    /**
     * Create a new CommandLineParser.
     * 
     * @param args
     *        The command line arguments to parse.
     */
    public CommandLineParser(String[] args) {
        
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            
            if (arg.equals("--")) {
                continue;
            }
            
            if (arg.startsWith("--")) {
                // Parse long options.
                arg = arg.substring(2);
                
                if (arg.contains("=")) {
                    String k = arg.substring(0, arg.indexOf('='));
                    String v = arg.substring(arg.indexOf('=') + 1);
                    map.put(k, v);
                } else {
                    
                    if (args.length == i + 1 || args[i + 1].startsWith("-")
                            || args[i + 1].equals("--")) {
                        map.put(arg, null);
                    } else {
                        map.put(arg, args[i + 1]);
                        i++;
                    }
                }
                
            } else if (arg.startsWith("-")) {
                // Parse short options.
                arg = arg.substring(1);
                
                if (arg.length() > 1) {
                    String k = arg.substring(0, 1);
                    String v = arg.substring(1);
                    map.put(k, v);
                } else {
                    
                    if (args.length == i + 1 || args[i + 1].startsWith("-")
                            || args[i + 1].equals("--")) {
                        map.put(arg, null);
                    } else {
                        map.put(arg, args[i + 1]);
                        i++;
                    }
                }
                
            } else {
                // Parse final list of strings (e.g. filenames).
                
                for (int j = i; j < args.length; j++) {
                    list.add(args[j]);
                }
                
                break;
            }
        }
    }
    
    /**
     * Access the list of arguments that occurred after the last switch.
     * 
     * @return A list of final arguments or an empty list if there was no list of arguments.
     */
    public LinkedList<String> getList() {
        return list;
    }
    
    /**
     * Check whether or not some option switch is present in this command line.
     * 
     * @param keys
     *        Multiple keys which shall be tested (e.g. a short option “l” and a long option
     *        “logfile”) without leading dashes.
     * @return {@code true} if at least one of the given options is present or {@code false} if none
     *         of the given options are present.
     */
    public boolean contains(String... keys) {
        
        for (String key : keys) {
            
            if (map.containsKey(key)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Access the value of a command line option switch but do not remove it from the internal
     * mapping.
     * 
     * @param keys
     *        Option switches after which the desired value was given on the command line without
     *        leading dashes. If there are multiple keys given (e.g. a short option “l” and a long
     *        option “logfile”) the value of the latter always overrides the values of previous
     *        keys.
     * @return The value of the last option switch for which a value was stored or {@code null} if
     *         either none of the given option switches is present in this command line or they were
     *         switches without values after them.
     */
    public String peek(String... keys) {
        String result = null;
        
        for (String key : keys) {
            
            if (contains(key)) {
                result = map.get(key);
            }
        }
        
        return result;
    }
    
    /**
     * This does the same as {@link #peek(String...)} except it removes the returned value from the
     * internal mapping.
     */
    public String pop(String... keys) {
        String result = null;
        
        for (String key : keys) {
            
            if (contains(key)) {
                result = map.get(key);
                map.remove(key);
            }
        }
        
        return result;
    }
    
    /**
     * Access the key set of the internal mapping of option switches to their values.
     * 
     * @return The set of option switches present in this command line.
     */
    public Set<String> keySet() {
        return map.keySet();
    }
}
