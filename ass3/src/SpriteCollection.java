//  Dvir Berlowitz

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
     * @param s the sprite to add
     * @throws NullPointerException if s is null
     */
    public void addSprite(Sprite s) throws NullPointerException {
        Objects.requireNonNull(s, "Sprite s cannot be null.");
        this.sprites.add(s);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : this.sprites) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the specified surface.
     *
     * @param surface the surface to draw on
     */
    public void drawAllOn(DrawSurface surface) {
        for (Sprite s : this.sprites) {
            s.drawOn(surface);
        }
    }
}
