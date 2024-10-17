package presentation.components;

import utility.Paths;
import utility.Utility;

import javax.swing.*;
import java.awt.*;


public class JPanelWithBackground extends JPanel {
  private Image backgroundImage;

  public JPanelWithBackground() {
    // Load the background image
    backgroundImage = Utility.INSTANCE.loadImage(Paths.INSTANCE.getBackgroundPath());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (backgroundImage != null) {
      // Get the current panel size
      int panelWidth = getWidth();
      int panelHeight = getHeight();

      // Get the dimensions of the image
      int imageWidth = backgroundImage.getWidth(this);
      int imageHeight = backgroundImage.getHeight(this);

      // Calculate the scale factor to fit the image within the panel
      double scale = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);
      int scaledWidth = (int) (imageWidth * scale);
      int scaledHeight = (int) (imageHeight * scale);

      // Calculate the x and y position to center the image
      int x = (panelWidth - scaledWidth) / 2;
      int y = (panelHeight - scaledHeight) / 2;

      // Draw the scaled image at the calculated position
      g.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
    }
  }
}
