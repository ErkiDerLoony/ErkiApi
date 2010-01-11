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

import java.util.Iterator;

/**
 * This class provides some useful mathematical methods not found in {@link Math}.
 * 
 * @author Edgar Kalkowski
 */
public class MathUtil {
    
    /** Used to compare two double values. */
    private static final double EPSILON = 1e-5;
    
    /** As this is a static helper class it must not be instanciated. */
    private MathUtil() {
    }
    
    /**
     * Round double values to a specified number of significant digits.
     * 
     * @param value
     *        The value to round.
     * @param significantDigits
     *        The number of significant digits after the comma.
     * @return {@code Math.round(value * Math.pow(10, significantDigits)) / Math.pow(10,
     *         significantDigits)}.
     */
    public static double round(double value, int significantDigits) {
        return Math.round(value * Math.pow(10, significantDigits))
                / Math.pow(10, significantDigits);
    }
    
    /**
     * Round a {@code value} to the next occurrance of {@code step}. For example if {@code step ==
     * 0.5} then {@code 1.2} is rounded to {@code 1.0} and {@code 1.3} is rounded to {@code 1.5}.
     * 
     * @param value
     *        The value to round.
     * @param step
     *        The stepping to which the value is to be rounded.
     * @return {@code ((int) (value / step)) * step}.
     */
    public static double round(double value, double step) {
        return ((int) (value / step)) * step;
    }
    
    /**
     * Checks if two double values are equal using an epsilon environment.
     * 
     * @param d1
     *        The first double value to compare.
     * @param d2
     *        The second double value to compare.
     * @return {@code true} if {@code d1 <= d2 + EPSILON && d1 >= d2 - EPSILON} and {@code false}
     *         otherwise where {@code EPSILON} is {@link #EPSILON}.
     */
    public static boolean equals(double d1, double d2) {
        return equals(d1, d2, EPSILON);
    }
    
    /**
     * Checks if two double values are equal using a specific epsilon environment.
     * 
     * @param d1
     *        The first double value to compare.
     * @param d2
     *        The second double value to compare.
     * @param epsilon
     *        The epsilon environment of {@code d1} in which {@code d2} must lie.
     * @return {@code true} if {@code d1 <= d2 + epsilon && d1 >= d2 - epsilon} and {@code false}
     *         otherwise.
     */
    public static boolean equals(double d1, double d2, double epsilon) {
        
        if (d1 <= d2 + epsilon && d1 >= d2 - epsilon) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Check if a double precision number equals zero. The comparison is done using some epsilon
     * epsilon environment because of the numeric errors a computer makes.
     * 
     * @param number
     *        The number to test.
     * @param epsilon
     *        The epsilon environment to use.
     * @return {@code true} if {@code number <= epsilon && number >= -epsilon} and {@code false}
     *         otherwise.
     */
    public static boolean isZero(double number, double epsilon) {
        
        if (number <= epsilon && number >= -epsilon) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Check if a double precision number equals zero. The comparison is done using the default
     * epsilon environment {@link #EPSILON}.
     * 
     * @param number
     *        The number to test.
     * @return The method returns {@link #isZero(double, double)} with {@code number} as first and
     *         {@link #EPSILON} as second argument.
     */
    public static boolean isZero(double number) {
        return isZero(number, EPSILON);
    }
    
    /**
     * Computes the minimum value of an {@link Iterable} of {@link Comparable} {@link Number}s.
     * 
     * @param <T>
     *        The actual type of the numbers to compare.
     * @param values
     *        The values to compare.
     * @return The minimum of all {@code values}.
     */
    public static <T extends Number & Comparable<T>> T getMin(Iterable<T> values) {
        
        if (values == null || !values.iterator().hasNext()) {
            return null;
        }
        
        Iterator<T> it = values.iterator();
        T min = it.next();
        
        while (it.hasNext()) {
            T next = it.next();
            
            if (next.compareTo(min) < 0) {
                min = next;
            }
        }
        
        return min;
    }
    
    /**
     * Computes the maximum value of an {@link Iterable} of {@link Comparable} {@link Number}s.
     * 
     * @param <T>
     *        The actual type of the numbers to compare.
     * @param values
     *        The values to compare.
     * @return The maximum of all {@code values}.
     */
    public static <T extends Number & Comparable<T>> T getMax(Iterable<T> values) {
        
        if (values == null || !values.iterator().hasNext()) {
            return null;
        }
        
        Iterator<T> it = values.iterator();
        T max = it.next();
        
        while (it.hasNext()) {
            T next = it.next();
            
            if (next.compareTo(max) > 0) {
                max = next;
            }
        }
        
        return max;
    }
}
