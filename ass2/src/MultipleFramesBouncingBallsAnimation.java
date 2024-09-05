//  Dvir Berlowitz

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is in charge of creating multiple frames with bouncing balls.
 */

public class MultipleFramesBouncingBallsAnimation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Multiple Frames Bouncing Balls Animation";

    private static final Rectangle SCREEN_FRAME = new Rectangle(WIDTH, HEIGHT);
    private static final long SLEEP_TIME = 50;

    private static final Rectangle GRAY_FRAME = new Rectangle(50, 50, 450, 450, Color.GRAY);
    private static final Rectangle YELLOW_FRAME = new Rectangle(450, 450, 150, 150, Color.YELLOW);

    private static final double BALL_MAX_SIZE = Math.round(Math.min(WIDTH, HEIGHT) / 6.0);
    private static final Interval BALL_SIZES = new Interval(1, 50);
    private static final Interval BALL_SPEEDS = new Interval(1, Math.min(WIDTH, HEIGHT) / 10.0);
    private static final List<Color> BALL_COLORS = List.of(Color.BLACK, Color.BLUE, Color.RED, Color.GREEN);

    /**
     * The main method is in charge of running the program.
     *
     * @param args the command line arguments, containing the sizes of the balls
     */
    public static void main(String[] args) {
        Interval validSizes = new Interval(1, BALL_MAX_SIZE);
        int[] sizes = parseSizesFromArgs(args, validSizes);
        if (sizes == null) {
            return;
        }

        GUI gui = new GUI(TITLE, WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();

        Random random = new Random();
        Ball[] balls = new Ball[sizes.length];
        List<Rectangle> grayCollideList = List.of(YELLOW_FRAME);
        List<Rectangle> outsideCollideList = List.of(YELLOW_FRAME, GRAY_FRAME);
        for (int i = 0; i < sizes.length; i++) {
            double speed = BALL_SIZES.reverseLinearInterpolation(BALL_SPEEDS, sizes[i]);
            Ball ball;
            if (i < sizes.length / 2) {
                ball = generateRandomBall(random, sizes[i], speed, BALL_COLORS, GRAY_FRAME, grayCollideList);
            } else {
                ball = generateRandomBall(random, sizes[i], speed, BALL_COLORS, SCREEN_FRAME, outsideCollideList);
            }
            balls[i] = ball;
        }

        while (true) {
            DrawSurface surface = gui.getDrawSurface();
            GRAY_FRAME.drawOn(surface);
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(surface);
            }
            YELLOW_FRAME.drawOn(surface);
            gui.show(surface);
            sleeper.sleepFor(SLEEP_TIME);
        }
    }

    /**
     * Generates a random ball with the given random object, size, speed, colors, and boundaries.
     * Smaller balls will have a higher speed than larger balls.
     * The speed is distributed linearly between {@code BALL_MAX_SPEED} and {@code BALL_MAX_SPEED - BALL_SLOWEST_SIZE}.
     *
     * @param random  the random object to use
     * @param size    the size of the ball
     * @param speed   the speed of the ball
     * @param colors  the list of colors to choose from
     * @param inside  the rectangle inside which the ball should be generated
     * @param outside the list of rectangles outside which the ball should be generated
     * @return a random ball with the given size
     */
    public static Ball generateRandomBall(Random random, int size, double speed, List<Color> colors, Rectangle inside,
                                          List<Rectangle> outside) {
        List<Rectangle> boundaries = new ArrayList<>(outside);
        boundaries.add(inside);
        Point start = Rectangle.randomPoint(random, size + 1, inside, outside);
        double angle = random.nextDouble() * 360;
        Color color = colors.get(random.nextInt(colors.size()));
        return new Ball(start, size, color, Velocity.fromAngleAndSpeed(angle, speed), boundaries);
    }

    /**
     * Parses the sizes of the balls from the command line arguments.
     *
     * @param args       the command line arguments
     * @param validSizes the valid sizes of the balls
     * @return the sizes of the balls, or null if the input is invalid
     */
    public static int[] parseSizesFromArgs(String[] args, Interval validSizes) {
        if (args.length == 0) {
            System.out.println("Please enter the sizes of the balls.");
            return null;
        }
        int[] sizes = new int[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                int size = Integer.parseInt(args[i]);
                if (!validSizes.contains(size)) {
                    System.out.println("Invalid input. Please enter integers within the range ["
                            + DoubleMath.round(validSizes.start()) + ", " + DoubleMath.round(validSizes.end()) + "].");
                    return null;
                }
                sizes[i] = size;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers only.");
            return null;
        }
        return sizes;
    }

}
