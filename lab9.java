import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
public class CircleDrawer extends JPanel {
    BufferedImage canvas;
    int centerX = 300, centerY = 300, radius = 100;
    String algorithm = "Bresenham";
    String style = "Solid";

    public CircleDrawer(String algorithm, String style) {
        this.algorithm = algorithm;
        this.style = style;
        canvas = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        clearCanvas();
        drawCircle(centerX, centerY, radius, style, algorithm);
    }

    public void clearCanvas() {
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 600);
    }

    public void plotCirclePoints(int xc, int yc, int x, int y, Color color, int step) {
        // Step controls dotted/dashed style
        if (step % getSkipFactor(style) != 0) return;

        int[] dx = {x, -x, x, -x, y, -y, y, -y};
        int[] dy = {y, y, -y, -y, x, x, -x, -x};
        for (int i = 0; i < 8; i++) {
            int px = xc + dx[i];
            int py = yc + dy[i];
            if (px >= 0 && py >= 0 && px < 600 && py < 600) {
                canvas.setRGB(px, py, color.getRGB());
            }
        }
    }

    public int getSkipFactor(String style) {
        switch (style.toLowerCase()) {
            case "dotted":
                return 4;
            case "dashed":
                return 2;
            default:
                return 1; // solid
        }
    }

    public void drawCircle(int xc, int yc, int r, String style, String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "dda":
                drawCircleDDA(xc, yc, r, style);
                break;
            case "midpoint":
                drawCircleMidpoint(xc, yc, r, style);
                break;
            default:
                drawCircleBresenham(xc, yc, r, style);
        }
    }

    public void drawCircleBresenham(int xc, int yc, int r, String style) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
        int step = 0;
        while (x <= y) {
            plotCirclePoints(xc, yc, x, y, Color.BLUE, step++);
            if (d < 0)
                d = d + 4 * x + 6;
            else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }

    public void drawCircleMidpoint(int xc, int yc, int r, String style) {
        int x = 0, y = r;
        int p = 1 - r;
        int step = 0;
        while (x <= y) {
            plotCirclePoints(xc, yc, x, y, Color.RED, step++);
            if (p < 0)
                p += 2 * x + 3;
            else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
        }
    }

    public void drawCircleDDA(int xc, int yc, int r, String style) {
        double angle = 0;
        int step = 0;
        while (angle < 360) {
            double rad = Math.toRadians(angle);
            int x = (int) Math.round(xc + r * Math.cos(rad));
            int y = (int) Math.round(yc + r * Math.sin(rad));
            if (step % getSkipFactor(style) == 0 && x >= 0 && y >= 0 && x < 600 && y < 600)
                canvas.setRGB(x, y, Color.MAGENTA.getRGB());
            angle += 0.5;
            step++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(canvas, 0, 0, null);
    }

    // Menu-driven main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String algoChoice = "Bresenham", styleChoice = "Solid";

        System.out.println("=== Circle Drawing Program ===");
        System.out.println("1. DDA\n2. Bresenham\n3. Midpoint");
        System.out.print("Select algorithm: ");
        int algo = sc.nextInt();
        switch (algo) {
            case 1: algoChoice = "DDA"; break;
            case 2: algoChoice = "Bresenham"; break;
            case 3: algoChoice = "Midpoint"; break;
        }

        System.out.println("\nChoose style:\n1. Solid\n2. Dotted\n3. Dashed");
        System.out.print("Select style: ");
        int style = sc.nextInt();
        switch (style) {
            case 1: styleChoice = "Solid"; break;
            case 2: styleChoice = "Dotted"; break;
            case 3: styleChoice = "Dashed"; break;
        }

        JFrame frame = new JFrame("Circle Drawing - " + algoChoice + " [" + styleChoice + "]");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 640);
        frame.add(new CircleDrawer(algoChoice, styleChoice));
        frame.setVisible(true);
    }
}

