//  Dvir Berlowitz

package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.collidables.Ball;
import game.collidables.Collidable;
import game.collidables.GameEnvironment;
import game.collidables.block.Block;
import game.collidables.block.CollidableBlock;
import game.collidables.hit.BallRemover;
import game.collidables.hit.BlockRemover;
import game.collidables.hit.ScoreTrackingListener;
import game.sprites.DrawableBlock;
import game.sprites.Paddle;
import game.sprites.ScoreIndicator;
import game.sprites.Sprite;
import game.sprites.SpriteCollection;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import util.Counter;
import util.DoubleMath;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a game.
 */
public class Game {
    private static final String TITLE = "Arkanoid";
    private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
    public static final Rectangle SCREEN = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT);
    private static final long FPS = 60;
    private static final Color BACKGROUND_COLOR = Color.BLUE;
    private static final Color OUTLINES_COLOR = Color.BLACK;

    private static final Color BORDER_COLOR = Color.GRAY;
    private static final double BORDER_THICKNESS = 20;
    private static final double SCORE_THICKNESS = BORDER_THICKNESS;
    private static final Rectangle BORDER_FRAME = new Rectangle(
            SCREEN.getUpperLeft().plus(new Point(0, SCORE_THICKNESS)), SCREEN_WIDTH, SCREEN_HEIGHT);

    private static final int HIT_SCORE = 5;
    private static final int CLEAR_SCORE = 100;

    private static final double BLOCKS_HEIGHT = 20;
    private static final double BLOCKS_WIDTH = BLOCKS_HEIGHT * 2.5;
    private static final Point BLOCKS_START = new Point(SCREEN_WIDTH - BORDER_THICKNESS - BLOCKS_WIDTH,
            BORDER_THICKNESS + BLOCKS_HEIGHT * 4);
    private static final List<Integer> BLOCKS_COUNTS = List.of(12, 11, 10, 9, 8, 7);
    private static final List<Color> BLOCKS_COLORS = List.of(Color.GRAY, Color.RED, Color.YELLOW, Color.CYAN,
            Color.PINK, Color.GREEN);

    private static final double BALLS_SPEED = 5;
    private static final int BALLS_RADIUS = 5;
    private static final int BALLS_COUNT = 3;

    private static final double PADDLE_WIDTH = 100;
    private static final double PADDLE_HEIGHT = 15;
    private static final double PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private static final double PADDLE_Y = SCREEN_HEIGHT - BORDER_THICKNESS - PADDLE_HEIGHT;
    private static final double PADDLE_SPEED = 5;
    private static final Color PADDLE_COLOR = Color.ORANGE;

    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter score;
    private GUI gui;

    /**
     * Constructs a new game with an empty sprite collection and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter(BALLS_COUNT);
        this.score = new Counter();
        this.gui = null;
    }

    /**
     * Adds the specified collidable to the game environment.
     *
     * @param c the collidable to add
     * @throws NullPointerException if c is null
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Removes the specified collidable from the game environment.
     * If the collidable is not found, nothing happens.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Adds the specified sprite to the game.
     *
     * @param s the sprite to add
     * @throws NullPointerException if s is null
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removes the specified sprite from the game.
     * If the sprite is not found, nothing happens.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initializes the game.
     * This method should be called before the game starts, and only once.
     *
     * @throws IllegalStateException if the game has already been initialized
     */
    public void initialize() {
        if (this.gui != null) {
            throw new IllegalStateException("The game has already been initialized.");
        }

        this.gui = new GUI(TITLE, SCREEN_WIDTH, SCREEN_HEIGHT);

        initFrame();
        initBlocks();
        initBalls();

        Paddle paddle = new Paddle(this.gui.getKeyboardSensor(), SCREEN.xProjection(), PADDLE_SPEED,
                new Rectangle(PADDLE_X, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT), PADDLE_COLOR, OUTLINES_COLOR);
        paddle.addToGame(this);
    }

    /**
     * Initializes the frame of the game.
     * The frame includes the background, borders, and score indicator.
     */
    private void initFrame() {
        DrawableBlock background = new DrawableBlock(SCREEN, BACKGROUND_COLOR);
        background.addToGame(this);

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score,
                new Rectangle(0, 0, SCREEN_WIDTH, SCORE_THICKNESS), Color.BLACK, Color.WHITE);
        scoreIndicator.addToGame(this);

        List<CollidableBlock> borders = CollidableBlock.createBorder(BORDER_FRAME, BORDER_THICKNESS, BORDER_COLOR);
        for (CollidableBlock border : borders) {
            border.addToGame(this);
        }

        Block deathRegion = new Block(
                new Rectangle(0, DoubleMath.nextUp(SCREEN_HEIGHT), DoubleMath.nextUp(SCREEN_WIDTH), SCREEN_HEIGHT),
                // new geometry.Rectangle(util.DoubleMath.nextUp(SCREEN_WIDTH), 0,
                //         util.DoubleMath.nextUp(SCREEN_WIDTH), util.DoubleMath.nextUp(SCREEN_HEIGHT)),
                Color.BLACK
        );
        deathRegion.addHitListener(new BallRemover(this, this.remainingBalls));
        deathRegion.addToGame(this);
    }

    /**
     * Initializes the blocks of the game.
     */
    private void initBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < BLOCKS_COUNTS.size(); i++) {
            int count = BLOCKS_COUNTS.get(i);
            Color color = BLOCKS_COLORS.get(i);
            List<Block> levelBlocks = new ArrayList<>();
            Point start = BLOCKS_START.plus(new Point(0, BLOCKS_HEIGHT * i));
            for (int j = 0; j < count; j++) {
                Block block = new Block(start.minus(new Point(BLOCKS_WIDTH * j, 0)), BLOCKS_WIDTH, BLOCKS_HEIGHT,
                        color, OUTLINES_COLOR);
                levelBlocks.add(block);
            }
            blocks.addAll(levelBlocks);
        }

        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score, HIT_SCORE);
        for (Block block : blocks) {
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
            this.remainingBlocks.increase();
            block.addToGame(this);
        }
    }

    /**
     * Initializes the balls of the game.
     */
    private void initBalls() {
        Random random = new Random();
        for (int i = 0; i < BALLS_COUNT; i++) {
            Ball ball = new Ball(new Point(PADDLE_X + PADDLE_WIDTH / 2.0, PADDLE_Y - BALLS_RADIUS),
                    BALLS_RADIUS, Color.WHITE, Velocity.randomAngle(random, BALLS_SPEED), this.environment);
            ball.addToGame(this);
        }
    }

    /**
     * Runs the game.
     * This method should be called after the game has been initialized.
     *
     * @throws IllegalStateException if the game has not been initialized
     */
    public void run() {
        if (this.gui == null) {
            throw new IllegalStateException("The game has not been initialized.");
        }

        Sleeper sleeper = new Sleeper();
        long sleepTime = 1000 / FPS;
        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface surface = this.gui.getDrawSurface();
            this.sprites.drawAllOn(surface);
            this.gui.show(surface);
            this.sprites.notifyAllTimePassed();

            if (this.remainingBlocks.getValue() <= 0) {
                this.score.increase(CLEAR_SCORE);
                break;
            }
            if (this.remainingBalls.getValue() <= 0) {
                break;
            }

            long dt = System.currentTimeMillis() - startTime;
            if (dt < sleepTime) {
                sleeper.sleepFor(sleepTime - dt);
            }
        }

        this.gui.close();
    }
}