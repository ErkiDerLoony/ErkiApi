/*
 * © Copyright 2007–2011 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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

import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This static class provides a highly dynamic log. One can specify a global log level and also
 * class specific log levels. If a class wants to log an event with a specific level it is first
 * checked if a class specific level was defined for that class. If that is not the case the global
 * log level is considered.
 * <p>
 * The log levels are priorized in such way that events of a certain level are only logged if the
 * considered log level for the logging class is lower or equal to the level of the message to be
 * logged. The priorities are (in descending order)
 * <ul>
 * <li>OFF (highest value)
 * <li>ERROR
 * <li>WARNING
 * <li>INFO
 * <li>DEBUG
 * <li>FINE_DEBUG
 * <li>FINER_DEBUG
 * <li>FINEST_DEBUG (lowest value)
 * </ul>
 * <p>
 * An example: If the global log level is {@link Level#WARNING} and no class specific log levels
 * have been defined all messages with log levels {@link Level#INFO}, {@link Level#DEBUG},
 * {@link Level#FINE_DEBUG} {@link Level#FINER} and {@link Level#FINEST} will be discarded and not
 * actually printed to the log. All {@link Level#WARNING} and {@link Level#ERROR} messages are
 * printed.
 * <p>
 * The log is a simple {@link PrintStream} that defaults to be {@link System#out} and may be changed
 * at runtime e.g. to some log file via {@link #setHandler(PrintStream)}. Be careful to change the
 * log handler before printing anything to the log or otherwise that information may be lost.
 * <p>
 * The log supports colouring which is by default disabled. To enable it use
 * {@link #setUseColours(boolean)}. By default it prints warnings in yellow and errors in red which
 * can be changed via {@link #setColour(Level, AnsiColour)}.
 * <p>
 * All actual logging methods of this class are thread safe.
 * 
 * @author Edgar Kalkowski
 */
public class Log {
    
    /**
     * This enum denotes colours which can be produced by ANSI control codes.
     * 
     * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
     */
    public enum AnsiColour {
        BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE,
        
        BRIGHT_BLACK, BRIGHT_RED, BRIGHT_GREEN, BRIGHT_YELLOW, BRIGHT_BLUE, BRIGHT_MAGENTA, BRIGHT_CYAN, BRIGHT_WHITE;
    }
    
    /** Colour mapping for the different log levels. */
    private static final Map<Level, AnsiColour> COLOURS = new HashMap<Level, AnsiColour>();
    
    static {
        COLOURS.put(Level.FINEST_DEBUG, AnsiColour.BRIGHT_BLACK);
        COLOURS.put(Level.FINER_DEBUG, AnsiColour.BRIGHT_BLACK);
        COLOURS.put(Level.FINE_DEBUG, AnsiColour.BRIGHT_BLACK);
        COLOURS.put(Level.DEBUG, AnsiColour.BRIGHT_BLACK);
        COLOURS.put(Level.INFO, AnsiColour.BRIGHT_GREEN);
        COLOURS.put(Level.WARNING, AnsiColour.BRIGHT_YELLOW);
        COLOURS.put(Level.ERROR, AnsiColour.BRIGHT_RED);
        
        // Log all uncaught exceptions and prevent them from going to System.err.
        UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
            
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.error(e);
            }
        };
        
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
    
    /**
     * This is the handler to which all log output is printed. It defaults to be System.out but may
     * be changed by the user via {@link #setHandler(PrintStream)} (for example to log to a file).
     */
    private static PrintStream handler = System.out;
    
    /**
     * Maps classes to special log levels. This allows to override the default log level for certain
     * classes for example to only get debug output for some classes and not for all.
     */
    private static Map<String, Level> mapping = new TreeMap<String, Level>();
    
    /* Default formats for the log output string. */
    private static final String DEFAULT_FORMAT = "[%H:%M:%S.%m, [%i], %c.%f(%l)] %L: %t";
    private static final String DEFAULT_FORMAT_WITH_DATE = "[%D.%n.%y %H:%M:%S.%m, [%i], %c.%f(%l)] %L: %t";
    
    /** The default format of the log output string. */
    private static String format = DEFAULT_FORMAT;
    
    /** The default log level for all classes for which no special log level is specified. */
    private static Level level = Level.INFO;
    
    /** A lock to synchronize parallel access to the log. */
    private static Object lock = new Object();
    
    /** Indicates whether the output shall be coloured using ANSI control codes. */
    private static boolean useColour = false;
    
    /** A list of unknown escape characters that the used was already warned about. */
    private static LinkedList<Character> warned = new LinkedList<>();
    
    /** Prevent others from instanciating this static class. */
    private Log() {
    }
    
    /**
     * Check whether or not a given level would currently be logged for the calling class.
     * 
     * @param level
     *        The level that shall be checked.
     * @return {@code true} if messages with the given log level would be currently logged or
     *         {@code false} if their log level is too low.
     */
    public static boolean isLoggable(Level level) {
        return isLoggable(new Throwable().getStackTrace()[1].getClassName(), level);
    }
    
    /**
     * Compute whether or not a message of a given level is loggable for a specific class.
     * 
     * @param classname
     *        The fully qualified class name of the class that wants to print something to the log.
     * @param level
     *        The log level of the information to be printed.
     * @return {@code true} if the class is allowed to print the message, {@code false} otherwise.
     */
    private static boolean isLoggable(String classname, Level level) {
        classname = classname.replaceAll("\\$[0-9]*", "");
        
        if (mapping.containsKey(classname)) {
            Level bound = mapping.get(classname);
            
            if (level.ordinal() < bound.ordinal()) {
                return false;
            } else {
                return true;
            }
            
        } else {
            
            if (level.ordinal() < Log.level.ordinal()) {
                return false;
            } else {
                return true;
            }
        }
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINE}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void debug(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.DEBUG)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.DEBUG);
            }
        }
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINE_DEBUG}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void fineDebug(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.FINE_DEBUG)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.FINE_DEBUG);
            }
        }
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINER_DEBUG}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void finerDebug(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.FINER_DEBUG)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.FINER_DEBUG);
            }
        }
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINEST_DEBUG}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void finestDebug(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.FINEST_DEBUG)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.FINEST_DEBUG);
            }
        }
    }
    
    /**
     * Logs a message that is classified as an information. The message is only actually printed to
     * the current handler if the log level is at most {@link Level#INFO}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void info(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.INFO)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.INFO);
            }
        }
    }
    
    /**
     * Logs a message that is classified as a warning. The message is only actually printed to the
     * current handler if the log level for the logging class is at most {@link Level#WARNING}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void warning(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.WARNING)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.WARNING);
            }
        }
    }
    
    /**
     * Logs an exception represented by an instance of {@link Throwable} or any subclass of it
     * including the stacktrace. The message is only actually printed to the current handler if the
     * log level for the logging class is at most {@link Level#SEVERE}.
     * 
     * @param error
     *        The {@link Throwable} object that describes the exception and the stacktrace. Must not
     *        be {@code null}.
     */
    public static void error(Throwable error) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.ERROR)) {
            
            synchronized (lock) {
                log(e, error.toString(), Level.ERROR);
                
                for (StackTraceElement s : error.getStackTrace()) {
                    log(e, "\tat " + s.toString(), Level.ERROR);
                }
                
                logCause(error.getCause(), e);
            }
        }
    }
    
    /**
     * Recursively log the causes of a throwable.
     * 
     * @param cause
     *        The cause of an error that is the starting point for the recursive traversal of the
     *        causes.
     * @param source
     *        The {@code StackTraceElement} from which information about the source of the error is
     *        taken.
     */
    private static void logCause(Throwable cause, StackTraceElement source) {
        
        if (cause != null) {
            log(source, "Caused by: " + cause.toString(), Level.ERROR);
            
            for (StackTraceElement s : cause.getStackTrace()) {
                log(source, "\tat " + s.toString(), Level.ERROR);
            }
            
            logCause(cause.getCause(), source);
        }
    }
    
    /**
     * Logs a message that is classified as an error. Messages of this type are always printed to
     * the log handler except if the log level for the logging class is {@link Level#OFF}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void error(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.ERROR)) {
            
            synchronized (lock) {
                log(e, Objects.toString(line), Level.ERROR);
            }
        }
    }
    
    /**
     * Access the current output handler of the log.
     * 
     * @return The output handler of the log.
     */
    public static PrintStream getHandler() {
        return handler;
    }
    
    /**
     * Change the handler to which all log output is actually printed.
     * 
     * @param handler
     *        The new handler of log messages.
     */
    public static void setHandler(PrintStream handler) {
        Log.handler = handler;
    }
    
    /**
     * Access the current global log level.
     * 
     * @return The global log level.
     */
    public static Level getLevel() {
        return level;
    }
    
    /**
     * Change the global log level to be more or less verbose. The global log level is used for
     * classes for which no class specific log level has been defined.
     * 
     * @param level
     *        The new log level.
     */
    public static void setLevel(Level level) {
        Log.level = level;
    }
    
    /**
     * Access the log level for a specific class. If no specific log level was defined, the global
     * log level is returned.
     * 
     * @param clazz
     *        The class whose log level shall be returned.
     * @return The given class’ log level or the global log level if no specific log level was
     *         defined for the given class.
     */
    public static Level getLevelForClass(Class<?> clazz) {
        String name = clazz.getCanonicalName().replaceAll("\\$[0-9]*", "");
        
        if (mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            return getLevel();
        }
    }
    
    /**
     * Change the log level for specific classes to be more or less verbose.
     * 
     * @param level
     *        The new log level for the given classes.
     * @param classes
     *        The classes whose log level shall be changed.
     */
    public static void setLevelForClasses(Level level, Class<?>... classes) {
        
        for (Class<?> clazz : classes) {
            mapping.put(clazz.getCanonicalName().replaceAll("\\$[0-9]*", ""), level);
        }
    }
    
    /**
     * Check the log level of the calling class.
     * 
     * @return A special log level for the calling class if one was defined (e.g. via
     *         {@link #setLevelForClasses(Level, Class...)}) or the global log level as given by
     *         {@link #getLevel()}.
     */
    public static Level getLevelForMe() {
        String name = new Throwable().getStackTrace()[1].getClassName().replaceAll("\\$[0-9]*", "");
        
        if (mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            return getLevel();
        }
    }
    
    /**
     * Change the log level of the calling class.
     * 
     * @param level
     *        The new log level for the calling class.
     */
    public static void setLevelForMe(Level level) {
        mapping.put(new Throwable().getStackTrace()[1].getClassName().replaceAll("\\$[0-9]*", ""),
                level);
    }
    
    /**
     * Access the current log format.
     * 
     * @return The format that is currently used for log messages.
     */
    public static String getFormat() {
        return format;
    }
    
    /**
     * Change the format of the log output. The following replacements are applied in the given
     * order:
     * <ul>
     * <li>%D → day in two digit notation
     * <li>%n → month in two digit notation
     * <li>%y → year in four digit notation
     * <li>%H → hour in two digit notation
     * <li>%M → minute in two digit notation
     * <li>%S → second in two digit notation
     * <li>%m → millisecond in three digit notation
     * <li>%T → thread name
     * <li>%i → thread id
     * <li>%p → package name
     * <li>%c → class name
     * <li>%f → method name
     * <li>%l → line number
     * <li>%L → message level (see {@link Level}).
     * <li>%t → text of the log message
     * </ul>
     * 
     * @param format
     *        The new log output format.
     */
    public static void setFormat(String format) {
        Log.format = format;
    }
    
    /** Reset the log format to the default format. */
    public static void setFormatDefault() {
        Log.format = DEFAULT_FORMAT;
    }
    
    /** Reset the log format to the default format that includes the date of the log messages. */
    public static void setFormatDefaultWithDate() {
        Log.format = DEFAULT_FORMAT_WITH_DATE;
    }
    
    /**
     * Check whether the log output is coloured using ANSI control codes.
     * 
     * @return {@code true} if the ANSI control codes will be inserted into the log output to colour
     *         the log messages or {@code false} if no colour codes will be included.
     */
    public static boolean isColourUsed() {
        return useColour;
    }
    
    /**
     * Change the colouring behaviour of the log.
     * 
     * @param useColour
     *        If this is {@code true} from this point forward ANSI control codes will be inserted
     *        into the log output to colour the log messages according to their level.
     */
    public static void setColourUsed(boolean useColour) {
        Log.useColour = useColour;
    }
    
    /**
     * Check which colour is used to print messages of a certain level. Be aware that colouring must
     * be enabled ({@link #setColourUsed(boolean)}) for actual colouring to take place.
     * 
     * @param level
     *        The log level whose colour is requested.
     * @return The colour that will be used for messages of the given level or {@code null} if for
     *         that level no colour is specified.
     */
    public static AnsiColour getColour(Level level) {
        return COLOURS.get(level);
    }
    
    /**
     * Change the colour that shall be used to print messages of a certain level. Be aware that
     * colouring must be enabled ({@link #setColourUsed(boolean)}) for actual colouring to take
     * place.
     * 
     * @param level
     *        The level whose colour shall be changed.
     * @param colour
     *        The new colour that shall be used for messages of the given level or {@code null} if
     *        no special colour shall be used.
     */
    public static void setColour(Level level, AnsiColour colour) {
        COLOURS.put(level, colour);
    }
    
    private static String formatColour(AnsiColour colour) {
        
        switch (colour) {
            case BLACK:
                return "30";
            case RED:
                return "31";
            case GREEN:
                return "32";
            case YELLOW:
                return "33";
            case BLUE:
                return "34";
            case MAGENTA:
                return "35";
            case CYAN:
                return "36";
            case WHITE:
                return "37";
            case BRIGHT_BLACK:
                return "90";
            case BRIGHT_RED:
                return "91";
            case BRIGHT_GREEN:
                return "92";
            case BRIGHT_YELLOW:
                return "93";
            case BRIGHT_BLUE:
                return "94";
            case BRIGHT_MAGENTA:
                return "95";
            case BRIGHT_CYAN:
                return "96";
            case BRIGHT_WHITE:
                return "97";
            default:
                Log.warning("The colour " + colour + " is not implemented properly!");
                return "";
        }
    }
    
    private static void log(StackTraceElement e, String line, Level modifier) {
        String className = e.getClassName();
        String packageName = "<default>";
        
        if (className.contains(".")) {
            packageName = className.substring(0, className.lastIndexOf("."));
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }
        
        String methodName = e.getMethodName();
        String lineNumber = e.getLineNumber() + "";
        
        Calendar calendar = Calendar.getInstance();
        
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int ms = calendar.get(Calendar.MILLISECOND);
        
        String days = day < 10 ? "0" + day : day + "";
        String months = month < 10 ? "0" + month : month + "";
        String hours = hour < 10 ? "0" + hour : hour + "";
        String mins = min < 10 ? "0" + min : min + "";
        String secs = sec < 10 ? "0" + sec : sec + "";
        String millis;
        
        if (ms < 10) {
            millis = "00" + ms;
        } else if (ms < 100) {
            millis = "0" + ms;
        } else {
            millis = "" + ms;
        }
        
        boolean colour = useColour && COLOURS.containsKey(modifier)
                && COLOURS.get(modifier) != null;
        
        for (String ln : line.split("\n")) {
            String raw = getFormat();
            String message = "";
            boolean escape = false;
            
            for (char c : raw.toCharArray()) {
                
                if (escape) {
                    
                    switch (c) {
                        case 'D':
                            message += days;
                            break;
                        case 'n':
                            message += months;
                            break;
                        case 'y':
                            message += year;
                            break;
                        case 'H':
                            message += hours;
                            break;
                        case 'M':
                            message += mins;
                            break;
                        case 'S':
                            message += secs;
                            break;
                        case 'm':
                            message += millis;
                            break;
                        case 'T':
                            message += Thread.currentThread().getName();
                            break;
                        case 'i':
                            message += Thread.currentThread().getId();
                            break;
                        case 'c':
                            message += className;
                            break;
                        case 'p':
                            message += packageName;
                            break;
                        case 'f':
                            message += methodName;
                            break;
                        case 'l':
                            message += lineNumber;
                            break;
                        case 'L':
                            message += modifier.name();
                            break;
                        case 't':
                            message += ln;
                            break;
                        case '%':
                            message += '%';
                            break;
                        default:
                            
                            if (!warned.contains(c)) {
                                warned.add(c);
                                Log.warning("Ignoring unknown escape character “" + c + "”!");
                            }
                    }
                    
                    escape = false;
                } else {
                    
                    if (c == '%') {
                        escape = true;
                    } else {
                        message += c;
                    }
                }
            }
            
            if (colour) {
                handler.print("\033[" + formatColour(COLOURS.get(modifier)) + "m");
            }
            
            handler.print(message);
            
            if (colour) {
                handler.print("\033[m");
            }
            
            handler.println();
        }
        
        handler.flush();
    }
}
