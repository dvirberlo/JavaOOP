package geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntervalTest {

    @Test
    void testConstructor() {
        Interval interval = new Interval(3, 5);
        Assertions.assertEquals(3, interval.start(), 0);
        Assertions.assertEquals(5, interval.end(), 0);
    }

    @Test
    void testEquals() {
        Interval interval1 = new Interval(3, 5);
        Interval interval2 = new Interval(3, 5);
        Assertions.assertTrue(interval1.equals(interval2));
    }

    @Test
    void testLength() {
        Interval interval = new Interval(3, 5);
        Assertions.assertEquals(2, interval.length(), 0);
    }

    @Test
    void testMiddle() {
        Interval interval = new Interval(3, 5);
        Assertions.assertEquals(4, interval.middle(), 0);
    }

    @Test
    void testContains() {
        Interval interval = new Interval(3, 5);
        Assertions.assertTrue(interval.contains(4));
    }

    @Test
    void testContainsInterval() {
        Interval interval1 = new Interval(2, 6);
        Interval interval2 = new Interval(3, 5);
        Assertions.assertTrue(interval1.contains(interval2));
    }

    @Test
    void testIntersection() {
        Interval interval1 = new Interval(2, 6);
        Interval interval2 = new Interval(3, 5);
        Interval intersection = interval1.intersection(interval2);
        Assertions.assertEquals(new Interval(3, 5).toString(), intersection.toString());
    }

    @Test
    void testIntersects() {
        Interval interval1 = new Interval(2, 6);
        Interval interval2 = new Interval(3, 5);
        Assertions.assertTrue(interval1.intersects(interval2));
    }
}
