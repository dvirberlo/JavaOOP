//  Dvir Berlowitz

package geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This  class represents an aligned rectangle in a two-dimensional plane.
 * An aligned rectangle is a rectangle whose sides are parallel to the x and y axes.
 */
public class Rectangle {
    private static final int MAX_RAND_ATTEMPTS = 1_000_000;

    private final Point minimumVertex;
    private final double width;
    private final double height;

    /**
     * Constructs a new aligned rectangle with the specified minimum vertex, width and height.
     *
     * @param minimumVertex the vertex of the rectangle with the smallest x and y coordinates
     * @param width         the width of the rectangle
     * @param height        the height of the rectangle
     * @throws IllegalArgumentException if width or height is less than zero
     * @throws NullPointerException     if minimumVertex or color is null
     */
    public Rectangle(Point minimumVertex, double width, double height) throws IllegalArgumentException {
        Objects.requireNonNull(minimumVertex, "geometry.Point minimumVertex cannot be null.");
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be greater than or equal to zero.");
        }
        this.minimumVertex = new Point(minimumVertex);
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new aligned rectangle with the specified minimum vertex, width and height.
     *
     * @param x      the x-coordinate of the minimum vertex
     * @param y      the y-coordinate of the minimum vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @throws IllegalArgumentException if width or height is less than or equal to zero
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }

    /**
     * Constructs a new aligned rectangle at the origin with the specified width and height.
     *
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @throws IllegalArgumentException if width or height is less than or equal to zero
     */
    public Rectangle(double width, double height) {
        this(Point.ORIGIN, width, height);
    }

    /**
     * Constructs a new aligned rectangle with the same properties as the specified rectangle.
     *
     * @param other the rectangle to copy
     * @throws NullPointerException if other is null
     */
    public Rectangle(Rectangle other) {
        this(Objects.requireNonNull(other, "geometry.Rectangle other cannot be null.").minimumVertex, other.width,
                other.height);
    }

    /**
     * Returns a random point inside the specified rectangle and outside the specified list of rectangles.
     * The point will be at least the specified padding distance away from the edges of the rectangle.
     * The method will attempt to generate a valid point up to {@value #MAX_RAND_ATTEMPTS} times, or throw an exception
     * if it fails.
     *
     * @param random  the random object to use
     * @param padding the padding distance from the edges of the rectangle
     * @param inside  the rectangle inside which the point should be generated
     * @param avoid   the list of rectangles outside which the point should be generated
     * @return a random point inside the specified rectangle and outside the specified list of rectangles
     * @throws IllegalArgumentException if a valid point cannot be generated
     */
    public static Point randomPoint(Random random, double padding, Rectangle inside, List<Rectangle> avoid)
            throws IllegalArgumentException {
        for (int i = 0; i < MAX_RAND_ATTEMPTS; i++) {
            Point point = inside.reduce(padding).randomPoint(random);
            boolean valid = true;
            for (Rectangle rectangle : avoid) {
                if (rectangle.reduce(-padding).contains(point)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return point;
            }
        }
        throw new IllegalArgumentException("Failed to generate a valid point.");
    }

    /**
     * @return the minimum vertex of the rectangle
     */
    public Point getUpperLeft() {
        return new Point(this.minimumVertex);
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Compares this rectangle to the specified rectangle for equality.
     *
     * @param other the other rectangle
     * @return true if the two rectangles are equal, false otherwise
     */
    public boolean equals(Rectangle other) {
        if (other == null) {
            return false;
        }
        return this.minimumVertex.equals(other.minimumVertex)
                && this.width == other.width && this.height == other.height;
    }

    /**
     * @return a string representation of the rectangle
     */
    @Override
    public String toString() {
        return String.format("AlignedRectangle{minimumVertex=%s, width=%s, height=%s}", this.minimumVertex, this.width,
                this.height);
    }

    /**
     * Returns a list of the four edges of the rectangle.
     * The edges are ordered clockwise starting from the minimum vertex.
     *
     * @return a list of the four edges of the rectangle
     */
    public List<Line> edges() {
        Point width = new Point(this.width, 0);
        Point height = new Point(0, this.height);
        return List.of(
                new Line(this.minimumVertex, this.minimumVertex.plus(width)),
                new Line(this.minimumVertex.plus(width), this.minimumVertex.plus(width).plus(height)),
                new Line(this.minimumVertex.plus(width).plus(height), this.minimumVertex.plus(height)),
                new Line(this.minimumVertex.plus(height), this.minimumVertex)
        );
    }

    /**
     * Returns a list of the two horizontal edges of the rectangle.
     * The order is: top, bottom (clockwise)
     *
     * @return a list of the two horizontal edges of the rectangle
     */
    public List<Line> horizontalEdges() {
        return List.of(this.edges().get(0), this.edges().get(2));
    }

    /**
     * Returns a list of the two vertical edges of the rectangle.
     * The order is: right, left (clockwise)
     *
     * @return a list of the two vertical edges of the rectangle
     */
    public List<Line> verticalEdges() {
        return List.of(this.edges().get(1), this.edges().get(3));
    }

    /**
     * Returns a list of the four vertices of the rectangle.
     * The vertices are ordered clockwise starting from the minimum vertex.
     *
     * @return a list of the four vertices of the rectangle
     */
    public List<Point> vertices() {
        return List.of(this.minimumVertex,
                this.minimumVertex.plus(new Point(this.width, 0)),
                this.minimumVertex.plus(new Point(this.width, this.height)),
                this.minimumVertex.plus(new Point(0, this.height))
        );
    }

    /**
     * @return the x-projection of the rectangle
     */
    public Interval xProjection() {
        return new Interval(this.minimumVertex.getX(), this.minimumVertex.getX() + this.width);
    }

    /**
     * @return the y-projection of the rectangle
     */
    public Interval yProjection() {
        return new Interval(this.minimumVertex.getY(), this.minimumVertex.getY() + this.height);
    }

    /**
     * Checks if the specified point is contained within the rectangle.
     *
     * @param p the point to check
     * @return true if the point is contained within the rectangle, false otherwise
     * @throws NullPointerException if p is null
     */
    public boolean contains(Point p) {
        Objects.requireNonNull(p, "geometry.Point p cannot be null.");
        return this.xProjection().contains(p.getX()) && this.yProjection().contains(p.getY());
    }

    /**
     * Returns a new rectangle reduced by the specified amount on each side.
     * The new rectangle will have the same center as the original rectangle.
     * If the amount to reduce is greater than half the width or height, the new width or height will be zero.
     * Negative amounts will expand the rectangle.
     *
     * @param amount the amount to reduce the rectangle on each side
     * @return a new rectangle reduced by the specified amount on each side
     */
    public Rectangle reduce(double amount) {
        if (amount >= this.width / 2 || amount >= this.height / 2) {
            return new Rectangle(this.minimumVertex.plus(new Point(this.width / 2, this.height / 2)), 0, 0);
        }
        return new Rectangle(this.minimumVertex.plus(new Point(amount, amount)),
                this.width - 2 * amount, this.height - 2 * amount);
    }

    /**
     * Returns a random point within the rectangle.
     *
     * @param random the random object to use
     * @return a random point within the rectangle
     */
    public Point randomPoint(Random random) {
        return new Point(this.xProjection().randomValue(random), this.yProjection().randomValue(random));
    }

    /**
     * Returns the intersection points of the rectangle with the specified line.
     * If the intersection is a line segment, the method will return the two endpoints.
     *
     * @param line the line to intersect with the rectangle
     * @return the intersection points of the rectangle with the specified line
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();
        for (Line edge : this.edges()) {
            intersections.addAll(edge.intersection(line));
        }
        return intersections;
    }

    /**
     * Returns a copy of this rectangle with the specified minimum vertex.
     *
     * @param newMinimumVertex the new minimum vertex of the rectangle
     * @return a copy of this rectangle with the specified minimum vertex
     * @throws NullPointerException if newMinimumVertex is null
     */
    public Rectangle copyTo(Point newMinimumVertex) {
        return new Rectangle(newMinimumVertex, this.width, this.height);
    }
}
