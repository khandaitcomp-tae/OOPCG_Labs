import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Rotation2D extends JPanel {
    double[][] originalPoints;
    double[][] rotatedPoints;

    public Rotation2D(double[][] shape, double[][] transformMatrix) {
        this.originalPoints = shape;
        this.rotatedPoints = applyTransformation(shape, transformMatrix);
    }

    private double[][] applyTransformation(double[][] points, double[][] matrix) {
        double[][] result = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            double x = points[i][0];
            double y = points[i][1];
            result[i][0] = x * matrix[0][0] + y * matrix[0][1] + matrix[0][2];
            result[i][1] = x * matrix[1][0] + y * matrix[1][1] + matrix[1][2];
        }
        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShape(g, originalPoints, Color.BLUE);
        drawShape(g, rotatedPoints, Color.RED);
    }

    private void drawShape(Graphics g, double[][] shape, Color color) {
        g.setColor(color);
        for (int i = 0; i < shape.length; i++) {
            int x1 = (int) shape[i][0], y1 = (int) shape[i][1];
            int x2 = (int) shape[(i + 1) % shape.length][0];
            int y2 = (int) shape[(i + 1) % shape.length][1];
            g.drawLine(x1, y1, x2, y2);
        }
    }

    // Generate rotation matrix about arbitrary point (xr, yr)
    public static double[][] rotationAboutPointMatrix(double angleDeg, double xr, double yr) {
        double angleRad = Math.toRadians(angleDeg);
        double cos = Math.cos(angleRad), sin = Math.sin(angleRad);

        return new double[][] {
            {cos, -sin, xr * (1 - cos) + yr * sin},
            {sin, cos, yr * (1 - cos) - xr * sin},
            {0, 0, 1}
        };
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Example shape: Rectangle
        double[][] shape = {
            {200, 200},
            {300, 200},
            {300, 300},
            {200, 300}
        };

        System.out.println("Choose rotation type:");
        System.out.println("1. About X-axis (flip Y)");
        System.out.println("2. About arbitrary point");
        int choice = sc.nextInt();

        double[][] matrix;

        if (choice == 1) {
            matrix = new double[][] {
                {1, 0, 0},
                {0, -1, 600}, // flip Y over X-axis (assuming height=600)
                {0, 0, 1}
            };
        } else {
            System.out.print("Enter rotation angle (degrees): ");
            double angle = sc.nextDouble();
            System.out.print("Enter X and Y coordinates of rotation point: ");
            double xr = sc.nextDouble(), yr = sc.nextDouble();

            matrix = rotationAboutPointMatrix(angle, xr, yr);
        }

        JFrame frame = new JFrame("2D Rotation - X-axis or Arbitrary Point");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new Rotation2D(shape, matrix));
        frame.setVisible(true);
    }
}

