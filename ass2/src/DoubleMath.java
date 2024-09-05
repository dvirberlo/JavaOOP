//  Dvir Berlowitz

/**
 * This class provides functionality for performing mathematical operations on double values.
 */
public class DoubleMath {
    /**
     * The threshold used for comparing two double values.
     */
    public static final double EPSILON = 1e-5;

    /**
     * Compares two double values for equality, using {@link #EPSILON} as the threshold.
     *
     * @param a double
     * @param b double
     * @return true if the two doubles are equal, false otherwise.
     */
    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < DoubleMath.EPSILON;
    }

    /**
     * Compares two double values for inequality, using {@link #EPSILON} as the threshold.
     *
     * @param a double
     * @param b double
     * @return true if the two doubles are not equal, false otherwise.
     */
    public static boolean neq(double a, double b) {
        return !eq(a, b);
    }

    /**
     * Compares two double values for less than, using {@link #EPSILON} as the threshold.
     *
     * @param a double
     * @param b double
     * @return true if a is less than b, false otherwise.
     */
    public static boolean lt(double a, double b) {
        return a < b && neq(a, b);
    }

    /**
     * Compares two double values for greater than, using {@link #EPSILON} as the threshold.
     *
     * @param a double
     * @param b double
     * @return true if a is greater than b, false otherwise.
     */
    public static boolean gt(double a, double b) {
        return a > b && neq(a, b);
    }

    /**
     * Compares two double values for less than or equal to, using {@link #EPSILON} as the
     * threshold.
     *
     * @param a double
     * @param b double
     * @return true if a is less than or equal to b, false otherwise.
     */
    public static boolean leq(double a, double b) {
        return !gt(a, b);
    }

    /**
     * Compares two double values for greater than or equal to, using {@link #EPSILON} as the
     * threshold.
     *
     * @param a double
     * @param b double
     * @return true if a is greater than or equal to b, false otherwise.
     */
    public static boolean geq(double a, double b) {
        return !lt(a, b);
    }

    /**
     * Rounds a double value to the nearest integer.
     *
     * @param a double
     * @return the rounded integer value
     */
    public static int round(double a) {
        return (int) Math.round(a);
    }
}
