package presentation.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LauncherButton extends JButton {

    // Shadow color
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 50); // Semi-transparent black
    private static final Color HOVER_COLOR = new Color(150, 150, 150); // Light gray for hover
    private static final Color BUTTON_COLOR = Color.BLACK; // Background color

    public LauncherButton(String text) {
        super(text);
        setFocusable(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(200, 60)); // Width: 200px, Height: 60px

        // Mouse listener for hover effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_COLOR); // Change to hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(BUTTON_COLOR); // Change back to default color
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw the button with shadow
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow
        g2d.setColor(SHADOW_COLOR);
        g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);

        // Draw the button background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Draw the border
        g2d.setColor(new Color(169, 169, 169)); // Light gray border
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Draw the button text
        g2d.setColor(getForeground());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 5; // Center the text
        g2d.drawString(getText(), x, y);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        repaint(); // Repaint the button when the background changes
    }
}
