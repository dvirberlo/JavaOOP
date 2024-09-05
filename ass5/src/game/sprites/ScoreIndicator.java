//  Dvir Berlowitz

package game.sprites;

import biuoop.DrawSurface;
import game.Game;
import geometry.Rectangle;
import util.Counter;
import util.DoubleMath;

import java.awt.Color;
import java.util.Objects;

/**
 * This class represents a score indicator in a game, that displays the current score.
 */
public class ScoreIndicator implements Sprite {
    private static final String SCORE_TEXT = "Score: ";
    private static final int FONT_SIZE = 15;

    private final Counter score;
    private final DrawableBlock block;
    private final Color textColor;

    /**
     * Constructs a new score indicator with the specified score counter, rectangle, text color, and background color.
     *
     * @param score           the counter of the score
     * @param rectangle       the rectangle of the indicator
     * @param textColor       the color of the text
     * @param backgroundColor the color of the background
     * @throws NullPointerException if score, rectangle, textColor, or backgroundColor is null
     */
    public ScoreIndicator(Counter score, Rectangle rectangle, Color textColor, Color backgroundColor) {
        this.score = Objects.requireNonNull(score, "util.Counter score cannot be null.");
        this.block = new DrawableBlock(rectangle, backgroundColor);
        this.textColor = Objects.requireNonNull(textColor, "Color textColor cannot be null.");
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);

        // calculate the point to draw the text at, so it will be centered in the block
        double centerX = this.block.getRectangle().getUpperLeft().getX()
                + this.block.getRectangle().getWidth() / 2
                - (FONT_SIZE * SCORE_TEXT.length() / 2.0);
        double centerY = this.block.getRectangle().getUpperLeft().getY()
                + this.block.getRectangle().getHeight() / 2
                + FONT_SIZE / 2.0;

        d.setColor(this.textColor);
        d.drawText(DoubleMath.round(centerX), DoubleMath.round(centerY),
                SCORE_TEXT + this.score.getValue(), FONT_SIZE);
    }

    @Override
    public void timePassed() {
        // Do nothing
    }

    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    @Override
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
}
