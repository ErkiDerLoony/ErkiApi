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
    public static double round(double value, double significantDigits) {
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
