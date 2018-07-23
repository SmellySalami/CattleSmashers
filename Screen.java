/*
 * Screen
 * @author		Ted Lim
 * @date		Dec 19
 * @version
 * this class is the super class of different screens that need to be drawn
 */

import java.awt.Graphics;
import javax.swing.ImageIcon;

public abstract class Screen {
	
	protected ImageIcon screenImage;
	
	public void draw(Graphics g) {}
}
