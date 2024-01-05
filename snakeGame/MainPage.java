import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;


public class MainPage extends JPanel {

    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private JButton startButton;
    private Border border;
    private Dimension screenSize;
    private int frameHeight;
    private int frameWidth;
    private Image image;
    private JFrame frame;
    

    public MainPage(GamePanel gamePanel,GameFrame gameFrame,ActionListener actionListener) {//Constructor initializes the main page with buttons and styling
        // Initialization of variables and components
        frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameHeight = screenSize.height;
        frameWidth = screenSize.width;
        ImageIcon icon = new ImageIcon("snake.png");
        Image getimage = icon.getImage();
        image = getimage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
        this.border = BorderFactory.createLineBorder(Color.red,3);
        this.gamePanel = gamePanel;
        this.gameFrame = gameFrame;
		this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(null);
        this.setBorder(border);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        // Set up the start button and add action listeners
        startButton(actionListener);
	}

    @Override
    public void paintComponent(Graphics g){//Overrides paintComponent to draw the background image on the main page.
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }


    public void startButton(ActionListener actionListener) {//Creates and sets up the start button with specified dimensions and appearance.
        
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Ink Free", Font.BOLD, 80));
        startButton.setForeground(Color.RED);
        startButton.setContentAreaFilled(false);
        startButton.setBorder(null);
        startButton.setFocusPainted(false);
        startButton.setBounds(540, 430, 500, 200);//or startButton.setBounds(740, 580, 500, 200)
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showModeDialog(); 
                actionListener.actionPerformed(e); 
            }
        });

        add(startButton);

    }
    
    private void showModeDialog() {//Displays a dialog for selecting the game difficulty mode.
        String[] options = {"Easy", "Normal", "Hard"};
        int choice = JOptionPane.showOptionDialog(SwingUtilities.getWindowAncestor(MainPage.this), "Select Difficultty", "Game Difficulty", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

        if (choice == 0){
            gamePanel.GameMode("Easy");
            gameFrame.modeSelected = true;
        }else if (choice == 1){
            gamePanel.GameMode("Normal");
            gameFrame.modeSelected = true;
        }else if (choice == 2){
            gamePanel.GameMode("Hard");
            gameFrame.modeSelected = true;
        }
    }

    public class MyKeyAdapter extends KeyAdapter{//KeyAdapter class for handling key events on the main page.
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
                System.exit(0); 
                break;
			}
		}
	}
}