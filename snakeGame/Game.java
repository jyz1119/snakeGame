import java.awt.*;


public class Game extends GamePanel {

	

    @Override
    void draw(Graphics g) {//draw the specific elements for the Snake Game.
        if(running) {
			
			/*for (int i = 0; i <= SCREEN_WIDTH / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
			}
			
			for (int i = 0; i <= SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			}*/
			
			
			g.setColor(Color.red);
			g.fillOval(getAppleX(), getAppleY(), UNIT_SIZE, UNIT_SIZE); 
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+getApplesEaten(), (SCREEN_WIDTH - metrics.stringWidth("Score: "+getApplesEaten()))/2, g.getFont().getSize());

			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			int currentDelay = timer.getDelay();
			g.drawString("Speed: " + currentDelay, 20, 30);
		}
		else {
			gameOver(g);
		}
    }

    @Override
	void move(){//implement specific snake movement for the Snake Game.
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}

    @Override
	void checkCollisions() {//check for specific collisions in the Snake Game.
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
				}
			}

		if (easyMode == true) {
            allowSnakeThroughWalls();
        } else{
			if(x[0] < 0) {
				running = false;
			}
			if(x[0] > SCREEN_WIDTH) {
				running = false;
			}
			if(y[0] < 0) {
				running = false;
			}
			if(y[0] > SCREEN_HEIGHT) {
				running = false;
			}
			
			if(!running) {
				timer.stop();
			}
		}
	}

    @Override
	void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+getApplesEaten(), (SCREEN_WIDTH - metrics1.stringWidth("Score: "+getApplesEaten()))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        // Play Again text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press SPACE to Play Again", (SCREEN_WIDTH - metrics3.stringWidth("Press SPACE to Play Again")) / 2, SCREEN_HEIGHT / 2 + 100);
	}

    private void allowSnakeThroughWalls() {//move through walls based on the easy game mode.
		if (x[0] < 0) {
			x[0] = SCREEN_WIDTH - UNIT_SIZE;
		}
		if (x[0] > SCREEN_WIDTH) {
			x[0] = 0;
		}
		if (y[0] < 0) {
			y[0] = SCREEN_HEIGHT - UNIT_SIZE;
		}
		if (y[0] > SCREEN_HEIGHT) {
			y[0] = 0;
		}
	}
}