package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleMathTest {

    @Test
    void testEquality() {
        Assertions.assertTrue(DoubleMath.eq(0.0, 0.0));
        Assertions.assertTrue(DoubleMath.eq(1.0, 1.0));
        Assertions.assertTrue(DoubleMath.eq(0.0, DoubleMath.EPSILON * 0.5));
        Assertions.assertFalse(DoubleMath.eq(0.0, DoubleMath.EPSILON * 2.0));
    }

    @Test
    void testInequality() {
        Assertions.assertTrue(DoubleMath.neq(0.0, 1.0));
        Assertions.assertTrue(DoubleMath.neq(1.0, 0.0));
        Assertions.assertFalse(DoubleMath.neq(0.0, 0.0));
    }

    @Test
    void testLessThan() {
        Assertions.assertTrue(DoubleMath.lt(0.0, 1.0));
        Assertions.assertFalse(DoubleMath.lt(1.0, 0.0));
        Assertions.assertFalse(DoubleMath.lt(0.0, 0.0));
    }

    @Test
    void testGreaterThan() {
        Assertions.assertTrue(DoubleMath.gt(1.0, 0.0));
        Assertions.assertFalse(DoubleMath.gt(0.0, 1.0));
        Assertions.assertFalse(DoubleMath.gt(0.0, 0.0));
    }

    @Test
    void testLessThanOrEqual() {
        Assertions.assertTrue(DoubleMath.leq(0.0, 1.0));
        Assertions.assertTrue(DoubleMath.leq(0.0, 0.0));
        Assertions.assertFalse(DoubleMath.leq(1.0, 0.0));
    }

    @Test
    void testGreaterThanOrEqual() {
        Assertions.assertTrue(DoubleMath.geq(1.0, 0.0));
        Assertions.assertTrue(DoubleMath.geq(0.0, 0.0));
        Assertions.assertFalse(DoubleMath.geq(0.0, 1.0));
    }
}
