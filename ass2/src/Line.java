//  Dvir Berlowitz

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a closed line segment in a 2D plane.
 */
public class Line {
    private final Point start;
    private final Point end;

    /**
     * Constructs a new line with the specified start and end points.
     *
     * @param start the start point of the line
     * @param end   the end point of the line
     * @throws NullPointerException if either start or end is null
     */
    public Line(Point start, Point end) {
        Objects.requireNonNull(start, "Point start cannot be null.");
        Objects.requireNonNull(end, "Point end cannot be null.");
        this.start = new Point(start);
        this.end = new Point(end);
    }

    /**
     * Constructs a new line with the specified x and y coordinates of the start and end points.
     *
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Constructs a new line that is a copy of the specified line.
     *
     * @param other the line to copy
     */
    public Line(Line other) {
        this(other.start, other.end);
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return new Point(this.start);
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return new Point(this.end);
    }

    /**
     * Compares this line to another line for equality.
     *
     * @param other the other line
     * @return true if the two lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Compares this line to another object for equality.
     *
     * @param obj the object to compare
     * @return true if the object is a line and the two lines are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Line) {
            return this.equals((Line) obj);
        }
        return false;
    }

    /**
     * @return a hash code value for the line
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    /**
     * @return a string representation of the line
     */
    @Override
    public String toString() {
        return String.format("Line{start=%s, end=%s}", this.start, this.end);
    }

    /**
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * @return the middle point of the line
     */
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2;
        double y = (this.start.getY() + this.end.getY()) / 2;
        return new Point(x, y);
    }

    /**
     * Calculate the intersection between this line and another line.
     * This method returns a list of points, according to the following rules:
     * <ul>
     *     <li>If the lines do not intersect, an empty list is returned.</li>
     *     <li>If the lines intersect at a single point, a list containing that point is returned.</li>
     *     <li>If the lines are collinear and intersect at a line segment, a list containing the start and end points
     *     of the intersection segment is returned.</li>
     * </ul>
     *
     * @param other the other line
     * @return a list of points representing the intersection between this line and another line
     * @throws NullPointerException if other is null
     */
    public List<Point> intersection(Line other) {
        Objects.requireNonNull(other, "Line other cannot be null.");

        // 0-length lines cases:
        if (this.start.equals(this.end) && other.start.equals(other.end)) {
            if (this.start.equals(other.start)) {
                return List.of(this.start);
            }
            return List.of();
        } else if (this.start.equals(this.end)) {
            return other.intersection(this);
        }

        // following the algorithm from https://stackoverflow.com/a/565282
        Point p = this.start, q = other.start;
        Point r = this.end.minus(this.start), s = other.end.minus(other.start);
        double rCrossS = r.crossProduct(s);
        Point qMinusP = q.minus(p);
        double qMinusPCrossR = qMinusP.crossProduct(r);

        if (DoubleMath.eq(rCrossS, 0)) {
            // the lines are parallel
            if (DoubleMath.neq(qMinusPCrossR, 0)) {
                // the lines are parallel and non-intersecting
                return List.of();
            } else {
                // the lines are collinear
                double rDotR = r.dotProduct(r);
                double t0 = qMinusP.dotProduct(r) / rDotR;
                double t1 = (qMinusP.plus(s)).dotProduct(r) / rDotR;
                Interval intersection = Interval.UNIT.intersection(new Interval(t0, t1));
                if (intersection == null) {
                    return List.of();
                }
                if (DoubleMath.eq(intersection.length(), 0)) {
                    return List.of(p.plus(r.scale(intersection.start())));
                } else {
                    return List.of(p.plus(r.scale(intersection.start())), p.plus(r.scale(intersection.end())));
                }
            }
        } else {
            double t = qMinusP.crossProduct(s) / rCrossS;
            double u = qMinusP.crossProduct(r) / rCrossS;
            if (Interval.UNIT.contains(t) && Interval.UNIT.contains(u)) {
                return List.of(p.plus(r.scale(t)));
            } else {
                return List.of();
            }
        }
    }

    /**
     * Checks if this line is intersecting with another line.
     *
     * @param other the other line
     * @return true if the two lines intersect, false otherwise
     * @throws NullPointerException if other is null
     */
    public boolean isIntersecting(Line other) {
        Objects.requireNonNull(other, "Line other cannot be null.");
        return !this.intersection(other).isEmpty();
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * If there are multiple intersection points, null is returned.
     *
     * @param other the other line
     * @return the intersection point if the lines intersect, and null otherwise
     * @throws NullPointerException if other is null
     */
    public Point intersectionWith(Line other) {
        Objects.requireNonNull(other, "Line other cannot be null.");
        List<Point> intersection = this.intersection(other);
        if (intersection.size() == 1) {
            return intersection.get(0);
        }
        return null;
    }

    /**
     * Checks if this line is intersecting with two other lines.
     *
     * @param other1 the first other line
     * @param other2 the second other line
     * @return true if the lines intersect, false otherwise
     * @throws NullPointerException if other1 or other2 is null
     */
    public boolean isIntersecting(Line other1, Line other2) {
        Objects.requireNonNull(other1, "Line other1 cannot be null.");
        Objects.requireNonNull(other2, "Line other2 cannot be null.");
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Returns the closest intersections of the trajectory of this line with a list of other lines.
     * Intersections at the start point of the line are ignored.
     *
     * @param lines the list of other lines
     * @return the closest intersections of the trajectory of this line with a list of other lines
     * @throws NullPointerException if lines is null
     */
    public List<Intersection> closestIntersectionToStartOfLine(List<Line> lines) {
        Objects.requireNonNull(lines, "List<Line> lines cannot be null.");
        double minDistance = Double.POSITIVE_INFINITY;
        for (Line line : lines) {
            Point intersection = this.intersectionWith(line);
            if (intersection != null && !intersection.equals(this.start)) {
                double distance = this.start.distance(intersection);
                if (DoubleMath.lt(distance, minDistance)) {
                    minDistance = distance;
                }
            }
        }
        List<Intersection> closestIntersections = new ArrayList<>();
        for (Line line : lines) {
            Point intersection = this.intersectionWith(line);
            if (intersection != null && !intersection.equals(this.start)
                    && DoubleMath.eq(this.start.distance(intersection), minDistance)) {
                closestIntersections.add(new Intersection(intersection, line));
            }
        }
        return closestIntersections;
    }
}