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
 * This class provides some additional methods
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
     * @param e
     *        Describes the class that wants to print something to the log.
     * @param level
     *        The log level of the information to be printed.
     * @return {@code true} if the class is allowed to print the message, {@code false} otherwise.
     */
    private static boolean isLoggable(StackTraceElement e, Level level) {
        String classname = e.getClassName().replaceAll("\\$", ".");
        
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
     * Formats the information contained in a {@link StackTraceElement} for output in the log.
     * 
     * @param e
     *        A {@link StackTraceElement} with the necessary information.
     * @return A string that describes class, method and file where the log event occurred.
     */
    private static String getSrc(StackTraceElement e) {
        return e.getClassName() + "." + e.getMethodName() + "(" + e.getFileName() + ":"
                + e.getLineNumber() + ")";
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
        
        if (isLoggable(e, Level.FINE)) {
            log(getSrc(e), line, "FINE: ");
        }
    }
    
    /**
     * Print debug information to the log file. In contrast to {@link #fine(String)} the prefix of
     * the log entry is “DEBUG” in this case. The message is only actually printed to the current
     * handler if the log level is at most {@link Level#FINE}.
     * 
     * @param line
     *        The line of text to log.
     * @see #fine(String)
     */
    public static void debug(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e, Level.FINE)) {
            log(getSrc(e), line, "DEBUG: ");
        }
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
        
        if (isLoggable(e, Level.FINER)) {
            log(getSrc(e), line, "FINER: ");
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
        
        if (isLoggable(e, Level.FINEST)) {
            log(getSrc(e), line, "FINEST: ");
        }
    }
    
    /**
     * Logs a message about the configuration of a program. The message is only actually printed to
     * the current handler if the log level is at most {@link Level#CONFIG}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void config(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e, Level.CONFIG)) {
            log(getSrc(e), line, "CONFIG: ");
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
        
        if (isLoggable(e, Level.INFO)) {
            log(getSrc(e), line, "INFO: ");
        }
    }
    
    /**
     * Prints a message to the log without any prefix. The message is only actually printed to the
     * current handler if the log level is at most {@link Level#INFO}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void print(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e, Level.INFO)) {
            log(getSrc(e), line, "");
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
        
        if (isLoggable(e, Level.WARNING)) {
            log(getSrc(e), line, "WARNING: ");
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
        
        if (isLoggable(e, Level.SEVERE)) {
            log(getSrc(e), error.toString(), "ERROR: ");
            
            for (StackTraceElement s : error.getStackTrace()) {
                log(getSrc(e), "\tat " + s.toString(), "ERROR: ");
            }
            
            logCause(error.getCause(), e);
        }
    }
    
    /**
     * This is the same as {@link #error(Throwable)} except that the prefix of the log entry is
     * “SEVERE” in this case rather than “ERROR”.
     * 
     * @see #error(Throwable)
     */
    public static void severe(Throwable error) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e, Level.SEVERE)) {
            log(getSrc(e), error.toString(), "SEVERE: ");
            
            for (StackTraceElement s : error.getStackTrace()) {
                log(getSrc(e), "\tat " + s.toString(), "SEVERE: ");
            }
            
            logCause(error.getCause(), e);
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
            log(getSrc(source), "Caused by: " + cause.toString(), "ERROR: ");
            
            for (StackTraceElement s : cause.getStackTrace()) {
                log(getSrc(source), "\tat " + s.toString(), "ERROR: ");
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
        
        if (isLoggable(e, Level.SEVERE)) {
            log(getSrc(e), line, "ERROR: ");
        }
    }
    
    /**
     * The same as {@link #error(String)} except that the prefix is “SEVERE” in this case rather
     * than “ERROR”.
     * 
     * @see #error(String)
     */
    public static void severe(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e, Level.SEVERE)) {
            log(getSrc(e), line, "SEVERE: ");
        }
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
