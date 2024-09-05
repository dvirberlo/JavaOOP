//  Dvir Berlowitz

package game.collidables.block;

import game.Game;
import game.collidables.Ball;
import game.collidables.Collidable;
import game.sprites.DrawableBlock;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a collidable block in the game.
 * A collidable block will change the ball's velocity when hit.
 */
public class CollidableBlock extends DrawableBlock implements Collidable {
    /**
     * Constructs a new block with the specified rectangle, color and outline color.
     *
     * @param rectangle    the rectangle that represents the block
     * @param color        the color of the block
     * @param outlineColor the outline color of the block
     * @throws NullPointerException if rectangle, color, or outlineColor is null
     */
    public CollidableBlock(Rectangle rectangle, Color color, Color outlineColor) {
        super(rectangle, color, outlineColor);
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
    public CollidableBlock(Point upperLeft, double width, double height, Color color, Color outlineColor) {
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
    public CollidableBlock(double x, double y, double width, double height, Color color, Color outlineColor) {
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
    public CollidableBlock(Rectangle rectangle, Color color) {
        this(rectangle, color, color);
    }

    /**
     * Creates a list of blocks that represent the borders of the specified rectangle.
     * The borders are created with the specified minimum point, width, height, thickness, color and border color.
     * (A top horizontal full-width blocks and 2 vertical blocks, with height to fit under the top block.)
     *
     * @param rectangle the rectangle to create the borders for
     * @param thickness the thickness of the borders
     * @param color     the color of the borders
     * @return a list of blocks that represent the borders of the screen
     * @throws NullPointerException if rectangle, color, or outlineColor is null
     */
    public static List<CollidableBlock> createBorder(Rectangle rectangle, double thickness, Color color) {
        Objects.requireNonNull(rectangle, "geometry.Rectangle rectangle cannot be null.");
        Objects.requireNonNull(color, "Color color cannot be null.");

        Point minimumPoint = rectangle.getUpperLeft();
        double height = rectangle.getHeight();
        double width = rectangle.getWidth();

        List<CollidableBlock> borders = new ArrayList<>();
        double horizontalBlocksHeight = height - thickness;
        // top:
        borders.add(new CollidableBlock(new Rectangle(minimumPoint, width, thickness), color));
        // left and right:
        borders.add(new CollidableBlock(new Rectangle(minimumPoint.plus(new Point(0, thickness)), thickness,
                horizontalBlocksHeight), color));
        borders.add(new CollidableBlock(new Rectangle(minimumPoint.plus(new Point(width - thickness, thickness)),
                thickness,
                horizontalBlocksHeight), color));
        return borders;
    }

    /**
     * Returns a copy of this block with the specified upper-left point.
     *
     * @param newUpperLeft the new upper left point of the block
     * @return a copy of this block with the specified upper-left point
     * @throws NullPointerException if newUpperLeft is null
     */
    public CollidableBlock copyTo(Point newUpperLeft) {
        return new CollidableBlock(this.getRectangle().copyTo(newUpperLeft), this.getColor(), this.getOutlineColor());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.getRectangle();
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = new Velocity(currentVelocity);
        if (this.getRectangle().horizontalEdges().stream().anyMatch(edge -> edge.contains(collisionPoint))) {
            newVelocity = newVelocity.reverseY();
        }
        if (this.getRectangle().verticalEdges().stream().anyMatch(edge -> edge.contains(collisionPoint))) {
            newVelocity = newVelocity.reverseX();
        }
        return newVelocity;
    }

    @Override
    public void addToGame(Game game) {
        super.addToGame(game);
        game.addCollidable(this);
    }

    @Override
    public void removeFromGame(Game game) {
        super.removeFromGame(game);
        game.removeCollidable(this);
    }
}
