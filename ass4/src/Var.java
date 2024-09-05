//  Dvir Berlowitz

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a logical variable.
 */
public class Var implements Expression {
    private final String name;

    /**
     * Construct a new Var with the specified name.
     *
     * @param name the name
     * @throws NullPointerException if name is null
     */
    public Var(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.name) || assignment.get(this.name) == null) {
            throw new VariableNotAssignedException(this.name);
        }
        return assignment.get(this.name);
    }

    @Override
    public List<String> getVariables() {
        return List.of(this.name);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (this.name.equals(var)) {
            return expression;
        }
        return this;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.evaluate(Map.of());
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
