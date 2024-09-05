//  Dvir Berlowitz

package game;

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

    /**
     * Removes this game object from the specified game.
     *
     * @param game the game to remove this object from
     * @throws NullPointerException if game is null
     */
    void removeFromGame(Game game);

}
