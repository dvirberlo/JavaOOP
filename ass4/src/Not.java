//  Dvir Berlowitz

import java.util.List;
import java.util.Map;

/**
 * This class represents a logical NOT expression.
 */
public class Not extends UnaryExpression {
    private static final String SYMBOL = "~";

    /**
     * Construct a new Not with the specified expression.
     *
     * @param expression the inner expression
     * @throws NullPointerException if expression is null
     */
    public Not(Expression expression) {
        super(expression);
    }

    @Override
    protected String getSymbol() {
        return SYMBOL;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !this.getExpression().evaluate(assignment);
    }

    @Override
    public List<String> getVariables() {
        return this.getExpression().getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Not(this.getExpression().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        return new Nand(this.getExpression().nandify(), this.getExpression().nandify());
    }

    @Override
    public Expression norify() {
        return new Nor(this.getExpression().norify(), this.getExpression().norify());
    }

    @Override
    protected Expression varSimplify() {
        Expression expression = this.getExpression().simplify();
        if (expression.equals(Val.TRUE)) {
            return Val.FALSE;
        }
        if (expression.equals(Val.FALSE)) {
            return Val.TRUE;
        }
        return new Not(expression);
    }
}
