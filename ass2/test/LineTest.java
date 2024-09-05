import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Line2D;
import java.util.List;

class LineTest {
    @Test
    void pointsConstructor() {
        Line l;
        l = new Line(new Point(1, 2), new Point(3, 4));
        Assertions.assertEquals(l.start().getX(), 1);
        Assertions.assertEquals(l.start().getY(), 2);
        Assertions.assertEquals(l.end().getX(), 3);
        Assertions.assertEquals(l.end().getY(), 4);

        l = new Line(new Point(5, 6), new Point(7, 8));
        Assertions.assertEquals(l.start().getX(), 5);
        Assertions.assertEquals(l.start().getY(), 6);
        Assertions.assertEquals(l.end().getX(), 7);
        Assertions.assertEquals(l.end().getY(), 8);
    }

    @Test
    void coordinatesConstructor() {
        Line l;
        l = new Line(1, 2, 3, 4);
        Assertions.assertEquals(l.start().getX(), 1);
        Assertions.assertEquals(l.start().getY(), 2);
        Assertions.assertEquals(l.end().getX(), 3);
        Assertions.assertEquals(l.end().getY(), 4);

        l = new Line(5, 6, 7, 8);
        Assertions.assertEquals(l.start().getX(), 5);
        Assertions.assertEquals(l.start().getY(), 6);
        Assertions.assertEquals(l.end().getX(), 7);
        Assertions.assertEquals(l.end().getY(), 8);
    }

    @Test
    void equals() {
        Line l1, l2;
        l1 = new Line(new Point(1, 2), new Point(3, 4));
        l2 = new Line(new Point(1, 2), new Point(3, 4));
        Assertions.assertTrue(l1.equals(l2));
        Assertions.assertTrue(l2.equals(l1));

        l1 = new Line(new Point(1, 2), new Point(3, 4));
        l2 = new Line(new Point(3, 4), new Point(1, 2));
        Assertions.assertTrue(l1.equals(l2));
        Assertions.assertTrue(l2.equals(l1));

        l1 = new Line(new Point(1, 2), new Point(3, 4));
        l2 = new Line(new Point(1, 2), new Point(3, 5));
        Assertions.assertFalse(l1.equals(l2));
        Assertions.assertFalse(l2.equals(l1));

        l1 = new Line(new Point(1, 2), new Point(3, 4));
        l2 = new Line(new Point(1, 2), new Point(4, 4));
        Assertions.assertFalse(l1.equals(l2));
        Assertions.assertFalse(l2.equals(l1));
    }

    @Test
    void length() {
        Line l;
        l = new Line(new Point(1, 2), new Point(3, 4));
        Assertions.assertEquals(l.length(), Math.sqrt(8));

        l = new Line(new Point(5, 6), new Point(7, 8));
        Assertions.assertEquals(l.length(), Math.sqrt(8));
    }

    @Test
    void middle() {
        Line l;
        l = new Line(new Point(1, 2), new Point(3, 4));
        Assertions.assertEquals(l.middle().getX(), 2);
        Assertions.assertEquals(l.middle().getY(), 3);

        l = new Line(new Point(5, 6), new Point(7, 8));
        Assertions.assertEquals(l.middle().getX(), 6);
        Assertions.assertEquals(l.middle().getY(), 7);
    }

    @Test
    void isIntersectingInEdges() {
        Line l1, l2;
        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(1, 3), new Point(3, 3));
        Assertions.assertTrue(l1.isIntersecting(l2));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(3, 3), new Point(3, 1));
        Assertions.assertTrue(l1.isIntersecting(l2));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(1, 1), new Point(3, 3));
        Assertions.assertTrue(l1.isIntersecting(l2));
    }

    @Test
    void isIntersectingNotInEdges() {
        Line l1, l2;
        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(1, 3), new Point(3, 1));
        Assertions.assertTrue(l1.isIntersecting(l2));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(3, 1), new Point(1, 3));
        Assertions.assertTrue(l1.isIntersecting(l2));
    }

    @Test
    void isIntersectingNull() {
        Line l1, l2;
        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = null;
        Assertions.assertThrows(NullPointerException.class, () -> l1.isIntersecting(l2));
    }

    @Test
    void isIntersectingSame() {
        Line l1, l2;
        l1 = new Line(new Point(1, 1), new Point(3, 3));
        Assertions.assertTrue(l1.isIntersecting(l1));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(1, 1), new Point(3, 3));
        Assertions.assertTrue(l1.isIntersecting(l2));
    }

    @Test
    void randomIsIntersecting() {
        int from = -2, to = 5;
        for (int x1 = from; x1 < to; x1++) {
            for (int x2 = from; x2 < to; x2++) {
                for (int x3 = from; x3 < to; x3++) {
                    for (int x4 = from; x4 < to; x4++) {
                        for (int y1 = from; y1 < to; y1++) {
                            for (int y2 = from; y2 < to; y2++) {
                                for (int y3 = from; y3 < to; y3++) {
                                    for (int y4 = from; y4 < to; y4++) {
                                        if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
                                            // Line2D.Double doesn't seem to support point-line intersection
                                            continue;
                                        }
                                        Line2D.Double l1d = new Line2D.Double(x1, y1, x2, y2);
                                        Line2D.Double l2d = new Line2D.Double(x3, y3, x4, y4);
                                        Line l1 = new Line(x1, y1, x2, y2);
                                        Line l2 = new Line(x3, y3, x4, y4);
                                        Assertions.assertEquals(l1.isIntersecting(l2), l1d.intersectsLine(l2d));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    void intersectionWith() {
        Line l1, l2;
        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(1, 3), new Point(3, 1));
        Assertions.assertTrue(l1.intersectionWith(l2).equals(new Point(2, 2)));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(3, 1), new Point(1, 3));
        Assertions.assertTrue(l1.intersectionWith(l2).equals(new Point(2, 2)));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(1, 2), new Point(4, 4));
        Assertions.assertNull(l1.intersectionWith(l2));

        l1 = new Line(new Point(1, 1), new Point(3, 3));
        l2 = new Line(new Point(-3, -3), new Point(-1, -1));
        Assertions.assertNull(l1.intersectionWith(l2));
    }

    @Test
    void intersectionEdgeCases() {
        Line l1, l2;
        Point p;


        // simple intersection
        l1 = new Line(new Point(0, 0), new Point(2, 2));
        l2 = new Line(new Point(0, 2), new Point(2, 0));
        p = new Point(1, 1);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // simple no intersection
        l1 = new Line(new Point(0, 0), new Point(2, 2));
        l2 = new Line(new Point(3, 3), new Point(3, 4));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel no intersection
        l1 = new Line(new Point(0, 0), new Point(2, 2));
        l2 = new Line(new Point(0, 1), new Point(2, 3));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel point intersection
        l1 = new Line(new Point(0, 0), new Point(2, 2));
        l2 = new Line(new Point(2, 2), new Point(3, 3));
        p = new Point(2, 2);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // parallel line intersection
        l1 = new Line(new Point(0, 0), new Point(2, 2));
        l2 = new Line(new Point(1, 1), new Point(3, 3));
        Assertions.assertEquals(l1.intersection(l2), List.of(new Point(1, 1), new Point(2, 2)));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel vertical line intersection
        l1 = new Line(new Point(0, 0), new Point(0, 2));
        l2 = new Line(new Point(0, 1), new Point(0, 3));
        Assertions.assertEquals(l1.intersection(l2), List.of(new Point(0, 1), new Point(0, 2)));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel vertical point intersection
        l1 = new Line(new Point(0, 0), new Point(0, 2));
        l2 = new Line(new Point(0, 2), new Point(0, 3));
        p = new Point(0, 2);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // parallel vertical no intersection
        l1 = new Line(new Point(0, 0), new Point(0, 2));
        l2 = new Line(new Point(0, 3), new Point(0, 4));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel horizontal line intersection
        l1 = new Line(new Point(0, 0), new Point(2, 0));
        l2 = new Line(new Point(1, 0), new Point(3, 0));
        Assertions.assertEquals(l1.intersection(l2), List.of(new Point(1, 0), new Point(2, 0)));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel horizontal point intersection
        l1 = new Line(new Point(0, 0), new Point(2, 0));
        l2 = new Line(new Point(2, 0), new Point(3, 0));
        p = new Point(2, 0);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // parallel horizontal no intersection
        l1 = new Line(new Point(0, 0), new Point(2, 0));
        l2 = new Line(new Point(3, 0), new Point(4, 0));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // parallel horizontal and vertical intersection
        l1 = new Line(new Point(0, 0), new Point(2, 0));
        l2 = new Line(new Point(1, 0), new Point(1, 2));
        p = new Point(1, 0);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // parallel horizontal and vertical no intersection
        l1 = new Line(new Point(0, 0), new Point(2, 0));
        l2 = new Line(new Point(3, 0), new Point(3, 2));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // point and line start intersection
        l1 = new Line(new Point(0, 0), new Point(0, 0));
        l2 = new Line(new Point(0, 0), new Point(2, 2));
        p = new Point(0, 0);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // point and line end intersection
        l1 = new Line(new Point(0, 0), new Point(0, 0));
        l2 = new Line(new Point(2, 2), new Point(0, 0));
        p = new Point(0, 0);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // point and line middle intersection
        l1 = new Line(new Point(1, 1), new Point(1, 1));
        l2 = new Line(new Point(0, 0), new Point(2, 2));
        p = new Point(1, 1);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // point and line collinear no intersection
        l1 = new Line(new Point(3, 3), new Point(3, 3));
        l2 = new Line(new Point(0, 0), new Point(2, 2));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // point and line no intersection
        l1 = new Line(new Point(0, 1), new Point(0, 1));
        l2 = new Line(new Point(0, 0), new Point(2, 2));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // line and point no intersection
        l1 = new Line(new Point(0, 0), new Point(2, 2));
        l2 = new Line(new Point(0, 1), new Point(0, 1));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));

        // point and point intersection
        l1 = new Line(new Point(1, 1), new Point(1, 1));
        l2 = new Line(new Point(1, 1), new Point(1, 1));
        p = new Point(1, 1);
        Assertions.assertEquals(l1.intersection(l2), List.of(p));
        Assertions.assertTrue(l1.isIntersecting(l2));
        Assertions.assertEquals(l1.intersectionWith(l2), p);

        // point and point no intersection
        l1 = new Line(new Point(1, 1), new Point(1, 1));
        l2 = new Line(new Point(2, 2), new Point(2, 2));
        Assertions.assertTrue(l1.intersection(l2).isEmpty());
        Assertions.assertFalse(l1.isIntersecting(l2));
        Assertions.assertNull(l1.intersectionWith(l2));
    }

}
