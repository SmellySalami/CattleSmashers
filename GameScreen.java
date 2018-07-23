/*
 * GameScreen
 * @author		Ted Lim
 * @date		January 17
 * @version		
 * This class loads the image for the game screen and draws the image
 */

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class GameScreen extends Screen{
	private ImageIcon gameScreen; // ImageIcon for game screen

	public GameScreen() {
		try {
			gameScreen = new ImageIcon("resource/gameBG1.png"); // load image for game background
		} catch (Exception e) {
			//System.out.println("Error loading game screen");
			e.printStackTrace();
		}
	}

	/**
	 * draw
	 * @param g 			graphics object to draw graphics with
	 * this method draws the game screen
	 */
	public void draw(Graphics g) {
		g.drawImage(gameScreen.getImage(), 0, 0, null);
	}
}
