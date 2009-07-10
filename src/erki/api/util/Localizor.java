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

package erki.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.TreeMap;

/**
 * A simple class that allows localization of an application. As this class follows the singleton
 * design pattern, the localization mechanism can be easily accessed from every location of the
 * application's source without the need of passing references through constructors.
 * <p>
 * The localized strings must be contained in files or subfolders of a root localization folder
 * {@link #getLocaleRoot()} which may be changed via {@link #setLocaleRoot(File)}.
 * <p>
 * In the root localization folder this class either expects files named after the corresponding
 * locale like {@code de_DE} or {@code en_US} (as returned by {@link Locale#toString()} or likewise
 * named folders. If this class finds files it reads the localized strings directly from the files.
 * If it finds folders it reads the localized strings from every file (no matter which name)
 * contained in the specific folder.
 * <p>
 * Every line in a file that has been detected to contain localized strings as described above that
 * does not start with the character “#” and that contains the character “=“ is considered a
 * definition. A definition consists of a unique keyword that is used in source code to identify the
 * string, the character “=” and the localized version of the string. As the character “=” is used
 * as a separator of keys and values here it must not be contained in any key.
 * <p>
 * The strings in the localization file are not trimmed when read from the file as it may happen
 * that some string needs a prepended or appended whitespace. Thus the “=” character should be
 * directly between the keys and values (like in “key=value”, NOT like in “key = value”).
 * 
 * @author Edgar Kalkowski
 */
public class Localizor {
    
    /** The one and only instance of the {@code Localizor}. */
    private static Localizor instance;
    
    /** The mapping of keys to localized strings. */
    private TreeMap<String, String> map = new TreeMap<String, String>();
    
    /** The root folder that contains the localized files (or subfolders containing the files). */
    private static File localeRoot = new File("locale").getAbsoluteFile();
    
    /**
     * The private constructor to conform to the singleton pattern.
     * 
     * @param locale
     *        The locale to load.
     * @throws LocalizationException
     *         if no mapping file or folder for the requested locale could be found.
     */
    private Localizor(Locale locale) throws LocalizationException {
        File file = new File(localeRoot.toString() + System.getProperty("file.separator")
                + locale.toString());
        
        if (file.exists()) {
            readFile(file);
        } else {
            throw new LocalizationException("The locale “" + locale.toString()
                    + "” is not defined for this program!");
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
     * This method may be used to check if a mapping file for a specific locale file exists for this
     * program.
     * 
     * @param locale
     *        The {@link Locale} to check.
     * @return {@code true} if a mapping file or folder for the requested locale exists.<br /> {@code
     *         false} otherwise.
     */
    public static boolean isValidLocale(Locale locale) {
        
        if (localeRoot.isDirectory() && contains(localeRoot.list(), locale)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks whether an array of file names (e.g. as obtained via {@link File#list()}) contains a
     * file or folder that matches the delivered locale (i.e. equals {@link Locale#toString()} for
     * the given {@code Locale}).
     * 
     * @param files
     *        The array of file names to check.
     * @param locale
     *        The locale to search for.
     * @return {@code true} if the delivered list of file names contains a file or folder equal to
     *         {@code locale.toString()}, <br /> {@code false} otherwise.
     */
    private static boolean contains(String[] files, Locale locale) {
        
        for (String file : files) {
            
            if (file.equals(locale.toString())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Parses the locale in string representation (like “de_DE”) into an instance of {@link Locale}.
     * 
     * @param locale
     *        The locale in string representation (for example as submitted via the command line).
     * @return A {@link Locale} representing {@code locale}.
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
     * {$0}, {$1} and so on. The strings submitted via this method's {@code params} parameter will
     * be inserted at these positions. {$0} will be substituted by {@code params[0]} and so on.
     * 
     * @param id
     *        The id of the string to translate.
     * @param params
     *        Values for the parameters to be inserted into the translated string.
     * @return The translated string with all parameters inserted.
     */
    public String get(String id, String... params) {
        String string = map.get(id);
        
        for (int i = 0; i < params.length && id != null && string != null; i++) {
            string = string.replaceAll("\\{\\$" + i + "\\}", params[i]);
        }
        
        return string == null ? "<BROKEN TRANSLATION FOR KEY “" + id + "”>" : string;
    }
    
    /**
     * @return An instance of {@code File} that denotes the currently used root directory for
     *         localization files and/or folders.
     */
    public static File getLocaleRoot() {
        return localeRoot;
    }
    
    /**
     * Change the root folder in which is looked for localization files and/or folders. This method
     * only checks if the abstract path denoted by {@code folder} exists and is a folder. It does
     * not check if it contains any localization files and/or folders and it does not reload the
     * currently used locale.
     * <p>
     * So if you change the localization root folder via this method it's best to do so before
     * {@link #getInstance()} is called for the first time in your program. Otherwise all previous
     * calls will use strings from the old location and may fail if there are no localized strings
     * at the old location. Instead you may call {@link #newInstance(Locale)} with the desired
     * {@code Locale} after changing the localization root folder which will cause a reload of all
     * localized strings.
     * 
     * @param folder
     *        The new folder to use.
     */
    public static void setLocaleRoot(File folder) {
        localeRoot = folder;
    }
    
    /**
     * Loads a new locale.
     * 
     * @param locale
     *        The new locale to load.
     * @return A reference to the new {@code Localizor} object representing the new locale.
     * @throws LocalizationException
     *         If no mapping file could be found for the requested locale. Please make sure before
     *         calling this method with a locale as parameter that a mapping file exists via the
     *         {@link Localizor#isValidLocale(Locale)} method.
     */
    public static Localizor newInstance(Locale locale) throws LocalizationException {
        instance = new Localizor(locale);
        return instance;
    }
    
    /**
     * Loads the default locale as returned by {@link Locale#getDefault()}.
     * 
     * @return A reference to the new {@code Localizor} object representing the default locale.
     * @throws LocalizationException
     *         If no mapping file or folder for the default locale could be found. Please make sure
     *         such a file exists by asserting that {@code
     *         isValidLocale(java.util.Locale.getDefault())} returns {@code true} before calling
     *         this method.
     */
    public static Localizor newInstance() throws LocalizationException {
        instance = new Localizor(Locale.getDefault());
        return instance;
    }
    
    /**
     * Returns the current {@code Localizor} instance or creates a new one if no such instance
     * exists so far. If no instance exists so far the default locale as returned by
     * {@link Locale#getDefault()} is used.
     * 
     * @return A reference to the new {@code Localizor} object representing the current locale.
     * @throws LocalizationException
     *         if no mapping file or folder for the default locale could be found and no other
     *         locale was loaded so far. Please make sure that the default locale is valid by
     *         asserting that {@link #isValidLocale(Locale)} returns {@code true} for the parameter
     *         {@link Locale#getDefault()} or load a different locale via
     *         {@link #newInstance(Locale)} before calling this method.
     */
    public static Localizor getInstance() throws LocalizationException {
        
        if (instance == null) {
            instance = new Localizor(Locale.getDefault());
        }
        
        return instance;
    }
}
