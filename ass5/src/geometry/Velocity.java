//  Dvir Berlowitz

package geometry;

import util.DoubleMath;

import java.util.Objects;
import java.util.Random;

/**
 * This class represents a velocity in 2D space.
 */
public class Velocity {
    public static final Velocity ZERO = new Velocity(0, 0);

    private final double dx;
    private final double dy;

    /**
     * Constructs a new velocity from the specified point.
     * The x and y values of the point will be used as the dx and dy values of the velocity.
     *
     * @param p the point to construct the velocity from
     * @throws NullPointerException if p is null
     */
    public Velocity(Point p) throws NullPointerException {
        Objects.requireNonNull(p, "geometry.Point p cannot be null.");
        this.dx = p.getX();
        this.dy = p.getY();
    }

    /**
     * Constructs a new velocity with the specified dx and dy.
     *
     * @param dx the change in x
     * @param dy the change in y
     */
    public Velocity(double dx, double dy) {
        this(new Point(dx, dy));
    }

    /**
     * Constructs a new velocity with the same dx and dy as the specified velocity.
     *
     * @param other the velocity to copy
     * @throws NullPointerException if other is null
     */
    public Velocity(Velocity other) throws NullPointerException {
        this(Objects.requireNonNull(other, "geometry.Velocity other cannot be null.").asPoint());
    }

    /**
     * Constructs a new velocity with the specified angle and speed.
     * The angle is measured in degrees where 0 degrees is (0, -speed) and 90 degrees is (speed, 0).
     *
     * @param angle the angle of the velocity in degrees
     * @param speed the speed of the velocity
     * @return a new velocity with the specified angle and speed
     * @throws IllegalArgumentException if speed or angle is NaN
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) throws IllegalArgumentException {
        if (Double.isNaN(speed) || Double.isNaN(angle)) {
            throw new IllegalArgumentException("speed and angle cannot be NaN.");
        }
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Constructs a new velocity with the specified speed and a random angle.
     *
     * @param random the random object to use
     * @param speed  the speed of the velocity
     * @return a random velocity with a random angle and speed
     */
    public static Velocity randomAngle(Random random, double speed) {
        Objects.requireNonNull(random, "Random random cannot be null.");
        double angle = random.nextDouble() * 360;
        return fromAngleAndSpeed(angle, speed);
    }

    /**
     * Compares this velocity to another velocity for equality.
     *
     * @param other the other velocity
     * @return true if the two velocities are equal, false otherwise
     */
    public boolean equals(Velocity other) {
        if (other == null) {
            return false;
        }
        return DoubleMath.eq(this.dx, other.dx) && DoubleMath.eq(this.dy, other.dy);
    }

    /**
     * @return a string representation of this velocity
     */
    @Override
    public String toString() {
        return String.format("geometry.Velocity{dx=%s, dy=%s}", this.dx, this.dy);
    }

    /**
     * Applies this velocity to the specified point and returns a new point.
     *
     * @param p the point to apply the velocity to
     * @return a new point with the velocity applied
     * @throws NullPointerException if p is null
     */
    public Point applyToPoint(Point p) {
        return this.applyToPointLimited(p, this.getSpeed());
    }

    /**
     * Applies this velocity to the specified point and returns a new point, limited by the specified distance.
     *
     * @param p        the point to apply the velocity to
     * @param distance the maximum distance the point can move
     * @return a new point with the velocity applied, limited by the distance
     */
    public Point applyToPointLimited(Point p, double distance) {
        Point v = this.asPoint();
        double distanceToMove = this.getSpeed();
        if (DoubleMath.gt(distanceToMove, distance)) {
            double ratio = distance / distanceToMove;
            v = v.scale(ratio);
        }
        return p.plus(v);
    }

    /**
     * Returns the speed of this velocity.
     *
     * @return the speed of this velocity
     */
    public double getSpeed() {
        return this.asPoint().magnitude();
    }

    /**
     * @return a new velocity object like this one, but with negated dx value
     */
    public Velocity reverseX() {
        return new Velocity(-this.dx, this.dy);
    }

    /**
     * @return a new velocity object like this one, but with negated dy value
     */
    public Velocity reverseY() {
        return new Velocity(this.dx, -this.dy);
    }

    /**
     * Returns a new velocity object like this one, but with the specified angle.
     *
     * @param angle the angle of the new velocity in degrees (see {@link #fromAngleAndSpeed(double, double)})
     * @return a new velocity object like this one, but with the specified angle
     */
    public Velocity withAngle(double angle) {
        return fromAngleAndSpeed(angle, this.getSpeed());
    }

    /**
     * Returns this velocity as a point.
     *
     * @return this velocity as a point
     */
    public Point asPoint() {
        return new Point(this.dx, this.dy);
    }
}
