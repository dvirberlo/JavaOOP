//  Dvir Berlowitz

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a binary logical expression.
 */
public abstract class BinaryExpression extends BaseExpression {
    private final Expression left;
    private final Expression right;

    /**
     * Construct a new BinaryExpression with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     * @throws NullPointerException if left or right is null
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = Objects.requireNonNull(left, "Left expression cannot be null");
        this.right = Objects.requireNonNull(right, "Right expression cannot be null");
    }

    /**
     * Returns the left expression.
     *
     * @return the left expression
     */
    protected Expression getLeft() {
        return this.left;
    }

    /**
     * Returns the right expression.
     *
     * @return the right expression
     */
    protected Expression getRight() {
        return this.right;
    }

    /**
     * @return the symbol of the expression
     */
    protected abstract String getSymbol();

    @Override
    public String toString() {
        return "(" + this.left + this.getSymbol() + this.right + ")";
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSymbol(), this.left, this.right);
    }

    /**
     * Apply the operation on the two expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     * @return the result of the operation
     */
    protected abstract Boolean apply(Boolean left, Boolean right);

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.apply(this.left.evaluate(assignment), this.right.evaluate(assignment));
    }

    @Override
    public List<String> getVariables() {
        Set<String> variables = new HashSet<>();
        variables.addAll(this.left.getVariables());
        variables.addAll(this.right.getVariables());
        return List.copyOf(variables);
    }

    /**
     * @return a new Nand expression that represents the nandified form of the left expression
     */
    protected Expression aNand() {
        return this.left.nandify();
    }

    /**
     * @return a new Nand expression that represents the nandified form of the right expression
     */
    protected Expression bNand() {
        return this.right.nandify();
    }

    /**
     * @return a new Nor expression that represents the norified form of the left expression
     */
    protected Expression aNor() {
        return this.left.norify();
    }

    /**
     * @return a new Nor expression that represents the norified form of the right expression
     */
    protected Expression bNor() {
        return this.right.norify();
    }
}
