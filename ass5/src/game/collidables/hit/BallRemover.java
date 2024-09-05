//  Dvir Berlowitz

package game.collidables.hit;

import game.Game;
import game.collidables.Ball;
import game.collidables.block.Block;
import util.Counter;

import java.util.Objects;

/**
 * This class is in charge of removing balls from the game, and keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter remainingBalls;

    /**
     * Constructs a new ball remover with the specified game and counter of remaining balls.
     *
     * @param game           the game to remove balls from
     * @param remainingBalls the counter of remaining balls
     * @throws NullPointerException if game or remainingBalls is null
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = Objects.requireNonNull(game, "game.Game game cannot be null.");
        this.remainingBalls = Objects.requireNonNull(remainingBalls, "util.Counter remainingBalls cannot be null.");
    }

    @Override
    public void hitEvent(Block block, Ball ball) {
        ball.removeFromGame(this.game);
        this.remainingBalls.decrease();
    }
}
