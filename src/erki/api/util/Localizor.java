/*
 * (c) Copyright 2008 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.TreeMap;

/**
 * A simple class that allows localization of an application. As this class
 * follows the singleton design pattern, the localization mechanism can be
 * easily accessed from every location of the application's source without the
 * need of passing references through constructors.
 * <p>
 * The localized strings are read from a file located at the subfolder {@code
 * locale/<locale>} of the program root directory where {@code <locale>} is the
 * two letter code of the used locale (for example »de_DE«) as returned by
 * {@link Locale#toString()}. Every line in that file that does not start with
 * the character »#« is considered a definition. A definition consists of a
 * unique keyword that is used in source code to identify the string, the
 * character »=« and the localized version of the string. As the character »=«
 * is used as a separator of keys and values here it must not be contained in
 * any key.
 * <p>
 * The strings in the localization file are not trimmed when read from the file
 * as it may happen that some string needs a prepended or appended whitespace.
 * Thus the »=« character should be directly between the keys and values (like
 * in »key=value«, NOT like in »key = value«).
 * 
 * @author Edgar Kalkowski
 */
public class Localizor {
    
    /** The one and only instance of the {@code Localizor}. */
    private static Localizor instance;
    
    /** The mapping of keys to localized strings. */
    private TreeMap<String, String> map = new TreeMap<String, String>();
    
    /**
     * The private constructor to conform to the singleton pattern.
     * 
     * @param locale
     *        The locale to load.
     * @throws LocaleNotFoundException
     *         if no mapping file for the requested locale could be found. So
     *         before calling please make sure via the
     *         {@link Localizor#isValidLocale(Locale)} method that a mapping
     *         file exists.
     */
    private Localizor(Locale locale) throws LocaleNotFoundException {
        
        try {
            BufferedReader fileIn = new BufferedReader(new InputStreamReader(
                    new FileInputStream("locale"
                            + System.getProperty("file.separator")
                            + locale.toString()), "UTF-8"));
            String line;
            
            while ((line = fileIn.readLine()) != null) {
                
                if (!line.startsWith("#") && line.contains("=")) {
                    map.put(new String(line.substring(0, line.indexOf('='))
                            .getBytes(), Charset.defaultCharset()), new String(
                            line.substring(line.indexOf('=') + 1).getBytes(),
                            Charset.defaultCharset()));
                }
            }
            
        } catch (FileNotFoundException e) {
            throw new LocaleNotFoundException(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * This method may be used to check if a mapping file for a specific locale
     * file exists for this program.
     * 
     * @param locale
     *        The {@link Locale} to check.
     * @return {@code true} if a mapping file for the requested locale exists.<br />
     *         {@code false} otherwise.
     */
    public static boolean isValidLocale(Locale locale) {
        File file = new File("locale" + System.getProperty("file.separator")
                + locale.toString());
        return file.isFile();
    }
    
    /**
     * Parses the locale in string representation (like de_DE) into an instance
     * of {@link Locale}.
     * 
     * @param locale
     *        The locale in string representation (for example as submitted via
     *        the command line).
     * @return A {@link Locale} representing {@code locale}.
     */
    public static Locale parseLocale(String locale) {
        
        if (locale.length() > 5) {
            String language = locale.substring(0, 2);
            String country = locale.substring(3, 5);
            String variant = locale.substring(6);
            return new Locale(language, country, variant);
        } else if (locale.length() > 2) {
            String language = locale.substring(0, 2);
            String country = locale.substring(3);
            return new Locale(language, country);
        } else if (locale.length() > 1) {
            String language = locale;
            return new Locale(language);
        } else {
            throw new IllegalArgumentException("Could not parse locale ("
                    + locale + ")!");
        }
    }
    
    /**
     * Get the traslation for a string. The translation may contain several
     * variabled denoted by {$0}, {$1} and so on. The strings submitted via this
     * method's {@code params} parameter will be inserted at these positions.
     * {$0} will be substituted by {@code params[0]} and so on.
     * 
     * @param id
     *        The id of the string to translate.
     * @param params
     *        Values for the parameters to be inserted into the translated
     *        string.
     * @return The translated string with all parameters inserted.
     */
    public String get(String id, String... params) {
        String string = map.get(id);
        
        for (int i = 0; i < params.length; i++) {
            string = string.replaceAll("\\{\\$" + i + "\\}", params[i]);
        }
        
        return string == null ? "<BROKEN TRANSLATION FOR KEY " + id + ">"
                : string;
    }
    
    /**
     * Loads a new locale.
     * 
     * @param locale
     *        The new locale to load.
     * @return A reference to the new {@code Localizor} object representing the
     *         new locale.
     * @throws LocaleNotFoundException
     *         If no mapping file could be found for the requested locale.
     *         Please make sure before calling this method with a locale as
     *         parameter that a mapping file exists via the
     *         {@link Localizor#isValidLocale(Locale)} method.
     */
    public static Localizor newInstance(Locale locale)
            throws LocaleNotFoundException {
        instance = new Localizor(locale);
        return instance;
    }
    
    /**
     * Loads the default locale as returned by {@link Locale#getDefault()}.
     * 
     * @return A reference to the new {@code Localizor} object representing the
     *         default locale.
     * @throws LocaleNotFoundException
     *         If no mapping file for the default locale could be found. Please
     *         make sure such a file exists by asserting that {@code
     *         isValidLocale(java.util.Locale.getDefault())} returns {@code
     *         true}.
     */
    public static Localizor newInstance() throws LocaleNotFoundException {
        instance = new Localizor(Locale.getDefault());
        return instance;
    }
    
    /**
     * Returns the current {@code Localizor} instance or creates a new one if no
     * such instance exists so far. If no instance exists so far the default
     * locale as returned by {@link Locale#getDefault()} is used.
     * 
     * @return A reference to the new {@code Localizor} object representing the
     *         default locale.
     * @throws LocaleNotFoundException
     *         If no mapping file for the default locale could be found. Please
     *         make sure such a file exists by asserting that {@code
     *         isValidLocale(java.util.Locale.getDefault())} returns {@code
     *         true}.
     */
    public static Localizor getInstance() throws LocaleNotFoundException {
        
        if (instance == null) {
            instance = new Localizor(Locale.getDefault());
        }
        
        return instance;
    }
}
