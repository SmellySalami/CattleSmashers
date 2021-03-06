import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//low heath
//high stamina
//high damage
//can stealth/ avoid collision but can attack

public class Samurai extends Player{

	Samurai() {
		setHealth(100);
		setAttackDamage(20);
		setMana(100);
		
		loadSprites();

		setHealth(100); //change
		setMana(100); //change

		setHud(new PlayerHud(getHealth(),getMana(), "samurai")); //base hp/mp
	}

	public void loadSprites(){
		try {
			BufferedImage sheet = ImageIO.read(new File("resource/samurai.png"));

			this.sprites = new BufferedImage[spriteRowNum* spriteColumnNum];

			for (int j = 0; j < spriteRowNum; j++)
				for (int i = 0; i < spriteColumnNum; i++)
					this.sprites[(j * spriteColumnNum) + i] = sheet.getSubimage(i * spriteWidth,j * spriteLength, spriteWidth, spriteLength);
		} catch(Exception e) { System.out.println("error loading sheet");}; //set width and stuff
	}

	@Override
	void basicAttack() {
		// TODO Auto-generated method stub
		
	}

}
