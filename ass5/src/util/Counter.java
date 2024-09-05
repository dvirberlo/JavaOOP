//  Dvir Berlowitz

package util;

/**
 * This class represents a counter.
 */
public class Counter {
    private int count;


    /**
     * Constructs a new counter with the specified initial count.
     *
     * @param initialCount the initial count of the counter
     */
    public Counter(int initialCount) {
        this.count = initialCount;
    }

    /**
     * Constructs a new counter with an initial count of 0.
     */
    public Counter() {
        this(0);
    }


    /**
     * Increases the count by the specified amount.
     *
     * @param amount the amount to increase the count by
     */
    public void increase(int amount) {
        this.count += amount;
    }

    /**
     * Increases the count by 1.
     */
    public void increase() {
        this.increase(1);
    }

    /**
     * Decreases the count by the specified amount.
     *
     * @param amount the amount to decrease the count by
     */
    public void decrease(int amount) {
        this.count -= amount;
    }

    /**
     * Decreases the count by 1.
     */
    public void decrease() {
        this.decrease(1);
    }

    /**
     * @return the current count
     */
    public int getValue() {
        return this.count;
    }
}
