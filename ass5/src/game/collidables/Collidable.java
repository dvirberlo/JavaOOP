//  Dvir Berlowitz

package game.collidables;

import game.GameObject;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * This interface represents an object that can collide with other objects.
 */
public interface Collidable extends GameObject {
    /**
     * @return the rectangle that represents the object's collision shape
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that the ball collided with it at the specified collision point and velocity.
     *
     * @param ball            the ball that collided with this object
     * @param collisionPoint  the point of collision
     * @param currentVelocity the velocity of the object that collided with this object
     * @return the new velocity expected after the hit (based on the force the object inflicted on us)
     */
    Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity);
}
