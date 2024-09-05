//  Dvir Berlowitz

/**
 * This class represents a logical XOR expression.
 */
public class Xor extends BinaryExpression {
    private static final String SYMBOL = " ^ ";

    /**
     * Construct a new Xor with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    public Xor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected Boolean apply(Boolean left, Boolean right) {
        return left ^ right;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Xor(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        // [ A NAND ( A NAND B ) ] NAND [ B NAND ( A NAND B ) ]
        Expression a = this.aNand(), b = this.bNand();
        return new Nand(
                new Nand(a, new Nand(a, b)),
                new Nand(b, new Nand(a, b))
        );
    }

    @Override
    public Expression norify() {
        // [ ( A NOR A ) NOR ( B NOR B ) ] NOR ( A NOR B )
        Expression a = this.aNor(), b = this.bNor();
        return new Nor(
                new Nor(new Nor(a, a), new Nor(b, b)),
                new Nor(a, b)
        );
    }

    @Override
    protected Expression varSimplify() {
        Expression left = this.getLeft().simplify(), right = this.getRight().simplify();
        if (left.equals(Val.TRUE)) {
            return new Not(right).simplify();
        }
        if (left.equals(Val.FALSE)) {
            return right;
        }
        if (right.equals(Val.TRUE)) {
            return new Not(left).simplify();
        }
        if (right.equals(Val.FALSE)) {
            return left;
        }
        if (left.equals(right)) {
            return Val.FALSE;
        }
        return new Xor(left, right);
    }
}
