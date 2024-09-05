//  Dvir Berlowitz

/**
 * This class represents a logical NOR expression.
 */
public class Nor extends BinaryExpression {
    private static final String SYMBOL = " V ";

    /**
     * Construct a new Nor with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    public Nor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected Boolean apply(Boolean left, Boolean right) {
        return !(left || right);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Nor(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //  [ ( A NAND A ) NAND ( B NAND B ) ] NAND [ ( A NAND A ) NAND ( B NAND B ) ]
        Expression a = this.aNand(), b = this.bNand();
        return new Nand(
                new Nand(new Nand(a, a), new Nand(b, b)),
                new Nand(new Nand(a, a), new Nand(b, b))
        );
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    protected Expression varSimplify() {
        Expression left = this.getLeft().simplify(), right = this.getRight().simplify();
        if (left.equals(Val.TRUE) || right.equals(Val.TRUE)) {
            return Val.FALSE;
        }
        if (left.equals(Val.FALSE)) {
            return new Not(right).simplify();
        }
        if (right.equals(Val.FALSE)) {
            return new Not(left).simplify();
        }
        if (left.equals(right)) {
            return new Not(left).simplify();
        }
        return new Nor(left, right);
    }
}
