import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.util.Random;

class BallsTest1 {
    public static void main(String[] args) {
        // GUI gui = new GUI("Balls Test 1", 400, 400);
        // DrawSurface d
        //         = gui.getDrawSurface();
        // Ball b1 = new Ball(new Point(100, 100), 30, java.awt.Color.RED);
        // Ball b2 = new Ball(new Point(100, 150), 10, java.awt.Color.BLUE);
        // Ball b3 = new Ball(new Point(80, 249), 50, java.awt.Color.GREEN);
        // b1.drawOn(d);
        // b2.drawOn(d);
        // b3.drawOn(d);
        // gui.show(d);
        drawAnimation(new Point(100, 100), 2, 2);
    }

    private static void drawAnimation() {
        GUI gui = new GUI("title", 200, 200);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            Ball ball = new Ball(new Point(rand.nextInt(200), rand.nextInt(200)), 30, java.awt.Color.BLACK);
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }

    private static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title", 200, 200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(new Point(start), 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }
}