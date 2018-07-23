import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//very high health
//low speed
//medium health
//stamina doesn't run out since bearly move

public class Monk extends Player{

	Monk() {
		setHealth(100);
		setAttackDamage(20);
		setMana(100);
		
		loadSprites();

		setHealth(100); //change
		setMana(100); //change

		setHud(new PlayerHud(getHealth(),getMana(), "monk")); //base hp/mp
	}

	public void loadSprites(){
		try {
			BufferedImage sheet = ImageIO.read(new File("resource/monk.png"));

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
