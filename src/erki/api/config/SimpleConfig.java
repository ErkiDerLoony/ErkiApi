/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

/**
 * This configuration backs it’s options to a simple .ini like structured file.
 * 
 * @author Edgar Kalkowski
 */
public class SimpleConfig extends Config {
    
    private File file;
    
    /**
     * Prevent others from instanciating this class. The configuration must be created by
     * {@link #load(File)}.
     */
    private SimpleConfig(File file) {
        this.file = file;
    }
    
    @Override
    public <T> void add(String group, Key<T> key, T option) throws FileNotFoundException,
            UnsupportedEncodingException {
        
        // Additionally check if this configuration can store the specified option.
        if (key.getKey().startsWith("[") && option.toString().endsWith("]")) {
            throw new IllegalArgumentException("This implementation cannot store this option "
                    + "because the key starts with ‘[’ and the value ends with ‘]’!");
        }
        
        if (key.getKey().contains(" = ")) {
            throw new IllegalArgumentException("This implementation cannot store this option "
                    + "because the key contains the special character sequence “ = ”!");
        }
        
        if (!option.getClass().getCanonicalName().equals(Double.class.getCanonicalName())
                && !option.getClass().getCanonicalName().equals(Float.class.getCanonicalName())
                && !option.getClass().getCanonicalName().equals(String.class.getCanonicalName())
                && !option.getClass().getCanonicalName().equals(Integer.class.getCanonicalName())) {
            throw new IllegalArgumentException("The type “" + option.getClass().getCanonicalName()
                    + "” is not supported by this implementation!");
        }
        
        super.add(group, key, option);
    }
    
    /**
     * Creates a new {@code SimpleConfig} that initially contains no options.
     * 
     * @param file
     *        The file to which the configuration is stored later.
     */
    public static void create(File file) {
        instance = new SimpleConfig(file);
    }
    
    /**
     * Loads the configuration options from a specific file.
     * 
     * @param file
     *        The file that contais the configuration options.
     * @throws IOException
     *         If the file cannot be read for some reason.
     */
    public static void load(File file) throws IOException {
        instance = new SimpleConfig(file);
        
        BufferedReader fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(file),
                "UTF-8"));
        String line, group = null;
        
        while ((line = fileIn.readLine()) != null) {
            
            if (line.startsWith("[") && line.endsWith("]")) {
                group = line.substring(1, line.length() - 1);
                instance.groups.put(group, new TreeMap<Key<?>, Option<?>>());
            } else if (line.contains(" = ")) {
                String key = line.substring(0, line.indexOf(" = "));
                String type = key.substring(key.lastIndexOf('<') + 1, key.length() - 1);
                key = key.substring(0, key.lastIndexOf('<'));
                String value = line.substring(line.indexOf(" = ") + 3, line.length());
                
                if (type.equals(Double.class.getCanonicalName())) {
                    instance.groups.get(group).put(new Key<Double>(key),
                            new Option<Double>(Double.parseDouble(value)));
                } else if (type.equals(Float.class.getCanonicalName())) {
                    instance.groups.get(group).put(new Key<Float>(key),
                            new Option<Float>(Float.parseFloat(value)));
                } else if (type.equals(Integer.class.getCanonicalName())) {
                    instance.groups.get(group).put(new Key<Integer>(key),
                            new Option<Integer>(Integer.parseInt(value)));
                } else if (type.equals(String.class.getCanonicalName())) {
                    instance.groups.get(group).put(new Key<String>(key), new Option<String>(value));
                } else {
                    throw new IllegalArgumentException("The type “" + type
                            + "” is not supported by this implementation!");
                }
            }
        }
        
        fileIn.close();
    }
    
    @Override
    public void save() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter fileOut = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file,
                false), "UTF-8"), false);
        
        for (String group : groups.keySet()) {
            fileOut.println("[" + group + "]");
            
            for (Key<?> key : groups.get(group).keySet()) {
                fileOut.println(key.getKey() + "<"
                        + groups.get(group).get(key).get().getClass().getCanonicalName() + "> = "
                        + groups.get(group).get(key).get());
            }
            
            fileOut.println();
        }
        
        fileOut.close();
    }
}
