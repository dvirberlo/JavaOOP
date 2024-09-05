//  Dvir Berlowitz


import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the game environment.
 */
public class GameEnvironment {
    private final List<Collidable> collidables;

    /**
     * Constructs a new empty game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds the specified collidable to the game environment.
     *
     * @param c the collidable to add
     * @throws NullPointerException if c is null
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Returns the closest collision that occurs with the specified trajectory.
     * If no collision occurs, null is returned.
     *
     * @param trajectory the trajectory to check for collisions
     * @return the closest collision that occurs with the specified trajectory
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closest = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Collidable c : this.collidables) {
            Rectangle rect = c.getCollisionRectangle();
            Point intersection = trajectory.closestIntersectionToStartOfLine(rect);
            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = new CollisionInfo(intersection, c);
                }
            }
        }
        return closest;
    }
}
