/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.util;

import java.util.TreeMap;

/**
 * This class represents a parser for command lines. It takes the {@code
 * String[]} of the main method and parses it into a {@link TreeMap} mapping
 * parameters to their values.
 * 
 * @author Edgar Kalkowski
 */
public class CommandLineParser {
    
    /**
     * Parse command line arguments. The concatenated arguments are scanned in a
     * unix style way and split up in key/value pairs that are returned in a
     * {@link TreeMap}. The keys may start with either »--« or »-« (e.g.
     * »--help« or »-help«) and the value may be separated from the key by
     * either a space or a »=« (e.g. »--port 7886« or »--port=7886«).
     * 
     * @param args
     *        The command line arguments taken e.g. from the main method.
     * @return A {@link TreeMap} containing parameters and their values.
     */
    public static TreeMap<String, String> parse(String[] args) {
        TreeMap<String, String> results = new TreeMap<String, String>();
        String commandLine = "";
        
        for (String s : args) {
            commandLine += " " + s;
        }
        
        commandLine = commandLine.trim();
        String key = "", value = "";
        boolean parseKey = true;
        
        for (char s : commandLine.toCharArray()) {
            
            if (parseKey) {
                
                if (s == ' ' || s == '=') {
                    parseKey = false;
                    continue;
                }
                
                key += s;
                
            } else {
                
                if (s == ' ') {
                    results.put(key.trim(), value.trim());
                    key = "";
                    value = "";
                    parseKey = true;
                    continue;
                }
                
                value += s;
            }
        }
        
        if (!key.equals("") || !value.equals("")) {
            results.put(key.trim(), value.trim());
        }
        
        return results;
    }
}
