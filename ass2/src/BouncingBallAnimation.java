//  Dvir Berlowitz

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

/**
 * This class is in charge of creating a bouncing ball animation.
 */
public class BouncingBallAnimation {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final String TITLE = "Bouncing Ball Animation";

    private static final Rectangle SCREEN_BOUNDS = new Rectangle(WIDTH, HEIGHT);
    private static final long SLEEP_TIME = 50;

    private static final double BALL_MAX_SPEED = Math.min(WIDTH, HEIGHT) / 3.0;
    private static final int BALL_RADIUS = 30;
    private static final Color BALL_COLOR = Color.BLACK;


    /**
     * The main method is in charge of running the program.
     *
     * @param args the command line arguments, containing the starting point and the velocity of the ball
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Invalid input.");
            System.out.println("Usage: BouncingBallAnimation <x> <y> <dx> <dy>");
            return;
        }
        int x, y, dx, dy;
        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            dx = Integer.parseInt(args[2]);
            dy = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
            return;
        }
        Point start = new Point(x, y);
        if (!SCREEN_BOUNDS.reduce(BALL_RADIUS + 1).contains(start)) {
            System.out.println("Invalid input. The ball, with its radius, must be within the screen bounds");
            return;
        }
        if (DoubleMath.gt(new Velocity(dx, dy).getSpeed(), BALL_MAX_SPEED)) {
            System.out.println("Invalid input. The speed of the ball must be less than or equal to " + BALL_MAX_SPEED);
            return;
        }
        drawAnimation(start, dx, dy);
    }

    /**
     * Draws the animation of the bouncing ball.
     *
     * @param start the starting point of the ball
     * @param dx    the change in the x coordinate
     * @param dy    the change in the y coordinate
     * @throws NullPointerException if start is null
     */
    private static void drawAnimation(Point start, double dx, double dy) {
        Objects.requireNonNull(start, "The starting point cannot be null");
        GUI gui = new GUI(TITLE, WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start, BALL_RADIUS, BALL_COLOR, new Velocity(dx, dy), List.of(SCREEN_BOUNDS));
        while (true) {
            ball.moveOneStep();
            DrawSurface surface = gui.getDrawSurface();
            ball.drawOn(surface);
            gui.show(surface);
            sleeper.sleepFor(SLEEP_TIME);
        }
    }
}
