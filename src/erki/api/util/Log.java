/*
 * © Copyright 2007–2010 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
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
 * All actual logging methods of this class are thread safe.
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
    
    private static String format = "[%H:%M:%S.%m, %T, %c.%f(%l)] %L: %t";
    
    /** The default log level for all classes for which no special log level is specified. */
    private static Level level = Level.INFO;
    
    /** A lock to synchronize parallel access to the log. */
    private static Object lock = new Object();
    
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.DEBUG)) {
            
            synchronized (lock) {
                log(e, line.toString(), Level.DEBUG);
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.FINE_DEBUG)) {
            
            synchronized (lock) {
                log(e, line.toString(), Level.FINE_DEBUG);
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.FINER_DEBUG)) {
            
            synchronized (lock) {
                log(e, line.toString(), Level.FINER_DEBUG);
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
    public static void finest(Object line) {
        StackTraceElement e = new Throwable().getStackTrace()[1];
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.FINEST_DEBUG)) {
            
            synchronized (lock) {
                log(e, line.toString(), Level.FINEST_DEBUG);
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.INFO)) {
            
            synchronized (lock) {
                log(e, line.toString(), Level.INFO);
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.WARNING)) {
            
            synchronized (lock) {
                log(e, line.toString(), Level.WARNING);
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
        
        if (isLoggable(e.getClassName().replaceAll("\\$", "."), Level.ERROR)) {
            
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
                log(e, line.toString(), Level.ERROR);
            }
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
    
    private static void log(StackTraceElement e, String line, Level level) {
        String className = e.getClassName();
        
        if (className.contains(".")) {
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
        
        String message = format;
        
        message = message.replaceAll("%D", days);
        message = message.replaceAll("%n", months);
        message = message.replaceAll("%y", year + "");
        message = message.replaceAll("%H", hours);
        message = message.replaceAll("%M", mins);
        message = message.replaceAll("%S", secs);
        message = message.replaceAll("%m", millis);
        message = message.replaceAll("%T", Thread.currentThread().getName());
        message = message.replaceAll("%i", "" + Thread.currentThread().getId());
        message = message.replaceAll("%c", className);
        message = message.replaceAll("%f", methodName);
        message = message.replaceAll("%l", lineNumber);
        message = message.replaceAll("%L", level.name());
        
        boolean first = true;
        
        for (String ln : line.split("\n")) {
            
            if (first) {
                handler.println(message.replaceAll("%t", ln));
                first = false;
            } else {
                handler.println(message.replaceAll("%t", "  " + ln));
            }
        }
        
        handler.flush();
    }
}
