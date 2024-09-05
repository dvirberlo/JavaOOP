//  Dvir Berlowitz

import java.util.List;
import java.util.Map;

/**
 * This interface represents a logical expression.
 */
public interface Expression {
    /**
     * Evaluate the expression using the variable assignments provided in the assignment map.
     *
     * @param assignment map of variables to their values
     * @return the result of the evaluation
     * @throws Exception if the assignment is not complete (i.e., some variables in the expression
     *                   were not assigned a value).
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * Evaluate the expression without variable assignments.
     *
     * @return the result of the evaluation
     * @throws Exception if there are variables in the expression
     */
    Boolean evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     *
     * @return list of variables
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     *
     * @return string representation
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable var are replaced with the
     * provided expression (Does not modify the current expression).
     *
     * @param var        the variable to replace
     * @param expression the expression to replace var with
     * @return the new expression
     */
    Expression assign(String var, Expression expression);

    /**
     * Returns the expression in Nand format.
     *
     * @return the expression in Nand format
     */
    Expression nandify();

    /**
     * Returns the expression in Nor format.
     *
     * @return the expression in Nor format
     */
    Expression norify();

    /**
     * Simplifies the expression.
     *
     * @return the simplified expression
     */
    Expression simplify();
}
