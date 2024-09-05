import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ExpressionsTest class.
 * This class tests the Expression interface and its implementations.
 */
public class ExpressionsTests {

    /**
     * Test the evaluate method of the And class.
     */
    @Test
    public void testAndEvaluate() {
        Expression expr1 = new Var("x");
        Expression expr2 = new Var("y");
        And andExpression = new And(expr1, expr2);

        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assignment.put("y", false);

        try {
            assertFalse(andExpression.evaluate(assignment));
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
    }

    /**
     * Test BIU.
     */
    @Test
    public void biuTest() {
        Expression x = new Var("x");
        Expression y = new Var("y");
        Expression z = new Var("z");
        Expression ex = new Xnor(new Nand(x, new Val(false)),
                new Not(new And(new Or(x, y),
                        new Xor(new Val(true), z))));

        assertEquals(ex.toString(), "((x A F) # ~(((x | y) & (T ^ z))))");

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        map.put("y", false);
        map.put("z", false);

        try {
            assertFalse(ex.evaluate(map));
        } catch (Exception ignored) {
        }

        assertEquals(ex.nandify().toString(), "((((x A F) A (x A F)) A ((((((x A x) A (y A y)) A ((T A (T A z)) A (z " +
                "A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z))))) A ((((x A x) A (y A y)) A (" +
                "(T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))))) A (((((x" +
                " A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A " +
                "(T A z))))) A ((((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T " +
                "A (T A z)) A (z A (T A z)))))))) A ((x A F) A (((((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)" +
                "))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z))))) A ((((x A x) A (y A y)) A ((T A (T A " +
                "z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z))))))))");
        assertEquals(ex.norify().toString(), "(((((x V x) V (F V F)) V ((x V x) V (F V F))) V ((((x V x) V (F V F)) V" +
                " ((x V x) V (F V F))) V (((((x V y) V (x V y)) V ((x V y) V (x V y))) V ((((T V T) V (z V z)) V (T V" +
                " z)) V (((T V T) V (z V z)) V (T V z)))) V ((((x V y) V (x V y)) V ((x V y) V (x V y))) V ((((T V T)" +
                " V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z))))))) V ((((((x V y) V (x V y)) V ((x V y) V" +
                " (x V y))) V ((((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z)))) V ((((x V y) V (x " +
                "V y)) V ((x V y) V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z)))))" +
                " V ((((x V x) V (F V F)) V ((x V x) V (F V F))) V (((((x V y) V (x V y)) V ((x V y) V (x V y))) V ((" +
                "((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z)))) V ((((x V y) V (x V y)) V ((x V " +
                "y) V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z))))))))");
        assertEquals(ex.simplify().toString(), "(T # ~(((x | y) & ~(z))))");
    }

    /**
     * Test the simplify method of the And class.
     */
    @Test
    public void testSimplify() {
        // ex1 = ((x&y)&(x&y))
        Expression ex1 = new And(new And(new Var("x"), new Var("y")),
                new And(new Var("x"), new Var("y")));
        // simplify(ex1) = (x&y)
        assertEquals(ex1.simplify().toString(), "(x & y)");
        // ex2 = ((x&y)&1)
        Expression ex2 = new And(new And(new Var("x"), new Var("y")), new Val(true));
        // simplify(ex2) = (x&y)
        assertEquals(ex2.simplify().toString(), "(x & y)");
        // same tests for OR
        // ex3 = ((x|y)|(x|y))
        Expression ex3 = new Or(new Or(new Var("x"), new Var("y")),
                new Or(new Var("x"), new Var("y")));
        // simplify(ex3) = (x|y)
        assertEquals(ex3.simplify().toString(), "(x | y)");
        // ex4 = ((x|y)|0)
        Expression ex4 = new Or(new Or(new Var("x"), new Var("y")), new Val(false));
        // simplify(ex4) = (x|y)
        assertEquals(ex4.simplify().toString(), "(x | y)");
        // same tests for NOT
        // ex5 = ~(~x)
        // Expression ex5 = new Not(new Not(new Var("x")));
        // simplify(ex5) = x
        // assertEquals(ex5.simplify().toString(), "x");
        // ex6 = ~(~1)
        Expression ex6 = new Not(new Not(new Val(true)));
        // simplify(ex6) = 1
        assertEquals(ex6.simplify().toString(), "T");
        // same tests for XOR
        // ex7 = (x^x)
        Expression ex7 = new Xor(new Var("x"), new Var("x"));
        // simplify(ex7) = F
        assertEquals(ex7.simplify().toString(), "F");
        // ex8 = (x^T)
        Expression ex8 = new Xor(new Var("x"), new Val(true));
        // simplify(ex8) = ~x
        assertEquals(ex8.simplify().toString(), "~(x)");
        // ex9 = ((x^y)^F)
        Expression ex9 = new Xor(new Xor(new Var("x"), new Var("y")), new Val(false));
        // simplify(ex9) = (x^y)
        assertEquals(ex9.simplify().toString(), "(x ^ y)");
        // ex10 = ((x^y)^(x^y))
        Expression ex10 = new Xor(new Xor(new Var("x"), new Var("y")),
                new Xor(new Var("x"), new Var("y")));
        // simplify(ex10) = F
        assertEquals(ex10.simplify().toString(), "F");
        // ex11 ((x^y)^T)
        Expression ex11 = new Xor(new Xor(new Var("x"), new Var("y")), new Val(true));
        // simplify(ex11) = ~(x^y)
        assertEquals(ex11.simplify().toString(), "~((x ^ y))");
        // same tests for NAND
        // ex12 = ((x A x) A (y A y))
        Expression ex12 = new Nand(new Nand(new Var("x"), new Var("x")),
                new Nand(new Var("y"), new Var("y")));
        // simplify(ex12) = (~(x) A ~(y))
        assertEquals(ex12.simplify().toString(), "(~(x) A ~(y))");
        // ex13 = ((x A y) A F)
        Expression ex13 = new Nand(new Nand(new Var("x"), new Var("y")), new Val(false));
        // simplify(ex13) = T
        assertEquals(ex13.simplify().toString(), "T");
        // ex14 = ((x A y) A T)
        Expression ex14 = new Nand(new Nand(new Var("x"), new Var("y")), new Val(true));
        // simplify(ex14) = ~(x A y)
        assertEquals(ex14.simplify().toString(), "~((x A y))");
        // ex15 = ((x A y) A (x A y))
        Expression ex15 = new Nand(new Nand(new Var("x"), new Var("y")),
                new Nand(new Var("x"), new Var("y")));
        // simplify(ex15) = ~(x A y)
        assertEquals(ex15.simplify().toString(), "~((x A y))");
        // same tests for NOR
        // ex16 = ((x V x) V (y V y))
        Expression ex16 = new Nor(new Nor(new Var("x"), new Var("x")),
                new Nor(new Var("y"), new Var("y")));
        // simplify(ex16) = (~(x) V ~(y))
        assertEquals(ex16.simplify().toString(), "(~(x) V ~(y))");
        // ex17 = ((x V y) V F)
        Expression ex17 = new Nor(new Nor(new Var("x"), new Var("y")), new Val(false));
        // simplify(ex17) = ~((x V y))
        assertEquals(ex17.simplify().toString(), "~((x V y))");
        // ex18 = ((x V y) V T)
        Expression ex18 = new Nor(new Nor(new Var("x"), new Var("y")), new Val(true));
        // simplify(ex18) = F
        assertEquals(ex18.simplify().toString(), "F");
        // ex19 = ((x V y) V (x V y))
        Expression ex19 = new Nor(new Nor(new Var("x"), new Var("y")), new Nor(new Var("x"), new Var("y")));
        // simplify(ex19) = ~((x V y))
        assertEquals(ex19.simplify().toString(), "~((x V y))");
        // same tests for XNOR
        // ex20.5 And(Xnor(x, x), y) ⇒ y
        Expression ex205 = new And(new Xnor(new Var("x"), new Var("x")), new Var("y"));
        // simplify(ex205) = y
        assertEquals(ex205.simplify().toString(), "y");
        // ex20 = ((x # x) # (y # y))
        Expression ex20 = new Xnor(new Xnor(new Var("x"), new Var("x")), new Xnor(new Var("y"), new Var("y")));
        // simplify(ex20) = T
        assertEquals(ex20.simplify().toString(), "T");
        // Repeat all tests but opposite side in the expression
        // ex21 = ((1&x)&x)
        Expression ex21 = new And(new And(new Val(true), new Var("x")), new Var("x"));
        // simplify(ex21) = x
        assertEquals(ex21.simplify().toString(), "x");
        // ex22 = ((0&x)&x)
        Expression ex22 = new And(new And(new Val(false), new Var("x")), new Var("x"));
        // simplify(ex22) = F
        assertEquals(ex22.simplify().toString(), "F");
        // ex23 = ((x|1)|x)
        Expression ex23 = new Or(new Or(new Var("x"), new Val(true)), new Var("x"));
        // simplify(ex23) = T
        assertEquals(ex23.simplify().toString(), "T");
        // ex24 = ((x|0)|x)
        Expression ex24 = new Or(new Or(new Var("x"), new Val(false)), new Var("x"));
        // simplify(ex24) = x
        assertEquals(ex24.simplify().toString(), "x");
        // ex27 = (F^(x^y))
        Expression ex27 = new Xor(new Val(false), new Xor(new Var("x"), new Var("y")));
        // simplify(ex27) = (x^y)
        assertEquals(ex27.simplify().toString(), "(x ^ y)");
        // ex28 = (T^(x^y))
        Expression ex28 = new Xor(new Val(true), new Xor(new Var("x"), new Var("y")));
        // simplify(ex28) = ~(x^y)
        assertEquals(ex28.simplify().toString(), "~((x ^ y))");
        // ex30 = (x^(y^T))
        Expression ex30 = new Xor(new Var("x"), new Xor(new Var("y"), new Val(true)));
        // simplify(ex30) = (x ^ ~(y))
        assertEquals(ex30.simplify().toString(), "(x ^ ~(y))");
        // ex31 = (x^(y^F))
        Expression ex31 = new Xor(new Var("x"), new Xor(new Var("y"), new Val(false)));
        // simplify(ex31) = (x^y)
        assertEquals(ex31.simplify().toString(), "(x ^ y)");
        // very complex expression
        // ex32 = ((x&y)&(x&y)) | ((x&y)&(x&y))
        // subexpression = (x&y)
        Expression subExpression = new And(new Var("x"), new Var("y"));
        Expression ex32 = new Or(new And(subExpression, subExpression), new And(subExpression, subExpression));
        // simplify(ex32) = (x&y)
        assertEquals(ex32.simplify().toString(), "(x & y)");
        // ex33 = ((x&y)&(x&y)) | ((x&y)&F)
        Expression ex33 = new Or(new And(subExpression, subExpression), new And(subExpression, new Val(false)));
        // simplify(ex33) = (x&y)
        assertEquals(ex33.simplify().toString(), "(x & y)");
        // ex34 = ((x&y)&(x&y)) | ((x&y)&T)
        Expression ex34 = new Or(new And(subExpression, subExpression), new And(subExpression, new Val(true)));
        // simplify(ex34) = (x&y)
        assertEquals(ex34.simplify().toString(), "(x & y)");
        // very very very with many types of expressions - XOR NAND NOR OR AND
        // A - NAND, V - NOR, ^ - XOR, | - OR, & - AND
        // ex35 = ((x^y) A (x^y)) V ((x^y) A (x^y))
        // ex35 = (~(x^y)) V (~(x^y))
        // subexpression = (x^y)
        subExpression = new Xor(new Var("x"), new Var("y"));
        Expression ex35 = new Nor(new Nand(subExpression, subExpression), new Nand(subExpression, subExpression));
        // simplify(ex35) = ~(~(x^y))
        assertEquals(ex35.simplify().toString(), "~(~((x ^ y)))");
        // ex36 = ((x^y) A (x^y)) V ((x^y) A F)
        // ex36 = (~(x^y)) V T
        Expression ex36 = new Nor(new Nand(subExpression, subExpression), new Nand(subExpression, new Val(false)));
        // simplify(ex36) = F
        assertEquals(ex36.simplify().toString(), "F");
    }

    /**
     * Test the evaluate method.
     */
    @Test
    void evaluateTest() {
        // ex1 = (T | F)
        Expression ex1 = new Or(new Val(true), new Val(false));
        // evaluate(ex1) = T
        try {
            assertEquals(ex1.evaluate(), true);
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
        // ex2 = (T & F)
        Expression ex2 = new And(new Val(true), new Val(false));
        // evaluate(ex2) = F
        try {
            assertEquals(ex2.evaluate(), false);
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
        // ex3 = ~(T)
        Expression ex3 = new Not(new Val(true));
        // evaluate(ex3) = F
        try {
            assertEquals(ex3.evaluate(), false);
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
        // ex4 = ~(F)
        Expression ex4 = new Not(new Val(false));
        // evaluate(ex4) = T
        try {
            assertEquals(ex4.evaluate(), true);
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
        // ex5 = (T ^ F)
        Expression ex5 = new Xor(new Val(true), new Val(false));
        // evaluate(ex5) = T
        try {
            assertEquals(ex5.evaluate(), true);
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
        // ex6 = (T ^ T)
        Expression ex6 = new Xor(new Val(true), new Val(true));
        // evaluate(ex6) = F
        try {
            assertEquals(ex6.evaluate(), false);
        } catch (Exception e) {
            fail("Exception thrown during evaluation: " + e.getMessage());
        }
    }
}
