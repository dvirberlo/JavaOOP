//  Dvir Berlowitz

/**
 * This class represents an exception that is thrown when a variable is not assigned.
 */
public class VariableNotAssignedException extends Exception {
    /**
     * Constructs a new VariableNotAssignedException with the specified variable name.
     *
     * @param var the variable name
     */
    public VariableNotAssignedException(String var) {
        super("Variable \"" + var + "\" was not assigned");
    }
}