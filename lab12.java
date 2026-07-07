import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Transform2D extends JPanel {
    double[][] points;
    double[][] transformedPoints;
    int n;

    public Transform2D(double[][] points, double[][] matrix) {
        this.points = points;
        this.n = points.length;
        this.transformedPoints = applyTransformation(points, matrix);
    }

    // Apply matrix transformation
    public double[][] applyTransformation(double[][] pts, double[][] mat) {
        double[][] result = new double[n][2];
        for (int i = 0; i < n; i++) {
            double x = pts[i][0], y = pts[i][1];
            result[i][0] = x * mat[0][0] + y * mat[0][1] + mat[0][2];
            result[i][1] = x * mat[1][0] + y * mat[1][1] + mat[1][2];
        }
        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw original polygon in blue
        g.setColor(Color.BLUE);
        drawPolygon(g, points);

        // Draw transformed polygon in red
        g.setColor(Color.RED);
        drawPolygon(g, transformedPoints);
    }

    private void drawPolygon(Graphics g, double[][] poly) {
        for (int i = 0; i < poly.length; i++) {
            int x1 = (int) poly[i][0], y1 = (int) poly[i][1];
            int j = (i + 1) % poly.length;
            int x2 = (int) poly[j][0], y2 = (int) poly[j][1];
            g.drawLine(x1, y1, x2, y2);
        }
    }

    // Helper to get transformation matrix
    public static double[][] getTransformationMatrix(String type, Scanner sc) {
        switch (type.toLowerCase()) {
            case "translate":
                System.out.print("Enter dx and dy: ");
                double dx = sc.nextDouble(), dy = sc.nextDouble();
                return new double[][]{
                        {1, 0, dx},
                        {0, 1, dy},
                        {0, 0, 1}
                };
            case "scale":
                System.out.print("Enter sx and sy: ");
                double sx = sc.nextDouble(), sy = sc.nextDouble();
                return new double[][]{
                        {sx, 0, 0},
                        {0, sy, 0},
                        {0, 0, 1}
                };
            case "rotate":
                System.out.print("Enter angle in degrees: ");
                double angle = Math.toRadians(sc.nextDouble());
                return new double[][]{
                        {Math.cos(angle), -Math.sin(angle), 0},
                        {Math.sin(angle), Math.cos(angle), 0},
                        {0, 0, 1}
                };
            case "shearx":
                System.out.print("Enter shear factor shx: ");
                double shx = sc.nextDouble();
                return new double[][]{
                        {1, shx, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                };
            case "sheary":
                System.out.print("Enter shear factor shy: ");
                double shy = sc.nextDouble();
                return new double[][]{
                        {1, 0, 0},
                        {shy, 1, 0},
                        {0, 0, 1}
                };
            default:
                return new double[][]{
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                };
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Define polygon
        double[][] polygon = {
                {100, 100},
                {200, 100},
                {200, 200},
                {100, 200}
        };

        System.out.println("Choose transformation:");
        System.out.println("1. Translate");
        System.out.println("2. Scale");
        System.out.println("3. Rotate");
        System.out.println("4. Shear X");
        System.out.println("5. Shear Y");
        System.out.print("Enter choice (1-5): ");
        int choice = sc.nextInt();

        String type = switch (choice) {
            case 1 -> "translate";
            case 2 -> "scale";
            case 3 -> "rotate";
            case 4 -> "shearx";
            case 5 -> "sheary";
            default -> "";
        };

        double[][] matrix = getTransformationMatrix(type, sc);

        // Create window and draw
        JFrame frame = new JFrame("2D Transformation - " + type.toUpperCase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new Transform2D(polygon, matrix));
        frame.setVisible(true);
    }
}

