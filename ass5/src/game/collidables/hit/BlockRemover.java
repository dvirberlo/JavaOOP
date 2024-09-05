//  Dvir Berlowitz

package game.collidables.hit;

import game.Game;
import game.collidables.Ball;
import game.collidables.block.Block;
import util.Counter;

import java.util.Objects;

/**
 * This class is in charge of removing blocks from the game, and keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;

    /**
     * Constructs a new block remover with the specified game and counter of remaining blocks.
     *
     * @param game            the game to remove blocks from
     * @param remainingBlocks the counter of remaining blocks
     * @throws NullPointerException if game or remainingBlocks is null
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = Objects.requireNonNull(game, "game.Game game cannot be null.");
        this.remainingBlocks = Objects.requireNonNull(remainingBlocks, "util.Counter remainingBlocks cannot be null.");
    }

    @Override
    public void hitEvent(Block block, Ball ball) {
        block.removeHitListener(this);
        block.removeFromGame(this.game);
        this.remainingBlocks.decrease();
    }
}
