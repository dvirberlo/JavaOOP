//  Dvir Berlowitz

package game.sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a collection of sprites.
 */
public class SpriteCollection {
    private final List<Sprite> sprites;

    /**
     * Constructs a new sprite collection with no sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds the specified sprite to the collection.
     *
     * @param sprite the sprite to add
     * @throws NullPointerException if sprite is null
     */
    public void addSprite(Sprite sprite) throws NullPointerException {
        Objects.requireNonNull(sprite, "game.sprites.Sprite sprite cannot be null.");
        this.sprites.add(sprite);
    }

    /**
     * Removes the specified sprite from the collection.
     * If the sprite is not in the collection, nothing happens.
     *
     * @param sprite the sprite to remove
     */
    public void removeSprite(Sprite sprite) throws NullPointerException {
        this.sprites.remove(sprite);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite sprite : new ArrayList<>(this.sprites)) {
            sprite.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the specified surface.
     *
     * @param surface the surface to draw on
     */
    public void drawAllOn(DrawSurface surface) {
        for (Sprite sprite : new ArrayList<>(this.sprites)) {
            sprite.drawOn(surface);
        }
    }
}
