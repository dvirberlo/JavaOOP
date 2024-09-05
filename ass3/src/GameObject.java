//  Dvir Berlowitz

/**
 * This interface represents an object that can be added to a game.
 */
public interface GameObject {
    /**
     * Adds this game object to the specified game.
     *
     * @param game the game to add this object to
     * @throws NullPointerException if game is null
     */
    void addToGame(Game game);
}
