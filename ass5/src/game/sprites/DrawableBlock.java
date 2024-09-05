//  Dvir Berlowitz

package game.sprites;

import biuoop.DrawSurface;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import util.DoubleMath;

import java.awt.Color;
import java.util.Objects;

/**
 * This class represents a drawable block in the game.
 * A drawable block will be drawn on the screen.
 */
public class DrawableBlock implements Sprite {
    private final Rectangle rectangle;
    private final Color color;
    private final Color outlineColor;

    /**
     * Constructs a new block with the specified rectangle, color and outline color.
     *
     * @param rectangle    the rectangle that represents the block
     * @param color        the color of the block
     * @param outlineColor the outline color of the block
     * @throws NullPointerException if rectangle, color, or outlineColor is null
     */
    public DrawableBlock(Rectangle rectangle, Color color, Color outlineColor) {
        this.rectangle = new Rectangle(Objects.requireNonNull(rectangle,
                "geometry.Rectangle rectangle cannot be null."));
        this.color = Objects.requireNonNull(color, "Color color cannot be null.");
        this.outlineColor = Objects.requireNonNull(outlineColor, "Color outlineColor cannot be null.");
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
    public DrawableBlock(Point upperLeft, double width, double height, Color color, Color outlineColor) {
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
    public DrawableBlock(double x, double y, double width, double height, Color color, Color outlineColor) {
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
    public DrawableBlock(Rectangle rectangle, Color color) {
        this(rectangle, color, color);
    }

    /**
     * @return the rectangle that represents the block
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * @return the color of the block
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return the outline color of the block
     */
    public Color getOutlineColor() {
        return this.outlineColor;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        Objects.requireNonNull(surface, "DrawSurface surface cannot be null.");
        Point minimumVertex = this.rectangle.getUpperLeft();
        surface.setColor(this.color);
        surface.fillRectangle(DoubleMath.round(minimumVertex.getX()), DoubleMath.round(minimumVertex.getY()),
                DoubleMath.round(this.rectangle.getWidth()), DoubleMath.round(this.rectangle.getHeight()));
        surface.setColor(this.outlineColor);
        surface.drawRectangle(DoubleMath.round(minimumVertex.getX()), DoubleMath.round(minimumVertex.getY()),
                DoubleMath.round(this.rectangle.getWidth()), DoubleMath.round(this.rectangle.getHeight()));
    }

    @Override
    public void timePassed() {
        // do nothing
    }

    @Override
    public void addToGame(Game game) {
        Objects.requireNonNull(game, "game.Game game cannot be null.");
        game.addSprite(this);
    }

    @Override
    public void removeFromGame(Game game) {
        Objects.requireNonNull(game, "game.Game game cannot be null.");
        game.removeSprite(this);
    }
}
