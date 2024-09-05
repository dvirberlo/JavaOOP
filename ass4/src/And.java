//  Dvir Berlowitz

/**
 * This class represents a logical AND expression.
 */
public class And extends BinaryExpression {
    private static final String SYMBOL = " & ";

    /**
     * Construct a new And with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected Boolean apply(Boolean left, Boolean right) {
        return left && right;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new And(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        // ( A NAND B ) NAND ( A NAND B )
        Expression a = this.aNand(), b = this.bNand();
        return new Nand(new Nand(a, b), new Nand(a, b));
    }

    @Override
    public Expression norify() {
        // ( A NOR A ) NOR ( B NOR B )
        Expression a = this.aNor(), b = this.bNor();
        return new Nor(new Nor(a, a), new Nor(b, b));
    }

    @Override
    protected Expression varSimplify() {
        Expression left = this.getLeft().simplify(), right = this.getRight().simplify();
        if (left.equals(Val.FALSE) || right.equals(Val.FALSE)) {
            return Val.FALSE;
        }
        if (left.equals(Val.TRUE)) {
            return right;
        }
        if (right.equals(Val.TRUE)) {
            return left;
        }
        if (left.equals(right)) {
            return left;
        }
        return new And(left, right);
    }
}
