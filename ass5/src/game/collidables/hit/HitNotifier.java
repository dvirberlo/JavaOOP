//  Dvir Berlowitz

package game.collidables.hit;

/**
 * This interface represents an object that can be hit.
 */
public interface HitNotifier {
    /**
     * Subscribes the specified listener to hit events.
     *
     * @param listener the listener to subscribe
     * @throws NullPointerException if listener is null
     */
    void addHitListener(HitListener listener);

    /**
     * Unsubscribes the specified listener from hit events.
     * If the listener is not subscribed, nothing happens.
     *
     * @param listener the listener to unsubscribe
     */
    void removeHitListener(HitListener listener);
}
