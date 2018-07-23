import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// medium health
// low attack
// medium speed
// has healing (can spam)

public class Paladin extends Player{

	Paladin() {
		setHealth(100);
		setAttackDamage(20);
		setMana(100);
		
		loadSprites();

		setHealth(100); //change
		setMana(100); //change

		setHud(new PlayerHud(getHealth(),getMana(), "paladin")); //base hp/mp
	}

	public void loadSprites(){
		try {
			BufferedImage sheet = ImageIO.read(new File("resource/paladin.png"));//change the name

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
