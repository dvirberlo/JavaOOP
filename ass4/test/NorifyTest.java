import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NorifyTest {
    private static final Var A = new Var("A"), B = new Var("B");

    /**
     * nor table:
     * not: A NOR A
     * and: ( A NOR A ) NOR ( B NOR B )
     * or: ( A NOR B ) NOR ( A NOR B )
     * nand: [ ( A NOR A ) NOR ( B NOR B ) ] NOR [ ( A NOR A ) NOR ( B NOR B ) ]
     * nor: A NOR B
     * xor:[ ( A NOR A ) NOR ( B NOR B ) ] NOR ( A NOR B )
     * xnor: [ A NOR ( A NOR B ) ] NOR [ B NOR ( A NOR B ) ]
     */

    @Test
    void not() {
        Expression expected = new Nor(A, A);
        Expression actual = new Not(A).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void and() {
        Expression expected = new Nor(new Nor(A, A), new Nor(B, B));
        Expression actual = new And(A, B).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void or() {
        Expression expected = new Nor(new Nor(A, B), new Nor(A, B));
        Expression actual = new Or(A, B).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void nand() {
        Expression expected = new Nor(new Nor(new Nor(A, A), new Nor(B, B)),
                new Nor(new Nor(A, A), new Nor(B, B)));
        Expression actual = new Nand(A, B).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void nor() {
        Expression expected = new Nor(A, B);
        Expression actual = new Nor(A, B).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void xor() {
        Expression expected = new Nor(new Nor(new Nor(A, A), new Nor(B, B)), new Nor(A, B));
        Expression actual = new Xor(A, B).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void xnor() {
        Expression expected = new Nor(new Nor(A, new Nor(A, B)), new Nor(B, new Nor(A, B)));
        Expression actual = new Xnor(A, B).norify();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }
}
