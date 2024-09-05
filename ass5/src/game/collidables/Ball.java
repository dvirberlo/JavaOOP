//  Dvir Berlowitz

package game.collidables;

import biuoop.DrawSurface;
import game.Game;
import game.sprites.Sprite;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import util.DoubleMath;

import java.awt.Color;
import java.util.Objects;

/**
 * This class represents a ball in the game.
 */
public class Ball implements Sprite {
    private final int radius;
    private final GameEnvironment environment;
    private Color color;
    private Point center;
    private Velocity velocity;

    /**
     * Constructs a new ball with the specified center, radius, color, velocity and game environment.
     *
     * @param center      the center of the ball
     * @param radius      the radius of the ball
     * @param color       the color of the ball
     * @param velocity    the velocity of the ball
     * @param environment the game environment of the ball
     * @throws IllegalArgumentException if the radius is non-positive
     * @throws NullPointerException     if the center, color or game environment is null
     */
    public Ball(Point center, int radius, Color color, Velocity velocity, GameEnvironment environment) {
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be positive");
        }

        this.center = new Point(Objects.requireNonNull(center, "The center must not be null"));
        this.radius = radius;
        this.color = Objects.requireNonNull(color, "The color must not be null");
        this.velocity = new Velocity(velocity);
        this.environment = Objects.requireNonNull(environment, "The game environment must not be null");
    }


    /**
     * Constructs a new ball with the specified center, radius, color.
     * With zero velocity and an empty game environment.
     *
     * @param center the center of the ball
     * @param radius the radius of the ball
     * @param color  the color of the ball
     * @throws IllegalArgumentException if the radius is non-positive
     */
    public Ball(Point center, int radius, Color color) {
        this(center, radius, color, Velocity.ZERO, new GameEnvironment());
    }

    /**
     * @return the x coordinate of the center of the ball
     */
    public int getX() {
        return DoubleMath.round(this.center.getX());
    }

    /**
     * @return the y coordinate of the center of the ball
     */
    public int getY() {
        return DoubleMath.round(this.center.getY());
    }

    /**
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color the color of the ball
     * @throws NullPointerException if the color is null
     */
    public void setColor(Color color) {
        this.color = Objects.requireNonNull(color, "The color must not be null");
    }

    /**
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the velocity of the ball
     * @throws NullPointerException if the velocity is null
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(Objects.requireNonNull(v, "The velocity must not be null"));
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x
     * @param dy the change in y
     */
    public void setVelocity(double dx, double dy) {
        this.setVelocity(new Velocity(dx, dy));
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Moves the ball one step, according to its velocity and the environment.
     * If the ball is about to hit a boundaries, it will bounce off it (starts moving in the opposite direction).
     * Naively assuming small balls (large collision objects and negligible radius) and small speeds.
     */
    public void moveOneStep() {
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        CollisionInfo collision = this.environment.getClosestCollision(trajectory);
        if (collision == null) {
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }

        Point intersection = collision.collisionPoint();
        this.center = this.velocity.applyToPointLimited(this.center,
                DoubleMath.nextDown(this.center.distance(intersection)));
        this.velocity = collision.collisionObject().hit(this, intersection, this.velocity);
    }

    @Override
    public void addToGame(Game game) {
        Objects.requireNonNull(game, "The game must not be null");
        game.addSprite(this);
    }

    @Override
    public void removeFromGame(Game game) {
        Objects.requireNonNull(game, "The game must not be null");
        game.removeSprite(this);
    }
}
