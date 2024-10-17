import data.DataInputOutput;
import model.ExecutionStatus;
import presentation.components.BlackTransparentPanel;
import presentation.components.JPanelWithBackground;
import presentation.components.RedButton;
import presentation.components.YellowButton;
import presentation.localization.Localization;
import usecases.LaunchOrUpdateGameUseCase;
import usecases.OpenBrowserUseCase;
import utility.Paths;
import utility.Utility;
import values.Dimensions;
import values.JBombUrls;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JBombLauncher extends JFrame {
    private static final int DRAG_AREA_HEIGHT = 50; // The height of the draggable area from the top
    private ExecutionStatus executionStatus = ExecutionStatus.READY_TO_LAUNCH;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private JPanel parentPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private RedButton redButton1;
    private JLabel imageBackgroundLabel;
    private JLabel downloadingContentLabel;
    private RedButton redButton2;
    private YellowButton yellowButton1;
    private YellowButton yellowButton2;

    // Constructor
    public JBombLauncher() {
        initUI();
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JBombLauncher::new);
        initData();
    }

    // Load necessary data
    private static void initData() {
        DataInputOutput.INSTANCE.retrieveData();
    }

    // Initialize the UI
    private void initUI() {
        initializeFrameSettings();
        addWindowDragListeners();
    }

    // Set up basic frame properties (size, location, etc.)
    private void initializeFrameSettings() {
        setIconImage(Utility.INSTANCE.loadImage(Paths.getIconPath()));
        setContentPane(parentPanel);
        setResizable(false);
        setUndecorated(true);
        setSize(new Dimension(960, 487));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Add listeners for dragging the window around
    private void addWindowDragListeners() {
        Point[] dragPoint = {null};

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
                    int deltaX = e.getX() - dragPoint[0].x;
                    int deltaY = e.getY() - dragPoint[0].y;
                    setLocation(getX() + deltaX, getY() + deltaY); // Move window
                }
            }
        });
    }

    // Create UI components (buttons, labels, etc.)
    private void createUIComponents() {
        setupPanels();
        setupButtons();
        setupLabels();
    }

    // Initialize the parent, north, and south panels
    private void setupPanels() {
        parentPanel = new JPanelWithBackground();
        northPanel = new BlackTransparentPanel();
        southPanel = new BlackTransparentPanel();
    }

    // Create and configure buttons
    private void setupButtons() {
        // Button to launch the game
        redButton1 = new RedButton(Localization.getTranslation(Localization.LAUNCH_GAME), Dimensions.FONT_SIZE_DEFAULT);
        redButton1.addActionListener(e -> launchGame());

        // Button to close the application
        redButton2 = new RedButton("X", Utility.INSTANCE.px(20));
        redButton2.addActionListener(e -> System.exit(0));

        // Button to open the "About" page
        yellowButton2 = new YellowButton(Localization.getTranslation(Localization.ABOUT), Utility.INSTANCE.px(20));
        yellowButton2.addActionListener(e -> openAboutPage());
    }

    // Create and configure labels
    private void setupLabels() {
        downloadingContentLabel = new JLabel();
        downloadingContentLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, Dimensions.FONT_SIZE_LITTLE));
        downloadingContentLabel.setForeground(Color.WHITE);

        // Display welcome text initially
        SwingUtilities.invokeLater(() -> downloadingContentLabel.setText(Localization.getTranslation(Localization.WELCOME)));
    }

    // Handle launching the game in a background thread
    private void launchGame() {
        if (executionStatus == ExecutionStatus.DOWNLOADING)
            return;

        executor.submit(() -> {
            setDownloadStatus(ExecutionStatus.DOWNLOADING);
            ExecutionStatus result = new LaunchOrUpdateGameUseCase().invoke(true);
            SwingUtilities.invokeLater(() -> setDownloadStatus(result));
        });
    }

    // Open the browser with the website URL
    private void openAboutPage() {
        new OpenBrowserUseCase(JBombUrls.INSTANCE.getWebsite()).invoke(true);
    }

    // Update the download status based on the result
    private void setDownloadStatus(ExecutionStatus status) {
        executionStatus = status;

        redButton1.setText(
                status == ExecutionStatus.DOWNLOADING
                        ? Localization.getTranslation(Localization.PLEASE_WAIT)
                        : Localization.getTranslation(Localization.LAUNCH_GAME)
        );

        switch (status) {
            case READY_TO_LAUNCH:
                downloadingContentLabel.setText(Localization.getTranslation(Localization.READY_TO_LAUNCH));
                break;
            case DOWNLOADED:
                downloadingContentLabel.setText(Localization.getTranslation(Localization.DOWNLOADED_SUCCESS));
                break;
            case DOWNLOADING:
                downloadingContentLabel.setText(Localization.getTranslation(Localization.DOWNLOADING_DESCRIPTION));
                break;
            case DOWNLOADING_ERROR:
                downloadingContentLabel.setText(Localization.getTranslation(Localization.DOWNLOADING_ERROR));
                break;
        }
    }
}
