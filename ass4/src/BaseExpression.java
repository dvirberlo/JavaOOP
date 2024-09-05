//  Dvir Berlowitz

import java.util.Map;

/**
 * This class represents a logical expression that isn't a variable or a value.
 */
public abstract class BaseExpression implements Expression {
    @Override
    public Boolean evaluate() throws Exception {
        return this.evaluate(Map.of());
    }

    /**
     * @return the simplified form of the expression, given that some variables haven't been assigned.
     */
    protected abstract Expression varSimplify();

    @Override
    public Expression simplify() {
        try {
            if (this.getVariables().isEmpty()) {
                return Val.of(this.evaluate());
            }
        } catch (Exception ignored) {
        }
        return this.varSimplify();
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
        return this.toString().hashCode();
    }
}
