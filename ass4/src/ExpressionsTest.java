//  Dvir Berlowitz

import java.util.Map;

/**
 * This class is in charge of testing the various expression classes.
 */
public class ExpressionsTest {
    /**
     * The main method of the class.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        // 1. Create an expression with at least three variables.
        Expression x = new Var("x");
        Expression y = new Var("y");
        Expression z = new Var("z");
        Expression ex = new Xnor(
                new Nand(x, new Val(false)),
                new Not(new And(
                        new Or(x, y),
                        new Xor(new Val(true), z)
                ))
        );

        // 2. Print the expression.
        System.out.println(ex);

        // 3. Print the value of the expression with an assignment to every variable.
        try {
            System.out.println(ex.evaluate(Map.of(
                    "x", true,
                    "y", false,
                    "z", false
            )));
        } catch (Exception ignored) {
        }

        // 4. Print the Nandified version of the expression.
        System.out.println(ex.nandify().toString());

        // 5. Print the Norified version of the expression.
        System.out.println(ex.norify().toString());

        // 6. Print the simplified version of the expression.
        System.out.println(ex.simplify().toString());
    }
}
