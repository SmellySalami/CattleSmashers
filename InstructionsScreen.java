/*
 * InstructionsScreen
 * @author		Ted Lim
 * @date		December 18
 * @version		
 * This class loads the image for the instructions screen and draws the image
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class InstructionsScreen extends Screen{
	
	private ImageIcon instrScreen;
	private Rectangle backBox;
		
	public InstructionsScreen() {
		try {
	instrScreen = new ImageIcon("resource/Instructions.png");
		} catch (Exception e) {
			//System.out.println("Error loading instructions screen");
			e.printStackTrace();
		}
		
		backBox = new Rectangle(100, 40);
		backBox.setLocation(1160, 660);
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(instrScreen.getImage(), 0, 0, null);
		//g.setColor(Color.RED);
		//g.fillRect(0, 0, 1280, 720);
		
	}

	public Rectangle getBackBox() {
		return backBox;
	}

	public void setBackBox(Rectangle backBox) {
		this.backBox = backBox;
	}
}