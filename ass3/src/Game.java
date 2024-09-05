//  Dvir Berlowitz

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

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

    private static final double BORDER_THICKNESS = 30;
    private static final Color BORDER_COLOR = Color.GRAY;

    private static final double BALLS_SPEED = 5;
    private static final int BALLS_RADIUS = 5;
    private static final int BALLS_COUNT = 2;

    private static final double BLOCKS_HEIGHT = 20;
    private static final double BLOCKS_WIDTH = BLOCKS_HEIGHT * 2.5;
    private static final Point BLOCKS_START = new Point(SCREEN_WIDTH - BORDER_THICKNESS - BLOCKS_WIDTH,
            BORDER_THICKNESS + BLOCKS_HEIGHT * 4);
    private static final List<Integer> BLOCKS_COUNTS = List.of(12, 11, 10, 9, 8, 7);
    private static final List<Color> BLOCKS_COLORS = List.of(Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE,
            Color.PINK, Color.GREEN);

    private static final double PADDLE_WIDTH = 100;
    private static final double PADDLE_HEIGHT = 15;
    private static final double PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private static final double PADDLE_Y = SCREEN_HEIGHT - BORDER_THICKNESS - PADDLE_HEIGHT;
    private static final double PADDLE_SPEED = 5;
    private static final Color PADDLE_COLOR = Color.ORANGE;

    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private GUI gui;

    /**
     * Constructs a new game with an empty sprite collection and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
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
     * Adds the specified sprite to the game.
     *
     * @param s the sprite to add
     * @throws NullPointerException if s is null
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
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
        Random random = new Random();
        List<GameObject> gameObjects = new ArrayList<>();

        Block background = new Block(SCREEN, BACKGROUND_COLOR);
        gameObjects.add(background);

        List<Block> borders = Block.createBorder(SCREEN, BORDER_THICKNESS, BORDER_COLOR, OUTLINES_COLOR);
        gameObjects.addAll(borders);

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
        gameObjects.addAll(blocks);

        for (int i = 0; i < BALLS_COUNT; i++) {
            Ball ball = new Ball(new Point(PADDLE_X + PADDLE_WIDTH / 2.0, PADDLE_Y - BALLS_RADIUS),
                    BALLS_RADIUS, Color.WHITE, Velocity.randomAngle(random, BALLS_SPEED), this.environment);
            gameObjects.add(ball);
        }

        Paddle paddle = new Paddle(this.gui.getKeyboardSensor(), SCREEN.xProjection(), PADDLE_SPEED,
                new Rectangle(PADDLE_X, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT), PADDLE_COLOR, OUTLINES_COLOR);
        gameObjects.add(paddle);

        for (GameObject gameObject : gameObjects) {
            gameObject.addToGame(this);
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

            long dt = System.currentTimeMillis() - startTime;
            if (dt < sleepTime) {
                sleeper.sleepFor(sleepTime - dt);
            }
        }
    }
}
