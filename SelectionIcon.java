/*
 * SelectionIcon
 * @author		Ted Lim
 * @date		December 18
 * @version		
 * This class loads the image for the selection icon and draws the image
 */

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class SelectionIcon {
	
	private Image selectionImage;
	
	public SelectionIcon () {
		try {
			selectionImage = (new ImageIcon("resource/selectionIcon.png")).getImage();
		} catch (Exception e) {
			System.out.println("Error loading selection icon");
			e.printStackTrace();
		}
	}
	
	/**
	 * draw
	 * @param g		graphics g object
	 * @param x		the x coord
	 * @param y		y coord
	 * this method draws the icon at the button the user is hovered over with mouse
	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(selectionImage, x - (selectionImage.getWidth(null)), y, null); // draw selection icon
	}
	
}
