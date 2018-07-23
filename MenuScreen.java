/*
 * MenuScreen
 * @author		Ted Lim
 * @date		December 18
 * @version		
 * This class loads the image for the menu screen and draws the image
 */

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MenuScreen extends Screen{

	private ImageIcon menuScreen;

	private Rectangle playRect;
	private Rectangle optionsRect;
	private Rectangle instructionsRect;
	private Rectangle quitRect;

	private boolean playHovered;
	private boolean optionsHovered;
	private boolean instructionsHovered;
	private boolean quitHovered;

	private SelectionIcon sIcon;

	public MenuScreen() {
		try {
			menuScreen = new ImageIcon("resource/MainMenu.png"); // load menu image
		} catch (Exception e) {
			//System.out.println("Error loading menu screen");
			e.printStackTrace();
		}
		playRect = new Rectangle(425, 50);
		optionsRect = new Rectangle(425, 50);
		instructionsRect = new Rectangle(425, 50);
		quitRect = new Rectangle(425, 50);
		playHovered = false;
		optionsHovered = false;
		instructionsHovered = false;
		quitHovered = false;
		sIcon = new SelectionIcon();
	}

	/**
	 * draw
	 * @param g 			graphics object to draw graphics with
	 * this method draws the menu screen
	 */
	public void draw(Graphics g) {
		g.drawImage(menuScreen.getImage(), 0, 0, null);


		playRect.setLocation(426, 300);
		instructionsRect.setLocation(426, 400);
		//optionsRect.setLocation(426, 500);
		quitRect.setLocation(426, 500);


		if (playHovered) {
			sIcon.draw(g, ((int)playRect.getMinX()), (int)playRect.getMinY());
		} else if (instructionsHovered) {
			sIcon.draw(g, ((int)instructionsRect.getMinX()), (int)instructionsRect.getMinY());
		} else if (optionsHovered) {
			sIcon.draw(g, ((int)optionsRect.getMinX()), (int)optionsRect.getMinY());
		} else if (quitHovered) {
			sIcon.draw(g, ((int)quitRect.getMinX()), (int)quitRect.getMinY());
		}
	}	

	/**
	 * getPlayRect
	 * @return			the rectangle object for play button
	 * this method returns the rectangle object for play button
	 */
	public Rectangle getPlayRect() {
		return playRect;
	}
	
	/**
	 * getInstructionsRect
	 * @return			the rectangle object for instructions button
	 * this method returns the rectangle object for instructions button
	 */
	public Rectangle getInstructionsRect() {
		return instructionsRect;
	}
	
	/**
	 * getOptionsRect
	 * @return			the rectangle object for options button
	 * this method returns the rectangle object for options button
	 */
	public Rectangle getOptionsRect() {
		return optionsRect;
	}
	
	/**
	 * getQuitRect
	 * @return			the rectangle object for quit button
	 * this method returns the rectangle object for quit button
	 */
	public Rectangle getQuitRect() {
		return quitRect;
	}
	
	/**
	 * setPlayHovered
	 * this method sets the boolean variable of whether user is hovering on top of play button
	 */
	public void setPlayHovered(boolean b) {
		playHovered = b;
	}
	
	/**
	 * setInstructionsHovered
	 * this method sets the boolean variable of whether user is hovering on top of instructions button
	 */
	public void setInstructionsHovered(boolean b) {
		instructionsHovered = b;
	}
	
	/**
	 * setOptionsHovered
	 * this method sets the boolean variable of whether user is hovering on top of options button
	 */
	public void setOptionsHovered(boolean b) {
		optionsHovered = b;
	}
	
	/**
	 * setQuitHovered
	 * this method sets the boolean variable of whether user is hovering on top of quit button
	 */
	public void setQuitHovered(boolean b) {
		quitHovered = b;
	}

}
