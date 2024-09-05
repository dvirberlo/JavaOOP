//  Dvir Berlowitz

import java.util.Objects;

// todo: record class allow?

/**
 * This class represents an intersection between a point and a line.
 */
public class Intersection {
    private final Point point;
    private final Line line;

    /**
     * Constructs a new intersection with the specified point and line.
     *
     * @param point the point of intersection
     * @param line  the line of intersection
     * @throws NullPointerException if either point or line is null
     */
    public Intersection(Point point, Line line) {
        Objects.requireNonNull(point, "The point must not be null");
        Objects.requireNonNull(line, "The line must not be null");
        this.point = new Point(point);
        this.line = new Line(line);
    }

    /**
     * @return the point of intersection
     */
    public Point point() {
        return new Point(this.point);
    }

    /**
     * @return the line of intersection
     */
    public Line line() {
        return new Line(this.line);
    }
}
