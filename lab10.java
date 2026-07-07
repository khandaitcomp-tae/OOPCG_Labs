import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PolygonFiller extends JPanel {
    BufferedImage canvas;
    Color fillColor = Color.RED;
    String fillMethod = "scan";

    int[] xPoints = { 100, 200, 150, 250, 300, 200 };
    int[] yPoints = { 100, 100, 200, 200, 300, 300 };
    int nPoints = xPoints.length;

    public PolygonFiller(String fillMethod) {
        this.fillMethod = fillMethod.toLowerCase();
        canvas = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        clearCanvas();
        drawPolygonOutline();
        if (this.fillMethod.equals("scan")) {
            scanlineFill(xPoints, yPoints, nPoints, fillColor);
        } else if (this.fillMethod.equals("flood")) {
            floodFill(160, 150, Color.BLACK.getRGB(), fillColor.getRGB());
        } else if (this.fillMethod.equals("seed")) {
            seedFill(160, 150, Color.BLACK.getRGB(), fillColor.getRGB());
        }
    }

    public void clearCanvas() {
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 600);
    }

    public void drawPolygonOutline() {
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, nPoints);
    }

    // Scanline fill algorithm
    public void scanlineFill(int[] x, int[] y, int n, Color fillColor) {
        int[] nodes = new int[n];
        for (int yScan = 0; yScan < canvas.getHeight(); yScan++) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;
                if ((y[i] < yScan && y[j] >= yScan) || (y[j] < yScan && y[i] >= yScan)) {
                    nodes[count++] = x[i] + (yScan - y[i]) * (x[j] - x[i]) / (y[j] - y[i]);
                }
            }
            java.util.Arrays.sort(nodes, 0, count);
            for (int i = 0; i < count - 1; i += 2) {
                for (int xScan = nodes[i]; xScan < nodes[i + 1]; xScan++) {
                    if (xScan >= 0 && xScan < canvas.getWidth())
                        canvas.setRGB(xScan, yScan, fillColor.getRGB());
                }
            }
        }
    }

    // Recursive Flood Fill (4-connected)
    public void floodFill(int x, int y, int oldColor, int newColor) {
        if (x < 0 || y < 0 || x >= canvas.getWidth() || y >= canvas.getHeight())
            return;
        if (canvas.getRGB(x, y) != oldColor || canvas.getRGB(x, y) == newColor)
            return;

        canvas.setRGB(x, y, newColor);
        floodFill(x + 1, y, oldColor, newColor);
        floodFill(x - 1, y, oldColor, newColor);
        floodFill(x, y + 1, oldColor, newColor);
        floodFill(x, y - 1, oldColor, newColor);
    }

    // Iterative Seed Fill (Queue based 4-connected)
    public void seedFill(int x, int y, int oldColor, int newColor) {
        if (oldColor == newColor)
            return;

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int px = p.x, py = p.y;

            if (px < 0 || py < 0 || px >= canvas.getWidth() || py >= canvas.getHeight())
                continue;
            if (canvas.getRGB(px, py) != oldColor)
                continue;

            canvas.setRGB(px, py, newColor);

            queue.add(new Point(px + 1, py));
            queue.add(new Point(px - 1, py));
            queue.add(new Point(px, py + 1));
            queue.add(new Point(px, py - 1));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== Concave Polygon Fill Menu =====");
        System.out.println("1. Scanline Fill");
        System.out.println("2. Flood Fill");
        System.out.println("3. Seed Fill");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();

        String method;
        switch (choice) {
            case 1:
                method = "scan";
                break;
            case 2:
                method = "flood";
                break;
            case 3:
                method = "seed";
                break;
            default:
                method = "scan";
                break;
        }

        JFrame frame = new JFrame("Polygon Fill - " + method.toUpperCase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 640);
        frame.add(new PolygonFiller(method));
        frame.setVisible(true);
    }
}

