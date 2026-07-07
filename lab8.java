import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class LineDrawing extends JPanel {
    BufferedImage canvas;
    public LineDrawing() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        // Draw Rectangles with DDA
        drawDDA(50, 50, 200, 50, "dotted", Color.BLUE);
        drawDDA(200, 50, 200, 200, "dotted", Color.BLUE);
        drawDDA(200, 200, 50, 200, "dotted", Color.BLUE);
        drawDDA(50, 200, 50, 50, "dotted", Color.BLUE);

        drawDDA(300, 50, 450, 50, "thick", Color.RED);
        drawDDA(450, 50, 450, 200, "thick", Color.RED);
        drawDDA(450, 200, 300, 200, "thick", Color.RED);
        drawDDA(300, 200, 300, 50, "thick", Color.RED);

        // Draw Diamond with Bresenham
        drawBresenham(150, 300, 250, 400, "dashed", Color.GREEN);
        drawBresenham(250, 400, 150, 500, "dashed", Color.GREEN);
        drawBresenham(150, 500, 50, 400, "dashed", Color.GREEN);
        drawBresenham(50, 400, 150, 300, "dashed", Color.GREEN);

        drawBresenham(400, 300, 500, 400, "solid", Color.MAGENTA);
        drawBresenham(500, 400, 400, 500, "solid", Color.MAGENTA);
        drawBresenham(400, 500, 300, 400, "solid", Color.MAGENTA);
        drawBresenham(300, 400, 400, 300, "solid", Color.MAGENTA);
    }

    public void drawDDA(int x1, int y1, int x2, int y2, String style, Color color) {
        Graphics2D g = canvas.createGraphics();
        g.setColor(color);

        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            if (style.equals("dotted")) {
                if (i % 4 == 0) canvas.setRGB(Math.round(x), Math.round(y), color.getRGB());
            } else if (style.equals("thick")) {
                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        int px = Math.round(x) + j;
                        int py = Math.round(y) + k;
                        if (px >= 0 && py >= 0 && px < 800 && py < 600)
                            canvas.setRGB(px, py, color.getRGB());
                    }
                }
            }
            x += xInc;
            y += yInc;
        }
    }

    public void drawBresenham(int x1, int y1, int x2, int y2, String style, Color color) {
        Graphics2D g = canvas.createGraphics();
        g.setColor(color);

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSteep = dy > dx;
        int err = (isSteep ? dy : dx) / 2;
        int count = 0;

        while (true) {
            boolean plot = true;

            if (style.equals("dashed")) {
                if ((count / 10) % 2 == 0) plot = true;
                else plot = false;
            } else if (style.equals("solid")) {
                plot = true;
            }

            if (plot) canvas.setRGB(x1, y1, color.getRGB());

            if (x1 == x2 && y1 == y2) break;

            if (isSteep) {
                y1 += sy;
                err -= dx;
                if (err < 0) {
                    x1 += sx;
                    err += dy;
                }
            } else {
                x1 += sx;
                err -= dy;
                if (err < 0) {
                    y1 += sy;
                    err += dx;
                }
            }
            count++;
        }
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Line Drawing Algorithms - DDA & Bresenham");
        LineDrawing panel = new LineDrawing();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(820, 640);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
