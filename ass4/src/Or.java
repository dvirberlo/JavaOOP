//  Dvir Berlowitz

/**
 * This class represents a logical OR expression.
 */
public class Or extends BinaryExpression {
    private static final String SYMBOL = " | ";

    /**
     * Construct a new Or with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected Boolean apply(Boolean left, Boolean right) {
        return left || right;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Or(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        // ( A NAND A ) NAND ( B NAND B )
        Expression a = this.aNand(), b = this.bNand();
        return new Nand(new Nand(a, a), new Nand(b, b));
    }

    @Override
    public Expression norify() {
        // ( A NOR B ) NOR ( A NOR B )
        Expression a = this.aNor(), b = this.bNor();
        return new Nor(new Nor(a, b), new Nor(a, b));
    }

    @Override
    protected Expression varSimplify() {
        Expression left = this.getLeft().simplify(), right = this.getRight().simplify();
        if (left.equals(Val.TRUE) || right.equals(Val.TRUE)) {
            return Val.TRUE;
        }
        if (left.equals(Val.FALSE)) {
            return right;
        }
        if (right.equals(Val.FALSE)) {
            return left;
        }
        if (left.equals(right)) {
            return left;
        }
        return new Or(left, right);
    }
}
