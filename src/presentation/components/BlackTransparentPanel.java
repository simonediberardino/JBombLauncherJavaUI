package presentation.components;

import javax.swing.*;
import java.awt.*;

public class BlackTransparentPanel extends JPanel {
    public BlackTransparentPanel() {
        // Make the panel non-opaque to allow transparency
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D to enable more control over drawing
        Graphics2D g2d = (Graphics2D) g.create();

        // Set color with RGBA where A (alpha) is for transparency (0-255)
        // For semi-transparent black: (0, 0, 0, 128)
        g2d.setColor(new Color(0, 0, 0, 128));

        // Fill the panel's background with the semi-transparent color
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose(); // Dispose to release system resources
    }
}
