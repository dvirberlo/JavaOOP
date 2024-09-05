//  Dvir Berlowitz

/**
 * This interface represents an object that can collide with other objects.
 */
public interface Collidable extends GameObject {
    /**
     * @return the rectangle that represents the object's collision shape
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at the specified point with the specified velocity.
     *
     * @param collisionPoint  the point of collision
     * @param currentVelocity the velocity of the object that collided with this object
     * @return the new velocity expected after the hit (based on the force the object inflicted on us)
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
