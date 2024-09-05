//  Dvir Berlowitz

import java.util.List;
import java.util.Map;

/**
 * This class represents a logical value.
 */
public class Val implements Expression {
    public static final Val TRUE = new Val(true);
    public static final Val FALSE = new Val(false);

    private static final String TRUE_SYMBOL = "T";
    private static final String FALSE_SYMBOL = "F";

    private final Boolean value;

    /**
     * Construct a new Val with the specified value.
     *
     * @param value the value
     */
    public Val(Boolean value) {
        this.value = value;
    }

    /**
     * Returns a Val with the specified value.
     *
     * @param value the value
     * @return a Val with the specified value
     */
    public static Val of(Boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public String toString() {
        return this.value ? TRUE_SYMBOL : FALSE_SYMBOL;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) {
        return this.value;
    }

    @Override
    public List<String> getVariables() {
        return List.of();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
    public Boolean evaluate() {
        return this.evaluate(Map.of());
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
