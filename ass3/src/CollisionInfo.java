//  Dvir Berlowitz

/**
 * This class represents information about a collision.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Constructs a new collision information with the specified collision point and object.
     *
     * @param collisionPoint  the point of the collision
     * @param collisionObject the object of the collision
     * @throws NullPointerException if collisionPoint or collisionObject is null
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the point of the collision
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the object of the collision
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
