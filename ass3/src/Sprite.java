//  Dvir Berlowitz

import biuoop.DrawSurface;

/**
 * This interface represents a sprite in the game.
 */
public interface Sprite extends GameObject {
    /**
     * Draw the sprite on the specified surface.
     *
     * @param surface the surface to draw on
     * @throws NullPointerException if surface is null
     */
    void drawOn(DrawSurface surface);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}
