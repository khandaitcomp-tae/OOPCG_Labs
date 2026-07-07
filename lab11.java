import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Point {
    int x, y;
    Point(int a, int b) {
        x = a;
        y = b;
    }
}

public class SutherlandHodgmanClipping extends JPanel {
    List<Point> originalPolygon, clippedPolygon;
    int xmin, ymin, xmax, ymax;

    public SutherlandHodgmanClipping(List<Point> polygon, int xmin, int ymin, int xmax, int ymax) {
        this.originalPolygon = polygon;
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;

        clippedPolygon = clipPolygon(originalPolygon, xmin, ymin, xmax, ymax);
    }

    // Clipping method using Sutherland-Hodgman
    private List<Point> clipPolygon(List<Point> polygon, int xmin, int ymin, int xmax, int ymax) {
        polygon = clipLeft(polygon, xmin);
        polygon = clipRight(polygon, xmax);
        polygon = clipBottom(polygon, ymin);
        polygon = clipTop(polygon, ymax);
        return polygon;
    }

    // Clipping boundaries
    private List<Point> clipLeft(List<Point> poly, int xmin) {
        return clip(poly, xmin, true, true);
    }

    private List<Point> clipRight(List<Point> poly, int xmax) {
        return clip(poly, xmax, true, false);
    }

    private List<Point> clipBottom(List<Point> poly, int ymin) {
        return clip(poly, ymin, false, true);
    }

    private List<Point> clipTop(List<Point> poly, int ymax) {
        return clip(poly, ymax, false, false);
    }

    private List<Point> clip(List<Point> poly, int bound, boolean vertical, boolean insideIfLess) {
        List<Point> newPoly = new ArrayList<>();
        int n = poly.size();

        for (int i = 0; i < n; i++) {
            Point curr = poly.get(i);
            Point prev = poly.get((i + n - 1) % n);

            int c = vertical ? curr.x : curr.y;
            int p = vertical ? prev.x : prev.y;

            boolean currIn = insideIfLess ? c >= bound : c <= bound;
            boolean prevIn = insideIfLess ? p >= bound : p <= bound;

            if (prevIn && currIn) {
                newPoly.add(curr);
            } else if (prevIn && !currIn) {
                newPoly.add(intersect(prev, curr, bound, vertical));
            } else if (!prevIn && currIn) {
                newPoly.add(intersect(prev, curr, bound, vertical));
                newPoly.add(curr);
            }
        }
        return newPoly;
    }

    // Intersection point between line and clipping boundary
    private Point intersect(Point a, Point b, int bound, boolean vertical) {
        float x, y;
        float dx = b.x - a.x;
        float dy = b.y - a.y;

        if (vertical) {
            x = bound;
            y = a.y + dy * (bound - a.x) / dx;
        } else {
            y = bound;
            x = a.x + dx * (bound - a.y) / dy;
        }

        return new Point(Math.round(x), Math.round(y));
    }

    // Draw method
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw original polygon (gray)
        g.setColor(Color.LIGHT_GRAY);
        drawPolygon(g, originalPolygon);

        // Draw clipping rectangle (black)
        g.setColor(Color.BLACK);
        g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);

        // Draw clipped polygon (red)
        g.setColor(Color.RED);
        drawPolygon(g, clippedPolygon);
    }

    private void drawPolygon(Graphics g, List<Point> poly) {
        int n = poly.size();
        for (int i = 0; i < n; i++) {
            Point a = poly.get(i);
            Point b = poly.get((i + 1) % n);
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }

    // Entry point
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of polygon vertices: ");
        int n = sc.nextInt();
        List<Point> polygon = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter x y for vertex " + (i + 1) + ": ");
            int x = sc.nextInt();
            int y = sc.nextInt();
            polygon.add(new Point(x, y));
        }

        System.out.print("Enter clipping rectangle xmin ymin xmax ymax: ");
        int xmin = sc.nextInt();
        int ymin = sc.nextInt();
        int xmax = sc.nextInt();
        int ymax = sc.nextInt();

        JFrame frame = new JFrame("Sutherland-Hodgman Polygon Clipping");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new SutherlandHodgmanClipping(polygon, xmin, ymin, xmax, ymax));
        frame.setVisible(true);
    }
}

Write a C/C++/Java program to implement the Cohen-Sutherland line clip ing algorithm.
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class CohenSutherlandClipping extends JPanel {
    // Region codes
    static final int INSIDE = 0; // 0000
    static final int LEFT = 1;   // 0001
    static final int RIGHT = 2;  // 0010
    static final int BOTTOM = 4; // 0100
    static final int TOP = 8;    // 1000

    int x1, y1, x2, y2; // Line endpoints
    int xmin, ymin, xmax, ymax; // Clipping window
    int[] clippedLine = new int[4];

    public CohenSutherlandClipping(int x1, int y1, int x2, int y2, int xmin, int ymin, int xmax, int ymax) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.xmin = xmin; this.ymin = ymin;
        this.xmax = xmax; this.ymax = ymax;

        boolean visible = cohenSutherlandClip();
        if (!visible) clippedLine = null;
    }

    // Compute region code for a point (x, y)
    int computeCode(int x, int y) {
        int code = INSIDE;

        if (x < xmin)
            code |= LEFT;
        else if (x > xmax)
            code |= RIGHT;
        if (y < ymin)
            code |= BOTTOM;
        else if (y > ymax)
            code |= TOP;

        return code;
    }

    // Cohen-Sutherland clipping algorithm
    boolean cohenSutherlandClip() {
        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);
        boolean accept = false;

        while (true) {
            if ((code1 | code2) == 0) {
                // Trivially accepted
                accept = true;
                clippedLine[0] = x1;
                clippedLine[1] = y1;
                clippedLine[2] = x2;
                clippedLine[3] = y2;
                break;
            } else if ((code1 & code2) != 0) {
                // Trivially rejected
                break;
            } else {
                int codeOut = (code1 != 0) ? code1 : code2;
                int x = 0, y = 0;

                if ((codeOut & TOP) != 0) {
                    x = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
                    y = ymax;
                } else if ((codeOut & BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
                    y = ymin;
                } else if ((codeOut & RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (xmax - x1) / (x2 - x1);
                    x = xmax;
                } else if ((codeOut & LEFT) != 0) {
                    y = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
                    x = xmin;
                }

                if (codeOut == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    code2 = computeCode(x2, y2);
                }
            }
        }

        return accept;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw clipping rectangle
        g.setColor(Color.BLACK);
        g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);

        // Draw original line
        g.setColor(Color.GRAY);
        g.drawLine(this.x1, this.y1, this.x2, this.y2);

        // Draw clipped line
        if (clippedLine != null) {
            g.setColor(Color.RED);
            g.drawLine(clippedLine[0], clippedLine[1], clippedLine[2], clippedLine[3]);
        } else {
            g.setColor(Color.BLUE);
            g.drawString("Line completely outside and rejected", 20, 20);
        }
    }

    // Main method for user input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter line endpoints (x1 y1 x2 y2): ");
        int x1 = sc.nextInt(), y1 = sc.nextInt(), x2 = sc.nextInt(), y2 = sc.nextInt();

        System.out.println("Enter clipping window (xmin ymin xmax ymax): ");
        int xmin = sc.nextInt(), ymin = sc.nextInt(), xmax = sc.nextInt(), ymax = sc.nextInt();

        JFrame frame = new JFrame("Cohen-Sutherland Line Clipping");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CohenSutherlandClipping(x1, y1, x2, y2, xmin, ymin, xmax, ymax));
        frame.setVisible(true);
    }
}
