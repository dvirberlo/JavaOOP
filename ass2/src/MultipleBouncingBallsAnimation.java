//  Dvir Berlowitz

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * This class is in charge of creating a multiple bouncing balls animation.
 */
public class MultipleBouncingBallsAnimation {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final String TITLE = "Multiple Bouncing Balls Animation";

    private static final Rectangle SCREEN_FRAME = new Rectangle(WIDTH, HEIGHT);
    private static final long SLEEP_TIME = 50;

    private static final double BALL_MAX_SIZE = Math.round(Math.min(WIDTH, HEIGHT) / 3.0);
    private static final Interval BALL_SIZES = new Interval(1, 50);
    private static final Interval BALL_SPEEDS = new Interval(1, Math.min(WIDTH, HEIGHT) / 10.0);
    private static final List<Color> BALL_COLORS = List.of(Color.BLACK);

    /**
     * The main method is in charge of running the program.
     *
     * @param args the command line arguments, containing the sizes of the balls
     */
    public static void main(String[] args) {
        Interval validSizes = new Interval(1, BALL_MAX_SIZE);
        int[] sizes = MultipleFramesBouncingBallsAnimation.parseSizesFromArgs(args, validSizes);
        if (sizes == null) {
            return;
        }

        GUI gui = new GUI(TITLE, WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();

        Random random = new Random();
        Ball[] balls = new Ball[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            double speed = BALL_SIZES.reverseLinearInterpolation(BALL_SPEEDS, sizes[i]);
            balls[i] = MultipleFramesBouncingBallsAnimation.generateRandomBall(random, sizes[i], speed, BALL_COLORS,
                    SCREEN_FRAME, List.of());
        }

        while (true) {
            DrawSurface surface = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(surface);
            }
            gui.show(surface);
            sleeper.sleepFor(SLEEP_TIME);
        }
    }
}
