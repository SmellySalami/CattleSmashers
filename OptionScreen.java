/*
 * OptionScreen
 * @author		Ted Lim
 * @date		December 18
 * @version		
 * This class loads the image for the option screen and draws the image
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class OptionScreen extends Screen{
	
	private ImageIcon optionScreen;
		
	public OptionScreen() {
		try {
		//optionScreen = new ImageIcon("resource/b.png"); // load image
		} catch (Exception e) {
			//System.out.println("Error loading options screen");
		}
	}
	
	/**
	 * draw
	 * @param		Grahpics g object
	 * this method draws the option screen
	 */
	public void draw(Graphics g) {
		//g.drawImage(optionScreen.getImage(), 0, 0, null);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 1280, 720);
		
	}
	
}	
