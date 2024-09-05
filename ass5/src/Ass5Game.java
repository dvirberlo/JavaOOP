//  Dvir Berlowitz

import game.Game;

/**
 * This class is in charge of running the game.
 */
public class Ass5Game {
    /**
     * Runs the game.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
