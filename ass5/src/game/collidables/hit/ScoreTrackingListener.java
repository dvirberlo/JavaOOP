//  Dvir Berlowitz

package game.collidables.hit;

import game.collidables.Ball;
import game.collidables.block.Block;
import util.Counter;

import java.util.Objects;

/**
 * This class is in charge of tracking the score of the game.
 */
public class ScoreTrackingListener implements HitListener {

    private final Counter score;
    private final int hitScore;

    /**
     * Constructs a new score tracking listener with the specified score counter and hit score.
     *
     * @param score    the counter to track the score with
     * @param hitScore the amount of points to add to the score when a hit occurs
     * @throws NullPointerException if score is null
     */
    public ScoreTrackingListener(Counter score, int hitScore) {
        this.score = Objects.requireNonNull(score, "util.Counter score cannot be null.");
        this.hitScore = hitScore;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.score.increase(hitScore);
    }
}
