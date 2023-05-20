import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaintSoftware extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Color currentColor;
    private int brushSize;
    private boolean isDrawing;
    private Point previousPoint;

    private JPanel canvasPanel;

    public PaintSoftware() {
        setTitle("Paint Software");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        // Create the canvas panel
        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDrawing = true;
                previousPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
            }
        });
        canvasPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    Graphics2D g2d = (Graphics2D) canvasPanel.getGraphics();
                    if (currentColor == Color.WHITE) {
                        g2d.setStroke(new BasicStroke(brushSize));
                    } else {
                        g2d.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                                1.0f, new float[]{5.0f}, 0.0f));
                    }
                    g2d.setColor(currentColor);
                    g2d.drawLine(previousPoint.x, previousPoint.y, e.getX(), e.getY());
                    previousPoint = e.getPoint();
                }
            }
        });
        add(canvasPanel, BorderLayout.CENTER);

        // Create the color panel
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(2, 5));
        colorPanel.setPreferredSize(new Dimension(WIDTH, 50));
        add(colorPanel, BorderLayout.NORTH);

        JButton[] colorButtons = new JButton[]{
                new JButton("Black"),
                new JButton("Red"),
                new JButton("Blue"),
                new JButton("Green"),
                new JButton("Yellow"),
                new JButton("Orange"),
                new JButton("Pink"),
                new JButton("Magenta"),
                new JButton("Cyan"),
                new JButton("Gray"),
                new JButton("Eraser")
        };

        for (JButton button : colorButtons) {
            button.addActionListener(e -> {
                String buttonText = button.getText();
                if (buttonText.equals("Eraser")) {
                    currentColor = Color.WHITE;
                } else {
                    currentColor = button.getBackground();
                }
            });
            colorPanel.add(button);
        }

        // Create the brush size panel
        JPanel brushSizePanel = new JPanel();
        brushSizePanel.setPreferredSize(new Dimension(WIDTH, 50));
        add(brushSizePanel, BorderLayout.SOUTH);

        JButton smallButton = new JButton("Small");
        smallButton.addActionListener(e -> brushSize = 5);
        brushSizePanel.add(smallButton);

        JButton mediumButton = new JButton("Medium");
        mediumButton.addActionListener(e -> brushSize = 10);
        brushSizePanel.add(mediumButton);

        JButton largeButton = new JButton("Large");
        largeButton.addActionListener(e -> brushSize = 15);
        brushSizePanel.add(largeButton);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaintSoftware::new);
    }
}
