//  Dvir Berlowitz

import java.util.Objects;

/**
 * This class represents a velocity in 2D space.
 */
public class Velocity {
    public static final Velocity ZERO = new Velocity(0, 0);

    private final double dx;
    private final double dy;

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
        Objects.requireNonNull(other, "Velocity other cannot be null.");
        this.dx = other.dx;
        this.dy = other.dy;
    }

    /**
     * Constructs a new velocity from the specified point.
     * The x and y values of the point will be used as the dx and dy values of the velocity.
     *
     * @param p the point to construct the velocity from
     * @throws NullPointerException if p is null
     */
    public Velocity(Point p) throws NullPointerException {
        Objects.requireNonNull(p, "Point p cannot be null.");
        this.dx = p.getX();
        this.dy = p.getY();
    }

    /**
     * Constructs a new velocity with the specified angle and speed.
     * The angle is measured in degrees where 0 degrees is up (0, speed) and 90 degrees is right (speed, 0).
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
        double dy = Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
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
     * Compares this velocity to another object for equality.
     *
     * @param obj the object to compare
     * @return true if the object is a velocity and the velocities are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Velocity) {
            return this.equals((Velocity) obj);
        }
        return false;
    }

    /**
     * @return a hash code value for this velocity
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.dx, this.dy);
    }

    /**
     * @return a string representation of this velocity
     */
    @Override
    public String toString() {
        return String.format("Velocity{dx=%s, dy=%s}", this.dx, this.dy);
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
     * Returns a velocity object that is the result of reflecting this velocity off the specified normal.
     *
     * @param normal the normal to reflect off
     * @return a velocity object that is the result of reflecting this velocity off the specified normal
     * @throws NullPointerException if normal is null
     */
    public Velocity reflect(Point normal) {
        Objects.requireNonNull(normal, "Point normal cannot be null.");
        Point current = this.asPoint();
        Point reflected = current.minus(normal.scale(2 * current.dotProduct(normal)));
        return new Velocity(reflected);
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
