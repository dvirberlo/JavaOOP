//  Dvir Berlowitz

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a block in the game.
 */
public class Block implements Collidable, Sprite {
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
    public Block(Rectangle rectangle, Color color, Color outlineColor) {
        this.rectangle = new Rectangle(Objects.requireNonNull(rectangle, "Rectangle rectangle cannot be null."));
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

    /**
     * Creates a list of blocks that represent the borders of the specified rectangle.
     * The borders are created with the specified minimum point, width, height, thickness, color and border color.
     * The borders are 2 horizontal full-width blocks and 2 vertical blocks, with height to fit between the
     * horizontal blocks.
     *
     * @param rectangle   the rectangle to create the borders for
     * @param thickness   the thickness of the borders
     * @param color       the color of the borders
     * @param borderColor the color of the borders' borders
     * @return a list of blocks that represent the borders of the screen
     * @throws NullPointerException if rectangle, color, or outlineColor is null
     */
    public static List<Block> createBorder(Rectangle rectangle, double thickness, Color color, Color borderColor) {
        Objects.requireNonNull(rectangle, "Rectangle rectangle cannot be null.");
        Objects.requireNonNull(color, "Color color cannot be null.");
        Objects.requireNonNull(borderColor, "Color outlineColor cannot be null.");

        Point minimumPoint = rectangle.getUpperLeft();
        double height = rectangle.getHeight();
        double width = rectangle.getWidth();

        List<Block> borders = new ArrayList<>();
        double horizontalBlocksHeight = height - 2 * thickness;
        // top and bottom:
        borders.add(new Block(new Rectangle(minimumPoint, width, thickness), color, borderColor));
        borders.add(new Block(new Rectangle(minimumPoint.plus(new Point(0, height - thickness)), width, thickness),
                color, borderColor));
        // left and right:
        borders.add(new Block(new Rectangle(minimumPoint.plus(new Point(0, thickness)), thickness,
                horizontalBlocksHeight), color, borderColor));
        borders.add(new Block(new Rectangle(minimumPoint.plus(new Point(width - thickness, thickness)), thickness,
                horizontalBlocksHeight), color, borderColor));
        return borders;
    }

    /**
     * Returns a copy of this block with the specified upper-left point.
     *
     * @param newUpperLeft the new upper left point of the block
     * @return a copy of this block with the specified upper-left point
     * @throws NullPointerException if newUpperLeft is null
     */
    public Block copyTo(Point newUpperLeft) {
        return new Block(this.rectangle.copyTo(newUpperLeft), this.color, this.outlineColor);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = new Velocity(currentVelocity);
        if (this.rectangle.horizontalEdges().stream().anyMatch(edge -> edge.contains(collisionPoint))) {
            newVelocity = newVelocity.reverseY();
        }
        if (this.rectangle.verticalEdges().stream().anyMatch(edge -> edge.contains(collisionPoint))) {
            newVelocity = newVelocity.reverseX();
        }
        return newVelocity;
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
        Objects.requireNonNull(game, "Game game cannot be null.");
        game.addCollidable(this);
        game.addSprite(this);
    }
}
