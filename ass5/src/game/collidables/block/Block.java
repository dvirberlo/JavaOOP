//  Dvir Berlowitz

package game.collidables.block;

import game.collidables.Ball;
import game.collidables.hit.HitListener;
import game.collidables.hit.HitNotifier;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a block in the game.
 * A block will change the ball's velocity and color when hit.
 * The block notifies all listeners when a hit event occurs.
 */
public class Block extends CollidableBlock implements HitNotifier {
    private final List<HitListener> hitListeners;

    /**
     * Constructs a new block with the specified rectangle, color and outline color.
     *
     * @param rectangle    the rectangle that represents the block
     * @param color        the color of the block
     * @param outlineColor the outline color of the block
     * @throws NullPointerException if rectangle, color, or outlineColor is null
     */
    public Block(Rectangle rectangle, Color color, Color outlineColor) {
        super(rectangle, color, outlineColor);
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Constructs a new block with the specified upper-left point, width, height, color, and outline color.
     *
     * @param upperLeft    the upper-left point of the block
     * @param width        the width of the block
     * @param height       the height of the block
     * @param color        the color of the block
     * @param outlineColor the outline color of the block
     * @throws NullPointerException if upperLeft or color is null
     */
    public Block(Point upperLeft, double width, double height, Color color, Color outlineColor) {
        this(new Rectangle(upperLeft, width, height), color, outlineColor);
    }

    /**
     * Constructs a new block with the specified upper-left point, width, height, color, and outline color.
     *
     * @param x            the x coordinate of the upper-left point of the block
     * @param y            the y coordinate of the upper-left point of the block
     * @param width        the width of the block
     * @param height       the height of the block
     * @param color        the color of the block
     * @param outlineColor the outline color of the block
     */
    public Block(double x, double y, double width, double height, Color color, Color outlineColor) {
        this(new Point(x, y), width, height, color, outlineColor);
    }

    /**
     * Constructs a new block with the specified rectangle and color.
     * The outline color is set to the same color as the block.
     *
     * @param rectangle the rectangle that represents the block
     * @param color     the color of the block
     * @throws NullPointerException if rectangle or color is null
     */
    public Block(Rectangle rectangle, Color color) {
        this(rectangle, color, color);
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = super.hit(ball, collisionPoint, currentVelocity);

        if (!this.ballMatchColor(ball)) {
            // the ball's color does not match the block's color, a hit event occurred:
            // change the ball's color to the block's color, and notify all listeners
            ball.setColor(this.getColor());
            this.notifyHit(ball);
        }
        return newVelocity;
    }

    /**
     * Checks if the ball matches the color of the block.
     *
     * @param ball the ball to check
     * @return true if the ball matches the color of the block, false otherwise
     */
    public boolean ballMatchColor(Ball ball) {
        return ball.getColor().equals(this.getColor());
    }

    @Override
    public void addHitListener(HitListener listener) {
        Objects.requireNonNull(listener, "game.collidables.hit.HitListener listener cannot be null.");
        this.hitListeners.add(listener);
    }

    @Override
    public void removeHitListener(HitListener listener) {
        this.hitListeners.remove(listener);
    }

    /**
     * Notifies all listeners that a hit event occurred.
     *
     * @param ball the ball that hit the block
     */
    private void notifyHit(Ball ball) {
        for (HitListener listener : new ArrayList<>(this.hitListeners)) {
            listener.hitEvent(this, ball);
        }
    }
}
