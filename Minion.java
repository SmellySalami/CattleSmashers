import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Minion extends Enemy{

	boolean aggro; //always aggro?
	//upload this
	Minion() {

		this.spriteLength = 240;
		this.spriteWidth = 240;
		loadSprites();
	}

	void loadSprites() {
		try {//960x480
			BufferedImage sheet = ImageIO.read(new File("resource/Cow.png"));//change the name

			this.sprites = new BufferedImage[spriteRowNum* spriteColumnNum];

			for (int j = 0; j < spriteRowNum; j++)
				for (int i = 0; i < spriteColumnNum; i++)
					this.sprites[(j * spriteColumnNum) + i] = sheet.getSubimage(i * spriteWidth,j * spriteLength, spriteWidth, spriteLength);
		} catch(Exception e) { System.out.println("error loading sheet");}; //set width and stuff

	}


	public void setxPosition(int x) {
		//System.out.println("This is the overriden one");
		this.xPosition = x-110;
	}


	public void setyPosition(int y) {
		//System.out.println("This is the overriden one");
		this.yPosition = y-180;
	}

	@Override
	void basicAttack() {
		// TODO Auto-generated method stub
		
	}

	
	
}
