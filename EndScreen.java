/*
 * EndScreen
 * @author 			Ted Lim
 * @date			January 20
 * @version
 * this class extends screen class to display the end screen
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class EndScreen extends Screen{
	private ImageIcon endScreen; // image for end screen
	private Rectangle confirm; // rectangle for confirm button
	
	public EndScreen() {
		try {
			endScreen = new ImageIcon("resource/GameOver.png"); // load menu image
		} catch (Exception e) {
			//System.out.println("Error loading menu screen");
			e.printStackTrace();
		}
		confirm = new Rectangle(490, 620, 330, 60);
	}
	
	/**
	 * draw
	 * @param			Graphics g object
	 * this object uses graphics g object passed in from paintcomponent to draw the end screen
	 */
	public void draw(Graphics g) {
		g.drawImage(endScreen.getImage(), 0, 0, null);
		g.setColor(Color.RED);
	}
	
	/**
	 * getConfirmRect
	 * @return			rectangle object for confirm button
	 * this method returns the rectangle object for the confirm button on the game over screen
	 */
	public Rectangle getConfirmRect() {
		return confirm;
	}
}
