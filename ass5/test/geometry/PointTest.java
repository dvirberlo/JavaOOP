package geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PointTest {
    @Test
    void coordinatesConstructor() {
        Point p;
        p = new Point(1, 2);
        Assertions.assertEquals(p.getX(), 1);
        Assertions.assertEquals(p.getY(), 2);

        p = new Point(3, 4);
        Assertions.assertEquals(p.getX(), 3);
        Assertions.assertEquals(p.getY(), 4);
    }

    @Test
    void pointConstructor() {
        Point p;
        p = new Point(new Point(1, 2));
        Assertions.assertEquals(p.getX(), 1);
        Assertions.assertEquals(p.getY(), 2);

        p = new Point(new Point(3, 4));
        Assertions.assertEquals(p.getX(), 3);
        Assertions.assertEquals(p.getY(), 4);
    }

    @Test
    void getX() {
        Point p = new Point(1, 2);
        Assertions.assertEquals(p.getX(), 1);
    }

    @Test
    void getY() {
        Point p = new Point(1, 2);
        Assertions.assertEquals(p.getY(), 2);
    }

    @Test
    void distance() {
        Point p1, p2;
        p1 = new Point(1, 2);
        p2 = p1;
        Assertions.assertEquals(p1.distance(p2), 0);

        p1 = new Point(1, 2);
        p2 = new Point(1, 2);
        Assertions.assertEquals(p1.distance(p2), 0);

        p1 = new Point(1, 2);
        p2 = new Point(1, 3);
        Assertions.assertEquals(p1.distance(p2), 1);

        p1 = new Point(1, 2);
        p2 = new Point(2, 3);
        Assertions.assertEquals(p1.distance(p2), Math.sqrt(2));
    }

    @Test
    void equals() {
        Point p1, p2;
        p1 = new Point(1, 2);
        p2 = p1;
        Assertions.assertTrue(p1.equals(p2));

        p1 = new Point(1, 2);
        p2 = new Point(1, 2);
        Assertions.assertTrue(p1.equals(p2));

        p1 = new Point(1, 2);
        p2 = new Point(1, 3);
        Assertions.assertFalse(p1.equals(p2));

        p1 = new Point(1, 2);
        p2 = new Point(2, 3);
        Assertions.assertFalse(p1.equals(p2));

        p1 = new Point(1, 2);
        p2 = null;
        Assertions.assertFalse(p1.equals(p2));
    }

    @Test
    void plus() {
        Point p1, p2, p3;
        p1 = new Point(1, 2);
        p2 = new Point(1, 2);
        p3 = p1.plus(p2);
        Assertions.assertEquals(p3.getX(), 2);
        Assertions.assertEquals(p3.getY(), 4);

        p1 = new Point(1, 2);
        p2 = new Point(1, 3);
        p3 = p1.plus(p2);
        Assertions.assertEquals(p3.getX(), 2);
        Assertions.assertEquals(p3.getY(), 5);

        p1 = new Point(1, 2);
        p2 = new Point(2, 3);
        p3 = p1.plus(p2);
        Assertions.assertEquals(p3.getX(), 3);
        Assertions.assertEquals(p3.getY(), 5);
    }

    @Test
    void minus() {
        Point p1, p2, p3;
        p1 = new Point(1, 2);
        p2 = new Point(1, 2);
        p3 = p1.minus(p2);
        Assertions.assertEquals(p3.getX(), 0);
        Assertions.assertEquals(p3.getY(), 0);

        p1 = new Point(1, 2);
        p2 = new Point(1, 3);
        p3 = p1.minus(p2);
        Assertions.assertEquals(p3.getX(), 0);
        Assertions.assertEquals(p3.getY(), -1);

        p1 = new Point(1, 2);
        p2 = new Point(2, 3);
        p3 = p1.minus(p2);
        Assertions.assertEquals(p3.getX(), -1);
        Assertions.assertEquals(p3.getY(), -1);
    }

    @Test
    void scale() {
        Point p;
        p = new Point(1, 2);
        p = p.scale(2);
        Assertions.assertEquals(p.getX(), 2);
        Assertions.assertEquals(p.getY(), 4);

        p = new Point(1, 2);
        p = p.scale(3);
        Assertions.assertEquals(p.getX(), 3);
        Assertions.assertEquals(p.getY(), 6);
    }

    @Test
    void crossProduct() {
        Point p1, p2;
        p1 = new Point(1, 2);
        p2 = new Point(1, 2);
        Assertions.assertEquals(p1.crossProduct(p2), 0);

        p1 = new Point(1, 2);
        p2 = new Point(1, 3);
        Assertions.assertEquals(p1.crossProduct(p2), 1);

        p1 = new Point(1, 2);
        p2 = new Point(2, 3);
        Assertions.assertEquals(p1.crossProduct(p2), -1);
    }

    @Test
    void dotProduct() {
        Point p1, p2;
        p1 = new Point(1, 2);
        p2 = new Point(1, 2);
        Assertions.assertEquals(p1.dotProduct(p2), 5);

        p1 = new Point(1, 2);
        p2 = new Point(1, 3);
        Assertions.assertEquals(p1.dotProduct(p2), 7);

        p1 = new Point(1, 2);
        p2 = new Point(2, 3);
        Assertions.assertEquals(p1.dotProduct(p2), 8);
    }

    @Test
    void nullPoint() {
        Point p = new Point(1, 2);
        Assertions.assertThrows(NullPointerException.class, () -> p.distance(null));
        Assertions.assertThrows(NullPointerException.class, () -> p.plus(null));
        Assertions.assertThrows(NullPointerException.class, () -> p.minus(null));
        Assertions.assertThrows(NullPointerException.class, () -> new Point(null));
        Assertions.assertThrows(NullPointerException.class, () -> p.crossProduct(null));
        Assertions.assertThrows(NullPointerException.class, () -> p.dotProduct(null));
    }
}
