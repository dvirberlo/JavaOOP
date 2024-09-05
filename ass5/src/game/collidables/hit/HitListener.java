//  Dvir Berlowitz

package game.collidables.hit;

import game.collidables.Ball;
import game.collidables.block.Block;

/**
 * The game.collidables.hit.HitListener interface represents an object that listens for hit events.
 */
public interface HitListener {
    /**
     * Notifies the listener that a hit event occurred.
     *
     * @param block the block that was hit
     * @param ball  the ball that hit the block
     */
    void hitEvent(Block block, Ball ball);
}
