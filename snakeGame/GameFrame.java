import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private MainPage mainPage;
    private GamePanel gamePanel;
    private ActionListener actionListener;
    public boolean modeSelected;
    


    GameFrame() { //Constructor initializes the frame, layout, panels, and sets up the game environment.
        // Initialization of variables and components
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        gamePanel = new Game();
        modeSelected = false;

        // Set up the frame and show the main page initially
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeSelected) { 
                    showGamePanel();
                };
            }
        };

        mainPage = new MainPage(gamePanel, this, actionListener);

        cardPanel.add(mainPage, "MainPage");
        cardPanel.add(gamePanel, "GamePanel");

        add(cardPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Enable full-screen mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        try {
            gd.setFullScreenWindow(this);
        }catch (Exception e) {
            System.out.println("Error setting full-screen : " + e.getMessage());
            this.setSize(1300, 750);
            this.setLocationRelativeTo(null);
        }finally{
            System.out.println("Full-screen");
        }

    }

    public void showMainPage() { //Displays the main page using CardLayout.
        cardLayout.show(cardPanel, "MainPage");
    }

    public void showGamePanel() {//Switches to the game panel and starts the game
        gamePanel.startGame();
        cardLayout.show(cardPanel, "GamePanel");
        gamePanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame().setVisible(true);
        });
    }
}