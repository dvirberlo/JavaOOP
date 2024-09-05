//  Dvir Berlowitz

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a paddle in the game.
 */
public class Paddle implements Collidable, Sprite {
    private static final Map<Interval, Double> SECTIONS_ANGLES = Map.of(
            new Interval(0, 1), -60.0,
            new Interval(1, 2), -30.0,
            new Interval(2, 3), 0.0,
            new Interval(3, 4), 30.0,
            new Interval(4, 5), 60.0
    );
    private static final Interval SECTIONS_INTERVAL = new Interval(0, 5);

    private final KeyboardSensor keyboard;
    private final Interval bounds;
    private final double speed;
    private Block block;

    /**
     * Constructs a new paddle with the specified keyboard sensor, bounds, speed, rectangle, color, and outline color.
     *
     * @param keyboard     the keyboard sensor
     * @param bounds       the x bounds that the paddle can move between
     * @param speed        the speed of the paddle
     * @param rectangle    the rectangle that represents the paddle
     * @param color        the color of the paddle
     * @param outlineColor the outline color of the paddle
     * @throws NullPointerException if the keyboard sensor, bounds, rectangle, color, or outline color is null
     */
    public Paddle(KeyboardSensor keyboard, Interval bounds, double speed,
                  Rectangle rectangle, Color color, Color outlineColor) {
        this.keyboard = Objects.requireNonNull(keyboard, "The keyboard sensor must not be null");
        this.bounds = new Interval(Objects.requireNonNull(bounds, "The bounds must not be null"));
        this.speed = speed;
        this.block = new Block(rectangle, color, outlineColor);
    }

    /**
     * Moves the paddle by the specified amount.
     * The paddle will not move outside the bounds.
     *
     * @param dx the amount to move the paddle by
     */
    private void move(double dx) {
        double width = this.block.getCollisionRectangle().getWidth();
        Interval upperLeftBounds = this.bounds.shiftStart(-width).expand(-DoubleMath.DELTA);
        Point upperLeft = this.block.getCollisionRectangle().getUpperLeft();
        double x = upperLeftBounds.mod(upperLeft.getX() + dx);
        this.block = this.block.copyTo(new Point(x, upperLeft.getY()));
    }

    /**
     * Moves the paddle left.
     */
    public void moveLeft() {
        this.move(-this.speed);
    }

    /**
     * Moves the paddle right.
     */
    public void moveRight() {
        this.move(this.speed);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double sectionsX = this.block.getCollisionRectangle().xProjection()
                .linearInterpolation(SECTIONS_INTERVAL, collisionPoint.getX());
        Double angle = 0.0;
        for (Map.Entry<Interval, Double> entry : SECTIONS_ANGLES.entrySet()) {
            if (entry.getKey().contains(sectionsX)) {
                angle = entry.getValue();
                break;
            }
        }
        if (angle == 0.0) {
            return this.block.hit(collisionPoint, currentVelocity);
        }
        return currentVelocity.withAngle(angle);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        this.block.drawOn(surface);
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }


    @Override
    public void addToGame(Game game) {
        Objects.requireNonNull(game, "The game must not be null");
        game.addCollidable(this);
        game.addSprite(this);
    }

}
