/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under the terms of the GNU
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

/**
 * A simple log for a program that allows for class specific log levels.
 * 
 * @author Edgar Kalkowski
 */
public class Log {
    
    /**
     * This enum is used to specify which messages shall be logged and which not. For example if the
     * log level is {@link #WARNING} all info, default or debug messages will be discarded and not
     * actually printed to the log.
     * 
     * @author Edgar Kalkowski
     */
    public static enum Level {
        NOTHING, ERROR, WARNING, INFO, DEFAULT, DEBUG;
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
    
    /** The default log level for all classes for which no special log level is specified. */
    private static Level level = Level.DEFAULT;
    
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
            
            if (level.compareTo(bound) <= 0) {
                return true;
            } else {
                return false;
            }
            
        } else {
            
            if (level.compareTo(Log.level) <= 0) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * Print debug information to the log file. The message is only actually printed to the current
     * handler if the log level is {@link Level#DEBUG}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void debug(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.DEBUG)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "DEBUG: ");
        }
    }
    
    /**
     * Logs a message. The message is only actually printed to the current handler if the log level
     * is at least {@link Level#DEFAULT}.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void print(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.DEFAULT)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "");
        }
    }
    
    /**
     * Logs a message that is classified as an information. The message is only actually printed to
     * the current handler if the log level is at least {@link Level#INFO}.
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
     * current handler if the log level for the logging class is at least {@link Level#WARNING}.
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
     * log level for the logging class is at least {@link Level#ERROR}.
     * 
     * @param error
     *        The {@link Throwable} object that describes the exception and the stacktrace. Must not
     *        be {@code null}.
     */
    public static void error(Throwable error) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.ERROR)) {
            log(e.getClassName() + "." + e.getMethodName(), error.toString(), "ERROR: ");
            
            for (StackTraceElement s : error.getStackTrace()) {
                log(e.getClassName() + "." + e.getMethodName(), "\tat " + s.toString(), "ERROR: ");
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
     * Logs a message that is classified as an error.
     * 
     * @param line
     *        The line of text to log.
     */
    public static void error(String line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName(), Level.ERROR)) {
            log(e.getClassName() + "." + e.getMethodName(), line, "ERROR: ");
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
     * Change the log level to be more or less verbose.
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
        
        String days = day < 10 ? "0" + day + "." : day + ".";
        String months = month < 10 ? "0" + month + "." : month + ".";
        String hours = hour < 10 ? "0" + hour + ":" : hour + ":";
        String mins = min < 10 ? "0" + min + ":" : min + ":";
        String secs = sec < 10 ? "0" + sec : "" + sec;
        
        return days + months + calendar.get(Calendar.YEAR) + " " + hours + mins + secs;
    }
}
