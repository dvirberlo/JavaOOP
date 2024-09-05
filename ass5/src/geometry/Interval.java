//  Dvir Berlowitz

package geometry;

import util.DoubleMath;

import java.util.Objects;
import java.util.Random;

/**
 * This class represents a closed interval on the real number line.
 */
public class Interval {
    public static final Interval UNIT = new Interval(0, 1);

    private final double start;
    private final double end;

    /**
     * Constructs start new interval with the specified start and end points.
     * If start > end, the points will be swapped.
     *
     * @param start the start point of the interval
     * @param end   the end point of the interval
     */
    public Interval(double start, double end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    /**
     * Constructs a new interval with the same start and end points as the specified interval.
     *
     * @param other the interval to copy
     * @throws NullPointerException if other is null
     */
    public Interval(Interval other) {
        this(Objects.requireNonNull(other, "geometry.Interval other cannot be null.").start, other.end);
    }

    /**
     * @return the start point of the interval
     */
    public double start() {
        return this.start;
    }

    /**
     * @return the end point of the interval
     */
    public double end() {
        return this.end;
    }

    /**
     * Compares this interval to another interval for equality.
     *
     * @param other the other interval
     * @return true if the two intervals are equal, false otherwise
     */
    public boolean equals(Interval other) {
        if (other == null) {
            return false;
        }
        return DoubleMath.eq(this.start, other.start) && DoubleMath.eq(this.end, other.end);
    }

    /**
     * @return a string representation of the interval
     */
    @Override
    public String toString() {
        return String.format("geometry.Interval{start=%s, end=%s}", this.start, this.end);
    }

    /**
     * @return the length of the interval
     */
    public double length() {
        return this.end - this.start;
    }

    /**
     * @return the middle point of the interval
     */
    public double middle() {
        return (this.start / 2) + (this.end / 2);
    }

    /**
     * Returns true if the interval contains the specified point, false otherwise.
     *
     * @param num the point to check
     * @return true if the interval contains the point, false otherwise
     */
    public boolean contains(double num) {
        return DoubleMath.geq(num, this.start) && DoubleMath.leq(num, this.end);
    }

    /**
     * Returns true if the interval contains the specified interval, false otherwise.
     *
     * @param other the interval to check
     * @return true if the interval contains the other interval, false otherwise
     * @throws NullPointerException if other is null
     */
    public boolean contains(Interval other) {
        Objects.requireNonNull(other, "geometry.Interval other cannot be null.");
        return this.contains(other.start) && this.contains(other.end);
    }

    /**
     * Returns the intersection of the interval with the specified interval.
     *
     * @param other the interval to intersect with
     * @return the intersection of the interval with the other interval or null if the intervals do not intersect
     * @throws NullPointerException if other is null
     */
    public Interval intersection(Interval other) {
        Objects.requireNonNull(other, "geometry.Interval other cannot be null.");

        double start = Math.max(this.start, other.start);
        double end = Math.min(this.end, other.end);
        if (DoubleMath.leq(start, end)) {
            return new Interval(start, end);
        }
        return null;
    }

    /**
     * Returns true if the interval intersects with the specified interval, false otherwise.
     *
     * @param other the interval to check
     * @return true if the interval intersects with the other interval, false otherwise
     * @throws NullPointerException if other is null
     */
    public boolean intersects(Interval other) {
        Objects.requireNonNull(other, "geometry.Interval other cannot be null.");
        return this.contains(other.start) || this.contains(other.end)
                || other.contains(this.start) || other.contains(this.end);
    }

    /**
     * Returns the specified value clamped to the interval.
     *
     * @param value the value to clamp
     * @return the value clamped to the interval
     */
    public double clamp(double value) {
        return Math.min(Math.max(value, this.start), this.end);
    }

    /**
     * Returns the linear interpolation of the specified value within the interval.
     * If the interval has a length of 0, the middle of the other interval will be returned.
     * The value is clamped to the interval.
     *
     * @param other the other interval
     * @param value the value to interpolate
     * @return the linear interpolation of the specified value within the interval
     */
    public double linearInterpolation(Interval other, double value) {
        if (DoubleMath.eq(this.length(), 0)) {
            return other.middle();
        }
        value = this.clamp(value);
        return other.start + (value - this.start) * other.length() / this.length();
    }

    /**
     * Returns the reverse linear interpolation of the specified value within the interval.
     *
     * @param other the other interval
     * @param value the value to interpolate
     * @return the reverse linear interpolation of the specified value within the interval
     */
    public double reverseLinearInterpolation(Interval other, double value) {
        return this.linearInterpolation(other, this.end - (this.clamp(value) - this.start));
    }

    /**
     * Returns a random value within the interval.
     *
     * @param random the random object to use
     * @return a random value within the interval
     */
    public double randomValue(Random random) {
        return this.start + (random.nextDouble() * this.length());
    }

    /**
     * Returns a new interval that its start is shifted by the specified amount.
     * If the amount is negative, the interval will be reduced.
     * If the start is shifted to be greater than the end, the end will be returned.
     *
     * @param shift the amount to shift the start
     * @return a new interval that its start is shifted by the specified amount
     */
    public Interval shiftStart(double shift) {
        return new Interval(Math.min(this.start + shift, this.end), this.end);
    }

    /**
     * Returns a new interval that its end is shifted by the specified amount.
     * If the amount is negative, the interval will be reduced.
     * If the end is shifted to be less than the start, the start will be returned.
     *
     * @param shift the amount to shift the end
     * @return a new interval that its end is shifted by the specified amount
     */
    public Interval shiftEnd(double shift) {
        return new Interval(this.start, Math.max(this.end + shift, this.start));
    }

    /**
     * Returns a new interval that is expanded by the specified amount.
     * If the amount is negative, the interval will be reduced.
     * If the interval is reduced to a length of 0 or less, the middle of the interval will be returned.
     *
     * @param amount the amount to expand the interval
     * @return a new interval that is expanded by the specified amount
     */
    public Interval expand(double amount) {
        double start = this.start - amount;
        double end = this.end + amount;
        if (DoubleMath.geq(start, end)) {
            return new Interval(this.middle(), this.middle());
        }
        return new Interval(start, end);
    }

    /**
     * Returns the specified value (positive) modulo the interval.
     *
     * @param value the value to mod
     * @return the value modulo the interval
     */
    public double mod(double value) {
        return this.start + DoubleMath.positiveModulo(value - this.start, this.length());
    }
}
