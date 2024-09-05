//  Dvir Berlowitz

import java.util.List;
import java.util.Objects;

/**
 * This class represents a point in 2D space.
 */
public class Point {
    public static final Point ORIGIN = new Point(0, 0);

    private final double x;
    private final double y;

    /**
     * Constructs a new point with the specified x and y coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new point with the same coordinates as the specified point.
     *
     * @param other the point to copy
     * @throws NullPointerException if other is null
     */
    public Point(Point other) {
        Objects.requireNonNull(other, "Point other cannot be null.");
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Returns a new point that is the sum of the specified list of points.
     *
     * @param points the list of points
     * @return a new point that is the sum of the specified list of points
     * @throws NullPointerException if points is null
     */
    public static Point sum(List<Point> points) {
        Objects.requireNonNull(points, "List<Point> points cannot be null.");
        Point sum = Point.ORIGIN;
        for (Point point : points) {
            sum = sum.plus(point);
        }
        return sum;
    }

    /**
     * @return the x coordinate of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y coordinate of the point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the distance between this point and another point.
     *
     * @param other the other point
     * @return the distance between this point and the other point
     * @throws NullPointerException if other is null
     */
    public double distance(Point other) {
        Objects.requireNonNull(other, "Point other cannot be null.");
        return this.minus(other).magnitude();
    }

    /**
     * Compares this point to another point for equality.
     *
     * @param other the other point
     * @return true if the two points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return DoubleMath.eq(this.x, other.x) && DoubleMath.eq(this.y, other.y);
    }

    /**
     * Compares this point to another object for equality.
     *
     * @param obj the object to compare to
     * @return true if the object is a point and the two points are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Point) {
            return this.equals((Point) obj);
        }
        return false;
    }

    /**
     * @return the hash code of the point
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    /**
     * @return a string representation of the point
     */
    @Override
    public String toString() {
        return String.format("Point{x=%s, y=%s}", this.x, this.y);
    }

    /**
     * Returns a new point with the coordinates of this point plus the coordinates of another point.
     *
     * @param other the other point
     * @return a new point with the coordinates of this point plus the coordinates of another point
     * @throws NullPointerException if other is null
     */
    public Point plus(Point other) {
        Objects.requireNonNull(other, "Point other cannot be null.");
        return new Point(this.x + other.x, this.y + other.y);
    }

    /**
     * Returns a new point with the coordinates of this point minus the coordinates of another point.
     *
     * @param other the other point
     * @return a new point with the coordinates of this point minus the coordinates of another point
     * @throws NullPointerException if other is null
     */
    public Point minus(Point other) {
        Objects.requireNonNull(other, "Point other cannot be null.");
        return new Point(this.x - other.x, this.y - other.y);
    }

    /**
     * Returns a new point with the coordinates of this point scaled by a scalar.
     *
     * @param scalar the scalar to multiply the coordinates by
     * @return a new point with the coordinates of this point scaled by a scalar
     */
    public Point scale(double scalar) {
        return new Point(this.x * scalar, this.y * scalar);
    }

    /**
     * Returns the cross product of this point and another point.
     *
     * @param other the other point
     * @return the cross product of this point and another point
     * @throws NullPointerException if other is null
     */
    public double crossProduct(Point other) {
        Objects.requireNonNull(other, "Point other cannot be null.");
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Returns the dot product of this point and another point.
     *
     * @param other the other point
     * @return the dot product of this point and another point
     * @throws NullPointerException if other is null
     */
    public double dotProduct(Point other) {
        Objects.requireNonNull(other, "Point other cannot be null.");
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Returns the normal of this point.
     *
     * @return the normal of this point
     */
    public Point normal() {
        return this.scale(1 / this.magnitude());
    }

    /**
     * Returns the magnitude of this point.
     *
     * @return the magnitude of this point
     */
    public double magnitude() {
        return Math.sqrt(this.dotProduct(this));
    }

    /**
     * Returns a new point with the coordinates of this point rotated by 90 degrees.
     *
     * @return a new point with the coordinates of this point rotated by 90 degrees
     */
    public Point rotate90() {
        return new Point(-this.y, this.x);
    }
}
