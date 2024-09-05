import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.Random;

/**
 * This class is in charge of drawing random circles on the screen.
 */
public class SimpleGuiExample {
    /**
     * The main method is in charge of running the program.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        SimpleGuiExample example = new SimpleGuiExample();
        example.drawRandomCircles();
    }

    /**
     * The method is in charge of drawing random circles on the screen.
     */
    public void drawRandomCircles() {
        Random rand = new Random(); // create a random-number generator
        // Create a window with the title "Random Circles Example"
        // which is 400 pixels wide and 300 pixels high.
        GUI gui = new GUI("Random Circles Example", 400, 300);
        DrawSurface d = gui.getDrawSurface();
        for (int i = 0; i < 10; ++i) {
            int x = rand.nextInt(400) + 1; // get integer in range 1-400
            int y = rand.nextInt(300) + 1; // get integer in range 1-300
            int r = 5 * (rand.nextInt(4) + 1); // get integer in 5,10,15,20
            d.setColor(Color.RED);
            d.fillCircle(x, y, r);
        }
        gui.show(d);
    }
}