import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import presentation.components.BlackTransparentPanel;
import presentation.components.JPanelWithBackground;
import presentation.components.RedButton;
import presentation.components.YellowButton;
import tasks.JBombInstaller;
import utility.Utility;
import values.Dimensions;

public class JBombLauncher extends JFrame {
    private static final int DRAG_AREA_HEIGHT = 50; // The height of the draggable area from the top
    private JPanel parentPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private RedButton redButton1;
    private JLabel imageBackgroundLabel;
    private JLabel downloadingContentLabel;
    private RedButton redButton2;
    private YellowButton yellowButton1;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public JBombLauncher() {
        initUI();
    }

    private void initUI() {
        setContentPane(parentPanel);
        setResizable(false);
        setUndecorated(true);
        setSize(new Dimension(960, 640));
        setLocationRelativeTo(null);
        setVisible(true);

        // Add mouse listeners for dragging
        Point[] dragPoint = {null};
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Check if the mouse is in the upper draggable area
                if (e.getY() < DRAG_AREA_HEIGHT) {
                    dragPoint[0] = e.getPoint(); // Store the initial drag point
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragPoint[0] = null; // Clear drag point
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragPoint[0] != null) {
                    // Move the window
                    int deltaX = e.getX() - dragPoint[0].x;
                    int deltaY = e.getY() - dragPoint[0].y;
                    setLocation(getX() + deltaX, getY() + deltaY);
                }
            }
        });
    }

    private void createUIComponents() {
        parentPanel = new JPanelWithBackground();
        southPanel = new BlackTransparentPanel();
        redButton1 = new RedButton(" Launch the game ", Dimensions.FONT_SIZE_DEFAULT);
        redButton1.addActionListener(e -> {
            executor.submit(() -> {
                setDownloadStatus(DownloadStatus.DOWNLOADING);
                boolean result = new JBombInstaller().install();
                SwingUtilities.invokeLater(() -> setDownloadStatus(result ? DownloadStatus.DOWNLOADED : DownloadStatus.DOWNLOADING_ERROR));
            });
        });
        downloadingContentLabel = new JLabel("Downloading status");
        downloadingContentLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, Dimensions.FONT_SIZE_LITTLE));
        downloadingContentLabel.setForeground(Color.WHITE);
        northPanel = new BlackTransparentPanel();
        redButton2 = new RedButton("X", Utility.INSTANCE.px(20));
        redButton2.addActionListener(e -> System.exit(0));
        yellowButton1 = new YellowButton("JBomb Launcher", Utility.INSTANCE.px(20));
        setDownloadStatus(DownloadStatus.READY_TO_LAUNCH);
    }

    private void setDownloadStatus(DownloadStatus status) {
        switch (status) {
            case READY_TO_LAUNCH:
                downloadingContentLabel.setText("JBomb is ready to launch!");
                break;
            case DOWNLOADED:
                downloadingContentLabel.setText("Updated downloaded successfully!");
                break;
            case DOWNLOADING:
                downloadingContentLabel.setText("Downloading the update...");
                break;
            case DOWNLOADING_ERROR:
                downloadingContentLabel.setText("There was an error while downloading the update, retry!");
                break;
        }
    }

    public enum DownloadStatus {
        READY_TO_LAUNCH,
        DOWNLOADING,
        DOWNLOADED,
        DOWNLOADING_ERROR
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JBombLauncher::new);
    }
}
