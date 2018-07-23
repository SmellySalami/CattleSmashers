import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PlayerHud {
	private double healthTotal;
	private double healthCurrent;
	private double manaTotal;
	private double manaCurrent;
	private BufferedImage icon;
	private BufferedImage healthBar;
	private BufferedImage manaBar;
	private BufferedImage base;
	private String type;

	PlayerHud(int hp, int mp, String type){
		this.type = type;
		loadImages();
		
		healthTotal = hp;
		manaTotal = mp;
	}

	public void drawHud (Graphics g, int multiplier) { //added
		
		
		g.drawImage(base, 10 + (multiplier*320), 10, null);
		g.drawImage(icon, 15 + (multiplier*320), 40, null );
		
		double percentageHealth = healthCurrent/healthTotal;
		double percentageMana = manaCurrent/manaTotal;	
		
		g.drawImage(healthBar, 62 + (multiplier*320), 42, (int)(193*percentageHealth), 14, null); // size of image (193 x 14)
		g.drawImage(manaBar, 62 + (multiplier*320), 57, (int)(193*percentageMana), 14, null);

		
	}

	public void loadImages(){	
		try {
			if(getType().equals("monk")){
				icon = ImageIO.read(new File("resource/monkicon.png"));
			}else if(getType().equals("warrior")) {
				icon = ImageIO.read(new File("resource/warrioricon.png"));
			}else if(getType().equals("paladin")) {
				icon = ImageIO.read(new File("resource/paladinicon.png"));
			}else if(getType().equals("samurai")) {
				icon = ImageIO.read(new File("resource/samuraiicon.png"));
			}

			healthBar = ImageIO.read(new File("resource/hp.png"));
			manaBar = ImageIO.read(new File("resource/mana.png"));
			base = ImageIO.read(new File("resource/base.png"));
			
		} catch(Exception e) { System.out.println("error loading image");};
	}

	public double getHealthCurrent() {
		return healthCurrent;
	}

	public void setHealthCurrent(double healthCurrent) {
		this.healthCurrent = healthCurrent;
	}

	public double getManaCurrent() {
		return manaCurrent;
	}

	public void setManaCurrent(double manaCurrent) {
		this.manaCurrent = manaCurrent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
}
