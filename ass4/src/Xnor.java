//  Dvir Berlowitz

/**
 * This class represents a logical XNOR expression.
 */
public class Xnor extends BinaryExpression {
    private static final String SYMBOL = " # ";

    /**
     * Construct a new Xnor with the specified expressions.
     *
     * @param left  the left expression
     * @param right the right expression
     */
    public Xnor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    protected Boolean apply(Boolean left, Boolean right) {
        return left == right;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Xnor(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        // [ ( A NAND A ) NAND ( B NAND B ) ] NAND ( A NAND B )
        Expression a = this.aNand(), b = this.bNand();
        return new Nand(
                new Nand(new Nand(a, a), new Nand(b, b)),
                new Nand(a, b)
        );
    }

    @Override
    public Expression norify() {
        // [ A NOR ( A NOR B ) ] NOR [ B NOR ( A NOR B ) ]
        Expression a = this.aNor(), b = this.bNor();
        return new Nor(
                new Nor(a, new Nor(a, b)),
                new Nor(b, new Nor(a, b))
        );
    }

    @Override
    protected Expression varSimplify() {
        Expression left = this.getLeft().simplify(), right = this.getRight().simplify();
        if (left.equals(right)) {
            return Val.TRUE;
        }
        return new Xnor(left, right);
    }
}
