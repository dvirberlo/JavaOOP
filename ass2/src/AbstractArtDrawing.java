//  Dvir Berlowitz

import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.Objects;
import java.util.Random;

/**
 * This {@code AbstractArtDrawing} class is in charge of drawing random lines on the screen.
 */
public class AbstractArtDrawing {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final String TITLE = "Abstract Art Drawing";

    private static final int NUM_OF_LINES = 10;
    private static final int POINT_RADIUS = 3;
    private static final Color LINE_COLOR = Color.BLACK;
    private static final Color MIDDLE_POINT_COLOR = Color.BLUE;
    private static final Color INTERSECTION_POINT_COLOR = Color.RED;
    private static final Color TRIANGLE_COLOR = Color.GREEN;

    /**
     * The method is in charge of generating a random line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random random = new Random();
        GUI gui = new GUI(TITLE, WIDTH, HEIGHT);
        DrawSurface surface = gui.getDrawSurface();

        Line[] lines = new Line[NUM_OF_LINES];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = generateRandomLine(random);
        }

        // draw the lines
        for (Line line : lines) {
            drawLine(line, surface, LINE_COLOR);
        }
        // draw triangles
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                for (int k = j + 1; k < lines.length; k++) {
                    Point a = lines[i].intersectionWith(lines[j]), b = lines[i].intersectionWith(lines[k]),
                            c = lines[j].intersectionWith(lines[k]);
                    if (a != null && b != null && c != null) {
                        drawTriangle(surface, a, b, c);
                    }
                }
            }
        }
        // draw the middle points of the lines
        for (Line line : lines) {
            drawPoint(surface, line.middle(), MIDDLE_POINT_COLOR);
        }
        // draw the intersection points
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                Point intersection = lines[i].intersectionWith(lines[j]);
                if (intersection != null) {
                    drawPoint(surface, intersection, INTERSECTION_POINT_COLOR);
                }
            }
        }
        gui.show(surface);
    }

    /**
     * The method is in charge of generating a random line.
     *
     * @param random the random number generator
     * @return a random line
     */
    private static Line generateRandomLine(Random random) {
        int x1, y1, x2, y2;
        do {
            x1 = random.nextInt(WIDTH) + 1;
            y1 = random.nextInt(HEIGHT) + 1;
            x2 = random.nextInt(WIDTH) + 1;
            y2 = random.nextInt(HEIGHT) + 1;
            // lines with the same start and end points are not allowed
        } while (x1 == x2 && y1 == y2);
        return new Line(x1, y1, x2, y2);
    }

    /**
     * The method is in charge of drawing a point on the screen.
     *
     * @param surface the drawing surface
     * @param point   the point to draw
     * @param color   the color of the point
     * @throws NullPointerException if the drawing surface, the point, or the color is null
     */
    private static void drawPoint(DrawSurface surface, Point point, Color color) {
        Objects.requireNonNull(surface, "DrawSurface surface cannot be null.");
        Objects.requireNonNull(color, "Color color cannot be null.");
        Objects.requireNonNull(point, "Point point cannot be null.");
        surface.setColor(color);
        surface.fillCircle(DoubleMath.round(point.getX()), DoubleMath.round(point.getY()), POINT_RADIUS);
    }

    /**
     * The method is in charge of drawing a line on the screen.
     *
     * @param line    the line to draw
     * @param surface the drawing surface
     * @param color   the color of the line
     * @throws NullPointerException if the drawing surface or the color is null
     */
    private static void drawLine(Line line, DrawSurface surface, Color color) {
        Objects.requireNonNull(surface, "DrawSurface surface cannot be null.");
        Objects.requireNonNull(color, "Color color cannot be null.");
        surface.setColor(color);
        surface.drawLine(DoubleMath.round(line.start().getX()), DoubleMath.round(line.start().getY()),
                DoubleMath.round(line.end().getX()), DoubleMath.round(line.end().getY()));
    }

    /**
     * The method is in charge of drawing a triangle on the screen.
     *
     * @param surface the drawing surface
     * @param a       the first point of the triangle
     * @param b       the second point of the triangle
     * @param c       the third point of the triangle
     * @throws NullPointerException if the drawing surface, or any of the points is null
     */
    private static void drawTriangle(DrawSurface surface, Point a, Point b, Point c) {
        Objects.requireNonNull(surface, "DrawSurface surface cannot be null.");
        Objects.requireNonNull(a, "Point a cannot be null.");
        Objects.requireNonNull(b, "Point b cannot be null.");
        Objects.requireNonNull(c, "Point c cannot be null.");
        Line ab = new Line(a, b), bc = new Line(b, c), ca = new Line(c, a);
        drawLine(ab, surface, TRIANGLE_COLOR);
        drawLine(bc, surface, TRIANGLE_COLOR);
        drawLine(ca, surface, TRIANGLE_COLOR);

    }
}
