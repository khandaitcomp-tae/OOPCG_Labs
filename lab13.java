import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ShapeTransformations extends JPanel {
    double[][] originalPoints;
    double[][] transformedPoints;

    public ShapeTransformations(double[][] shape, double[][] transformationMatrix) {
        this.originalPoints = shape;
        this.transformedPoints = applyTransformation(shape, transformationMatrix);
    }

    // Apply 2D Transformation
    public double[][] applyTransformation(double[][] points, double[][] mat) {
        double[][] result = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            double x = points[i][0];
            double y = points[i][1];
            result[i][0] = x * mat[0][0] + y * mat[0][1] + mat[0][2];
            result[i][1] = x * mat[1][0] + y * mat[1][1] + mat[1][2];
        }
        return result;
    }

    // Drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShape(g, originalPoints, Color.BLUE);  // Original
        drawShape(g, transformedPoints, Color.RED); // Transformed
    }

    private void drawShape(Graphics g, double[][] shape, Color color) {
        g.setColor(color);
        for (int i = 0; i < shape.length; i++) {
            int x1 = (int) shape[i][0];
            int y1 = (int) shape[i][1];
            int x2 = (int) shape[(i + 1) % shape.length][0];
            int y2 = (int) shape[(i + 1) % shape.length][1];
            g.drawLine(x1, y1, x2, y2);
        }
    }

    // Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose shape:\n1. Equilateral Triangle\n2. Rhombus");
        int shapeChoice = sc.nextInt();

        double[][] shape;
        if (shapeChoice == 1) {
            shape = new double[][]{
                {200, 200},
                {300, 200},
                {250, 115}
            };
        } else {
            shape = new double[][]{
                {200, 200},
                {250, 150},
                {300, 200},
                {250, 250}
            };
        }

        System.out.println("Choose transformation:");
        System.out.println("1. Translation\n2. Scaling\n3. Rotation\n4. Shear X\n5. Shear Y");
        int transformChoice = sc.nextInt();

        double[][] mat = switch (transformChoice) {
            case 1 -> {
                System.out.print("Enter dx and dy: ");
                double dx = sc.nextDouble(), dy = sc.nextDouble();
                yield new double[][]{
                    {1, 0, dx},
                    {0, 1, dy},
                    {0, 0, 1}
                };
            }
            case 2 -> {
                System.out.print("Enter sx and sy: ");
                double sx = sc.nextDouble(), sy = sc.nextDouble();
                yield new double[][]{
                    {sx, 0, 0},
                    {0, sy, 0},
                    {0, 0, 1}
                };
            }
            case 3 -> {
                System.out.print("Enter angle (degrees): ");
                double angle = Math.toRadians(sc.nextDouble());
                yield new double[][]{
                    {Math.cos(angle), -Math.sin(angle), 0},
                    {Math.sin(angle), Math.cos(angle), 0},
                    {0, 0, 1}
                };
            }
            case 4 -> {
                System.out.print("Enter shear X value: ");
                double shx = sc.nextDouble();
                yield new double[][]{
                    {1, shx, 0},
                    {0, 1, 0},
                    {0, 0, 1}
                };
            }
            case 5 -> {
                System.out.print("Enter shear Y value: ");
                double shy = sc.nextDouble();
                yield new double[][]{
                    {1, 0, 0},
                    {shy, 1, 0},
                    {0, 0, 1}
                };
            }
            default -> new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
            };
        };

        JFrame frame = new JFrame("2D Transformations - Triangle/Rhombus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new ShapeTransformations(shape, mat));
        frame.setVisible(true);
    }
}
