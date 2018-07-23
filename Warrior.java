import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//medium health
//high damage
//medium speed
//special is?

public class Warrior extends Player{

	Warrior() {
		setHealth(100);
		setAttackDamage(20);
		setMana(100);

		loadSprites();

		setHealth(100);
		setMana(100); 

		setHud(new PlayerHud(getHealth(),getMana(), "warrior")); //base hp/mp

	}

	/**
	* loadSprites 
	* this method loads the sprite sheet into the game 
	* @return Boolean, true if the operation was a success, false otherwise.
	*/
	public void loadSprites(){
		try {
			BufferedImage sheet = ImageIO.read(new File("resource/warrior.png"));

			this.sprites = new BufferedImage[spriteRowNum* spriteColumnNum];

			for (int j = 0; j < spriteRowNum; j++)
				for (int i = 0; i < spriteColumnNum; i++)
					this.sprites[(j * spriteColumnNum) + i] = sheet.getSubimage(i * spriteWidth,j * spriteLength, spriteWidth, spriteLength);
		} catch(Exception e) { System.out.println("error loading sheet");}; //set width and stuff
	}


}
