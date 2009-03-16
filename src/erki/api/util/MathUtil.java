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

/**
 * This class provides some useful mathematical methods not found in
 * {@link Math}.
 * 
 * @author Edgar Kalkowski
 */
public class MathUtil {
    
    /** Used to compare two double values. */
    private static final double EPSILON = 1e-5;
    
    /**
     * Round double values to a specified number of significant digits.
     * 
     * @param value
     *        The value to round.
     * @param significantDigits
     *        The number of significant digits after the comma.
     * @return {@code Math.round(value * Math.pow(10, significantDigits)) /
     *         Math.pow(10, significantDigits)}.
     */
    public static double round(double value, int significantDigits) {
        return Math.round(value * Math.pow(10, significantDigits))
                / Math.pow(10, significantDigits);
    }
    
    /**
     * Checks if two double values are equal using an epsilon environment.
     * 
     * @param d1
     *        The first double value to compare.
     * @param d2
     *        The second double value to compare.
     * @return {@code true} if {@code d1 <= d2 + EPSILON && d1 >= d2 - EPSILON}
     *         and {@code false} otherwise where {@code EPSILON} is
     *         {@link #EPSILON}.
     */
    public static boolean equals(double d1, double d2) {
        return equals(d1, d2, EPSILON);
    }
    
    /**
     * Checks if two double values are equal using a specific epsilon
     * environment.
     * 
     * @param d1
     *        The first double value to compare.
     * @param d2
     *        The second double value to compare.
     * @param epsilon
     *        The epsilon environment of {@code d1} in which {@code d2} must
     *        lie.
     * @return {@code true} if {@code d1 <= d2 + epsilon && d1 >= d2 - epsilon}
     *         and {@code false} otherwise.
     */
    public static boolean equals(double d1, double d2, double epsilon) {
        
        if (d1 <= d2 + epsilon && d1 >= d2 - epsilon) {
            return true;
        } else {
            return false;
        }
    }
}
