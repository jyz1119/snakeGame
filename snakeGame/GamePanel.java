import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public abstract class GamePanel extends JPanel implements ActionListener{
	// Constants and variables for game parameters
	static final int SCREEN_WIDTH = 1500;//or 1900
	static final int SCREEN_HEIGHT = 850;//or 1050
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 175;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	private int applesEaten;
	private int appleX;
	private int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	int additionalSpeed;
	public boolean easyMode = false;
	public boolean hardMode = false;

	GamePanel(){//Constructor initializes common settings and starts the game.
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());

		startGame();
	}

	void apple(int applesEaten,int appleX,int appleY){
		this.applesEaten = applesEaten;
		this.appleX = appleX;
		this.appleY = appleY;
	}

	public int getApplesEaten(){
		return applesEaten;
	}

	public int getAppleX(){
		return appleX;
	}

	public int getAppleY(){
		return appleY;
	}

	public void setApplesEaten(int applesEaten){
		this.applesEaten = applesEaten;
	}

	public void setAppleX(int appleX){
		this.appleX = appleX;
	}
	
	public void setAppleY(int appleY){
		this.appleY = appleY;
	}

	public void paintComponent(Graphics g) {//Overrides paintComponent to draw the game elements on the panel.
		super.paintComponent(g);
		draw(g);
	}

	public void startGame() {//Starts the game by setting up initial conditions and starting the timer.
        newApple();
		running = true;
	
		// 停止现有的计时器
		if (timer != null) {
			timer.stop();
		}
	
		try {
			timer = new Timer(DELAY, this);
			timer.start();
		} catch (Exception e) {
			System.err.println("Error starting the game timer: " + e.getMessage());
		}
	
		for (int i = 0; i < bodyParts; i++) {
			x[i] = 0;
			y[i] = 0;
		}
		applesEaten = 0;
		bodyParts = 6;
		direction = 'R';
    }

    abstract void draw(Graphics g);//implemented for drawing specific game elements.

    abstract void move();//implemented for moving the snake.

    abstract void checkCollisions();//implemented for checking collisions (e.g., with walls or itself).

    abstract void gameOver(Graphics g);//implemented for handling game over scenario.

	public void newApple(){//generate a new apple position for the Snake Game.
			try {
				appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
				appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
			} catch (Exception e) {
				System.err.println("Error generating a new apple: " + e.getMessage());
			}
	}

	public void checkApple() {//check if the snake ate an apple in the Snake Game.
		if((x[0] == getAppleX()) && (y[0] == getAppleY())) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {// ActionListener for game updates
			move();
			checkApple();
			checkCollisions();

			if (hardMode) {// If in hard mode, increase speed with score
				increaseSpeedWithScore();
			}
		}
		repaint();// Refresh the display
	}
	
	public class MyKeyAdapter extends KeyAdapter{//KeyAdapter class for handling key events during the game.
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;

			case KeyEvent.VK_A:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if(direction != 'U') {
					direction = 'D';
				}
				break;	



            case KeyEvent.VK_SPACE:
                if (!running) {
                    startGame();
                }
                break;
			case KeyEvent.VK_ESCAPE:
				timer.stop();
				showPauseDialog(); // Exit the game when ESC key is pressed
                break;
			}
		}
	private void showPauseDialog() {//Displays a dialog when the game is paused.
		String[] options = {"Continue", "Back to Menu", "Exit"};
		int choice = JOptionPane.showOptionDialog(SwingUtilities.getWindowAncestor(GamePanel.this), "Game Paused", "Pause", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

		if (choice == 0){
            resumeGame();// Continue the game
        }else if (choice == 1){
            showMainPage();// Return to the main menu
        }else if (choice == 2){
            System.exit(0);// Exit the game
        }
	}

	private void resumeGame() {//Resumes the game by restarting the timer.
			timer.start(); 
	}

	private void showMainPage() {//Returns to the main menu.
			GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
			gameFrame.showMainPage();
			gameFrame.modeSelected = false;
		}
	}

	public void GameMode(String difficulty) {//Sets the game mode based on the selected difficulty.
		
		if (difficulty.equals("Easy")) {
			easyMode = true; 
			hardMode = false;
		}
		else if (difficulty.equals("Normal")){
			easyMode = false;
			hardMode = false;
		}
		else if (difficulty.equals("Hard")){
			easyMode = false;
			hardMode = true;
		}
	}

	private void increaseSpeedWithScore() {//Increases the game speed as the score (apples eaten) advances.

		if(bodyParts < 36){
			additionalSpeed = applesEaten * 3;
		}
			
		timer.setDelay(DELAY - additionalSpeed);
	}
}