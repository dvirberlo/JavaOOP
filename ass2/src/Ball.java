//  Dvir Berlowitz

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Ball} class represents a ball in 2D space.
 */
public class Ball {
    private final int radius;
    private final Color color;
    private final List<Rectangle> boundaries;
    private Point center;
    private Velocity velocity;

    /**
     * Constructs a new ball with the specified center, radius, color, velocity and boundaries.
     *
     * @param center     the center of the ball
     * @param radius     the radius of the ball
     * @param color      the color of the ball
     * @param velocity   the velocity of the ball
     * @param boundaries the list of boundaries
     * @throws IllegalArgumentException if the radius is non-positive
     * @throws NullPointerException     if the center, color or bounds list is null
     */
    public Ball(Point center, int radius, Color color, Velocity velocity, List<Rectangle> boundaries) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be positive");
        }
        Objects.requireNonNull(center, "The center must not be null");
        Objects.requireNonNull(color, "The color must not be null");
        Objects.requireNonNull(boundaries, "The bounds list must not be null");

        this.center = new Point(center);
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(velocity);
        this.boundaries = List.copyOf(boundaries);
    }


    /**
     * Constructs a new ball with the specified center, radius, color.
     * The velocity is set to zero and the bounds list is empty.
     *
     * @param center the center of the ball
     * @param radius the radius of the ball
     * @param color  the color of the ball
     * @throws IllegalArgumentException if the radius is non-positive
     */
    public Ball(Point center, int radius, Color color) {
        this(center, radius, color, Velocity.ZERO, new ArrayList<>());
    }

    /**
     * @return the x coordinate of the center of the ball
     */
    public int getX() {
        return DoubleMath.round(this.center.getX());
    }

    /**
     * @return the y coordinate of the center of the ball
     */
    public int getY() {
        return DoubleMath.round(this.center.getY());
    }

    /**
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v);
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x
     * @param dy the change in y
     */
    public void setVelocity(double dx, double dy) {
        this.setVelocity(new Velocity(dx, dy));
    }

    /**
     * Draws the ball on the specified surface.
     *
     * @param surface the surface on which to draw the ball
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * Moves the ball one step, according to its velocity and the boundaries.
     * If the ball is about to hit a boundaries, it will bounce off it (starts moving in the opposite direction).
     * Corner collisions are handled naively, by reversing the velocity in both directions.
     */
    public void moveOneStep() {
        if (this.velocity.equals(Velocity.ZERO)) {
            return;
        }

        Point prev = this.center;
        // moves the ball between bounds, as long as some distance remains
        double distanceLeft = this.velocity.getSpeed();
        while (DoubleMath.gt(distanceLeft, 0)) {
            Point next = this.velocity.applyToPointLimited(prev, distanceLeft);
            Line trajectory = new Line(prev, next);

            // creates a list of edges of the boundaries, padded by the radius of the ball
            List<Line> edges = new ArrayList<>();
            for (Rectangle bounds : this.boundaries) {
                edges.addAll(bounds.reduce(bounds.contains(prev) ? this.radius : -this.radius).edges());
            }

            List<Intersection> intersections = trajectory.closestIntersectionToStartOfLine(edges);
            if (intersections.isEmpty()) {
                // no intersections, move the ball to the end of the trajectory
                prev = next;
                break;
            }
            // there are intersections, move the ball to the closest intersection and reflect the velocity
            List<Point> normals = new ArrayList<>();
            for (Intersection intersection : intersections) {
                Line edge = intersection.line();
                normals.add(edge.end().minus(edge.start()).normal().rotate90());
            }
            double intersectionDistance = prev.distance(intersections.get(0).point());
            prev = this.velocity.applyToPointLimited(prev, intersectionDistance);
            distanceLeft -= intersectionDistance;
            this.velocity = this.velocity.reflect(Point.sum(normals).normal());
        }
        this.center = prev;
    }
}
