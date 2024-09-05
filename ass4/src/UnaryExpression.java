//  Dvir Berlowitz

import java.util.Objects;

/**
 * This class represents a unary logical expression.
 */
public abstract class UnaryExpression extends BaseExpression {
    private final Expression expression;

    /**
     * Construct a new UnaryExpression with the specified expression.
     *
     * @param expression the inner expression
     * @throws NullPointerException if expression is null
     */
    public UnaryExpression(Expression expression) {
        this.expression = Objects.requireNonNull(expression, "Expression cannot be null");
    }

    /**
     * Returns the inner expression.
     *
     * @return the inner expression
     */
    protected Expression getExpression() {
        return expression;
    }

    /**
     * @return the symbol of the expression
     */
    protected abstract String getSymbol();

    @Override
    public String toString() {
        return this.getSymbol() + "(" + this.expression + ")";
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSymbol(), this.getExpression());
    }
}
