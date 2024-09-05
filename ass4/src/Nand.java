//  Dvir Berlowitz

/**
 * This class represents a logical NAND expression.
 */
public class Nand extends BinaryExpression {
    private static final String SYMBOL = " A ";

    /**
     * Construct a new Nand with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    public Nand(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected Boolean apply(Boolean left, Boolean right) {
        return !(left && right);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Nand(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        // [ ( A NOR A ) NOR ( B NOR B ) ] NOR [ ( A NOR A ) NOR ( B NOR B ) ]
        Expression a = this.aNor(), b = this.bNor();
        return new Nor(
                new Nor(new Nor(a, a), new Nor(b, b)),
                new Nor(new Nor(a, a), new Nor(b, b))
        );
    }

    @Override
    protected Expression varSimplify() {
        Expression left = this.getLeft().simplify(), right = this.getRight().simplify();
        if (left.equals(Val.FALSE) || right.equals(Val.FALSE)) {
            return Val.TRUE;
        }
        if (left.equals(Val.TRUE)) {
            return new Not(right).simplify();
        }
        if (right.equals(Val.TRUE)) {
            return new Not(left).simplify();
        }
        if (left.equals(right)) {
            return new Not(left).simplify();
        }
        return new Nand(left, right);
    }
}
