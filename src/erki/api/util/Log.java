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

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

/**
 * This class logs everything that happens about the bot to a
 * {@link PrintStream}. This defaults to be stdout but may be set to a file via
 * the {@link #setHander(PrintStream)} method.
 * 
 * @author Edgar Kalkowski
 */
public class Log {
    
    private static PrintStream handler;
    
    private static boolean debug = false;
    
    static {
        handler = System.out;
    }
    
    /** Prevent others from instanciating this class. */
    private Log() {
    }
    
    /**
     * Logs a message that is classified as an information.
     * 
     * @param source
     *        The source object that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void info(Object source, String line) {
        log(source, line, "INFO: ");
    }
    
    /**
     * Logs a message that is classified as an information.
     * 
     * @param source
     *        The source class that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void info(Class<?> source, String line) {
        log(source, line, "INFO: ");
    }
    
    /**
     * Logs an exception represented by an instance of {@link Throwable} or any
     * subclass of it including the stacktrace.
     * 
     * @param source
     *        The source class that wants to log this error.
     * @param error
     *        The {@link Throwable} object that describes the exception and the
     *        stacktrace. Must not be {@code null}.
     */
    public static void error(Class<?> source, Throwable error) {
        log(source, error.toString(), "ERROR: ");
        
        for (StackTraceElement s : error.getStackTrace()) {
            log(source, "\tat " + s.toString(), "ERROR: ");
        }
        
        logCause(source, error.getCause());
    }
    
    /**
     * Recursively log the causes of a throwable.
     * 
     * @param source
     *        The source object that wants to log this.
     * @param cause
     *        The cause of an error that is the starting point for the recursive
     *        traversal of the causes.
     */
    private static void logCause(Class<?> source, Throwable cause) {
        
        if (cause != null) {
            log(source, "Caused by: " + cause.toString(), "ERROR: ");
            
            for (StackTraceElement s : cause.getStackTrace()) {
                log(source, "\tat " + s.toString(), "ERROR: ");
            }
            
            logCause(source, cause.getCause());
        }
    }
    
    /**
     * Logs an exception represented by an instance of {@link Throwable} or any
     * subclass of it including the stacktrace.
     * 
     * @param source
     *        The source object that wants to log this error.
     * @param error
     *        The {@link Throwable} object that describes the exception and the
     *        stacktrace. Must not be {@code null}.
     */
    public static void error(Object source, Throwable error) {
        log(source, error.toString(), "ERROR: ");
        
        for (StackTraceElement s : error.getStackTrace()) {
            log(source, "\tat " + s.toString(), "ERROR: ");
        }
        
        logCause(source, error.getCause());
    }
    
    /**
     * Recursively log the causes of a throwable.
     * 
     * @param source
     *        The source object that wants to log this.
     * @param cause
     *        The cause of an error that is the starting point for the recursive
     *        traversal of the causes.
     */
    private static void logCause(Object source, Throwable cause) {
        
        if (cause != null) {
            log(source, "Caused by: " + cause.toString(), "ERROR: ");
            
            for (StackTraceElement s : cause.getStackTrace()) {
                log(source, "\tat " + s.toString(), "ERROR: ");
            }
            
            logCause(source, cause.getCause());
        }
    }
    
    /**
     * Logs a message that is classified as an error.
     * 
     * @param source
     *        The source object that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void error(Object source, String line) {
        log(source, line, "ERROR: ");
    }
    
    /**
     * Logs a message that is classified as an error.
     * 
     * @param source
     *        The source class that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void error(Class<?> source, String line) {
        log(source, line, "ERROR: ");
    }
    
    /**
     * Logs a message that is classified as a warning.
     * 
     * @param source
     *        The source object that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void warning(Object source, String line) {
        log(source, line, "WARNING: ");
    }
    
    /**
     * Logs a message that is classified as a warning.
     * 
     * @param source
     *        The source class that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void warning(Class<?> source, String line) {
        log(source, line, "WARNING: ");
    }
    
    /**
     * Logs a message.
     * 
     * @param source
     *        The source object that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void print(Object source, String line) {
        log(source, line, "");
    }
    
    /**
     * Logs a message.
     * 
     * @param source
     *        The source class that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void print(Class<?> source, String line) {
        log(source, line, "");
    }
    
    /**
     * Print debug information to the log file. This information is only
     * actually printed if debugging was previously activated via {@link
     * #setDebug(true)}.
     * 
     * @param source
     *        The source object that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void debug(Object source, String line) {
        
        if (debug) {
            log(source, line, "DEBUG: ");
        }
    }
    
    /**
     * Print debug information to the log file. This information is only
     * actually printed if debugging was previously activated via {@link
     * #setDebug(true)}.
     * 
     * @param source
     *        The souce class that wants to log this message.
     * @param line
     *        The line of text to log.
     */
    public static void debug(Class<?> source, String line) {
        
        if (debug) {
            log(source, line, "DEBUG: ");
        }
    }
    
    private static void log(Object source, String line, String modifier) {
        String src = source == null ? "<unknown.source>" : source.getClass()
                .getCanonicalName() == null ? "<anonymous.class>" : source
                .getClass().getCanonicalName();
        src += source == null ? "" : "@"
                + Integer.toHexString(source.hashCode());
        String ln = line == null ? "" : line;
        log(formatDate(), src, modifier, ln);
    }
    
    private static void log(Class<?> source, String line, String modifier) {
        String src = source == null ? "<unknown.source>" : source
                .getCanonicalName() == null ? "<anonymous.class>" : source
                .getCanonicalName();
        String ln = line == null ? "" : line;
        log(formatDate(), src, modifier, ln);
    }
    
    /**
     * Activate or deactivate the debug mode. Debugging is deactivated by
     * default. Lines printed via {@link #debug(Object, String)} are ignored and
     * not printed to the log file if debugging is deactivated.
     * 
     * @param debug
     *        If {@code true} all a bunch of debug info is printed to the log
     *        file. If {@code false} all debug output will be ignored.
     */
    public static void setDebug(boolean debug) {
        Log.debug = debug;
    }
    
    /**
     * @return {@code true} if debugging is currently enabled, {@code false}
     *         otherwise.
     */
    public static boolean isDebug() {
        return debug;
    }
    
    private static void log(String date, String source, String modifier,
            String line) {
        handler.println("[" + date + ", " + source + "] " + modifier + line);
        handler.flush();
    }
    
    private static String formatDate() {
        return formatDate(new Date());
    }
    
    private static String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
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
        
        return days + months + calendar.get(Calendar.YEAR) + " " + hours + mins
                + secs;
    }
    
    /**
     * Change the handler i.d. the receiver of the log messages. The handler
     * defaults to be stdout but this way one can redirect the log output e.g.
     * to a file.
     * 
     * @param handler
     *        The new handler of log messages.
     */
    public static void setHandler(PrintStream handler) {
        Log.handler = handler;
    }
}
