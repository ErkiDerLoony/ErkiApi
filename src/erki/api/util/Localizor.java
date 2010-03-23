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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.TreeMap;

/**
 * A simple class that allows localization of an application.
 * <p>
 * The localized strings must be contained in files or subfolders of a root localization folder
 * {@link #getLocaleRoot()}. This folder defaults to “i18n” and can be changed via
 * {@link #setLocaleRoot(File)}.
 * <p>
 * In the root localization folder this class either expects files named after the corresponding
 * locale like {@code de_DE} or {@code en_US} (as returned by {@link Locale#toString()} or likewise
 * named folders. If this class finds files it reads the localized strings directly from the files.
 * If it finds folders it reads the localized strings from every file (no matter which name)
 * contained in the specific folder.
 * <p>
 * Every line in a file that has been detected to contain localized strings that does not start with
 * the character “#” and that contains the character “=“ is considered a definition. A definition
 * consists of a unique keyword that is used in source code to identify the string, the character
 * “=” and the localized version of the string. As the character “=” is used as a separator of keys
 * and values here it must not be contained in any key.
 * <p>
 * The strings in the localization file are not trimmed when read from the file as it may happen
 * that some string needs a prepended or appended whitespace. Thus the “=” character should be
 * directly between the keys and values (like in “key=value”, NOT like in “key = value”).
 * <p>
 * This class does no longer follow the singleton design pattern because now an enum is used to
 * identify the translatable strings. To give easy access to the localized strings in a client
 * program it may however be useful to encapsulate a Localizor instance that is parametrized with a
 * specific enum type in a singleton class.
 * 
 * @author Edgar Kalkowski
 */
public class Localizor<E extends Enum<E>> {
    
    /** The mapping of keys to localized strings. */
    private TreeMap<String, String> map = new TreeMap<String, String>();
    
    /**
     * Create a new Localizor.
     * 
     * @param locale
     *        The desired locale to load.
     * @throws LocalizationException
     *         if no mapping file or folder for the requested locale could be found.
     */
    public Localizor(Locale locale) {
        this(locale, "i18n");
    }
    
    /**
     * Create a new Localizor.
     * 
     * @param locale
     *        The locale to load.
     * @param localeRoot
     *        The root directory which shall be searched for files/folders with translations in it.
     * @throws LocalizationException
     *         if no mapping file or folder for the requested locale could be found.
     */
    public Localizor(Locale locale, String localeRoot) throws LocalizationException {
        File root = PathUtil.getProgramRoot();
        
        if (root == null) {
            throw new LocalizationException(
                    "Could not locate localization folder: Invalid protocol!s");
        } else {
            readFile(new File(root.getAbsolutePath() + File.separator + localeRoot));
        }
    }
    
    /**
     * Reads localized strings from a file. If the file is in fact a folder call
     * {@link #readFile(File)} recursively for each file contained in that folder.
     * 
     * @param file
     *        The file to process. Make sure {@link File#exists()} returns {@code true} for the
     *        delivered file!
     */
    private void readFile(File file) {
        
        if (file.isFile()) {
            
            try {
                BufferedReader fileIn = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));
                String line;
                
                while ((line = fileIn.readLine()) != null) {
                    
                    if (!line.startsWith("#") && line.contains("=")) {
                        
                        if (map.containsKey(line.substring(0, line.indexOf('=')))) {
                            throw new LocalizationException("Duplicate mapping for key “"
                                    + line.substring(0, line.indexOf('=')) + "”!");
                        } else {
                            map.put(line.substring(0, line.indexOf('=')), line.substring(line
                                    .indexOf('=') + 1));
                        }
                    }
                }
                
            } catch (IOException e) {
                throw new LocalizationException("Error while parsing localization file " + file
                        + "!", e);
            }
            
        } else if (file.isDirectory()) {
            
            for (File f : file.listFiles()) {
                readFile(f);
            }
            
        } else {
            throw new LocalizationException(
                    "The locale file to parse is neither a file nor a directory!");
        }
    }
    
    /**
     * Parses the locale in string representation (like “de_DE”) into an instance of {@link Locale}.
     * 
     * @param locale
     *        The locale in string representation (for example as submitted via the command line).
     * @return An instance of {@link Locale} representing the given {@code locale}.
     * @throws IllegalArgumentException
     *         if the delivered string could not be parsed into a locale.
     */
    public static Locale parseLocale(String locale) throws IllegalArgumentException {
        
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
            throw new IllegalArgumentException("Could not parse locale (" + locale + ")!");
        }
    }
    
    /**
     * Get the traslation for a string. The translation may contain several variabled denoted by
     * {$0}, {$1} and so on. The strings submitted via this method’s {@code params} parameter will
     * be inserted at these positions. {$0} will be substituted by {@code params[0]} and so on.
     * 
     * @param id
     *        The id of the string to translate.
     * @param params
     *        Values for the parameters to be inserted into the translated string.
     * @return The translated string with all parameters inserted.
     */
    public String get(E id, String... params) {
        String string = map.get(id.toString());
        
        for (int i = 0; i < params.length && id != null && string != null; i++) {
            string = string.replaceAll("\\{\\$" + i + "\\}", params[i]);
        }
        
        return string == null ? "»" + id + "«" : string;
    }
}
