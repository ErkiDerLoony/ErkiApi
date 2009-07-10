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

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 * This static class provides a highly dynamic log. One can specify a global log level and also
 * class specific log levels. If a class wants to log an event with a specific level it is first
 * checked if a class specific level was defined for that class. If that is not the case the global
 * log level is considered.
 * <p>
 * The log levels are priorized in such way that events of a certain level are only logged if the
 * considered log level for the logging class is lower or equal to the level of the message to be
 * logged. The priority is (in descending order)
 * <ul>
 * <li>SEVERE (highest value)
 * <li>WARNING
 * <li>INFO
 * <li>CONFIG
 * <li>FINE
 * <li>FINER
 * <li>FINEST (lowest value)
 * </ul>
 * The special value {@link Level#OFF} turns off all log output so nothing will be printed at all.
 * <p>
 * The translation of the log levels into prefixes for the lines in the log output is not totally
 * consistent with the naming of the enum constants. For example messages of the type
 * {@link Level#FINE} are logged with prefix “DEBUG: ” (note that {@link #debug(String)} redirects
 * to {@link #fine(String)}). Java exceptions logged either via {@link #error(Throwable)} or
 * {@link #severe(Throwable)} are prefixed with “ERROR: ” while other errors logged either via
 * {@link #error(String)} or {@link #severe(String)} are prefixed with “SEVERE: ”. This behaviour is
 * intended to be able to distinguish between these two conditions but this may change in future
 * versions of this class.
 * <p>
 * An example: If the global log level is {@link Level#INFO} and no class specific log levels have
 * been defined all messages with log levels CONFIG, FINE, FINER and FINEST will be discarded and
 * not actually printed to the log. All SEVERE, WARNING and INFO messages are printed.
 * <p>
 * The log is a simple {@link PrintStream} that defaults to be {@link System#out} and may be changed
 * at runtime e.g. to some log file via {@link #setHandler(PrintStream)}. Be careful to change the
 * log handler before printing anything to the log or otherwise that information may be lost.
 * 
 * @author Edgar Kalkowski
 */
public class Log {
    
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
    
    /** The default log level for all classes for which no special log level is specified. */
    private static Level level = Level.INFO;
    
    /** Prevent others from instanciating this static class. */
    private Log() {
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
        
        if (mapping.containsKey(classname)) {
            Level bound = mapping.get(classname);
            
            if (level.intValue() < bound.intValue() || level.intValue() == Level.OFF.intValue()) {
                return false;
            } else {
                return true;
            }
            
        } else {
            
            if (level.intValue() < Log.level.intValue() || level.intValue() == Level.OFF.intValue()) {
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
    public static void fine(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.FINE)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "DEBUG: ");
        }
    }
    
    /** See {@link #fine(String)}. */
    public static void debug(String line) {
        fine(line);
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINER}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void finer(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.FINER)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "FINER: ");
        }
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINEST}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void finest(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.FINEST)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "FINEST: ");
        }
    }
    
    /**
     * Logs a message. The message is only actually printed to the current handler if the log level
     * is at most {@link Level#CONFIG}. It has no prefix (like FINE, INFO etc.) in the resulting log
     * output.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void config(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.CONFIG)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "");
        }
    }
    
    /**
     * Logs a message that is classified as an information. The message is only actually printed to
     * the current handler if the log level is at most {@link Level#INFO}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void info(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.INFO)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "INFO: ");
        }
    }
    
    /**
     * Logs a message that is classified as a warning. The message is only actually printed to the
     * current handler if the log level for the logging class is at most {@link Level#WARNING}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void warning(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.WARNING)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "WARNING: ");
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.SEVERE)) {
            log(e.getClassName() + "." + e.getMethodName(), error.toString(), "ERROR: ");
            
            for (StackTraceElement s : error.getStackTrace()) {
                log(e.getClassName() + "." + e.getMethodName(), "\tat " + s.toString(), "ERROR: ");
            }
            
            logCause(error.getCause(), e);
        }
    }
    
    /** See {@link #error(Throwable)}. */
    public static void severe(Throwable error) {
        error(error);
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
            log(source.getClassName() + "." + source.getMethodName(), "Caused by: "
                    + cause.toString(), "ERROR: ");
            
            for (StackTraceElement s : cause.getStackTrace()) {
                log(source.getClassName() + "." + source.getMethodName(), "\tat " + s.toString(),
                        "ERROR: ");
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
    public static void error(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.SEVERE)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "SEVERE: ");
        }
    }
    
    /** See {@link #error(String)}. */
    public static void severe(String line) {
        error(line);
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
     * Change the log level for specific classes to be more or less verbose.
     * 
     * @param level
     *        The new log level for the given classes.
     * @param classes
     *        The classes whose log level shall be changed.
     */
    public static void setLevelForClasses(Level level, Class<?>... classes) {
        
        for (Class<?> clazz : classes) {
            mapping.put(clazz.getCanonicalName(), level);
        }
    }
    
    private static void log(String src, String line, String modifier) {
        String ln = line == null ? "" : line;
        handler.println("[" + getDate() + ", " + src + "] " + modifier + ln);
        handler.flush();
    }
    
    private static String getDate() {
        Calendar calendar = Calendar.getInstance();
        
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int ms = calendar.get(Calendar.MILLISECOND);
        
        String days = day < 10 ? "0" + day + "." : day + ".";
        String months = month < 10 ? "0" + month + "." : month + ".";
        String hours = hour < 10 ? "0" + hour + ":" : hour + ":";
        String mins = min < 10 ? "0" + min + ":" : min + ":";
        String secs = sec < 10 ? "0" + sec : "" + sec;
        String millis;
        
        if (ms < 10) {
            millis = ".00" + ms;
        } else if (ms < 100) {
            millis = ".0" + ms;
        } else {
            millis = "." + ms;
        }
        
        return days + months + calendar.get(Calendar.YEAR) + " " + hours + mins + secs + millis;
    }
}
