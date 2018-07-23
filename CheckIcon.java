import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class CheckIcon {
	
	private Image checkImage;
	
	public CheckIcon () {
		checkImage = (new ImageIcon("resource/checkIcon.png")).getImage();
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(checkImage, x - (checkImage.getWidth(null)), y, null);
	}
	
}