import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Player extends Character {

	private int mana; //do this later full = 100
	private boolean ready;

	private PlayerHud hud;

	private double velocityY = 5;

	private boolean jumping;

	Player() {

		this.spriteColumnNum = 6;
		this.spriteRowNum = 2;
		this.spriteLength = 240;
		this.spriteWidth = 240;

	}

	abstract void loadSprites();

	public void drawHud(Graphics g, int multiplier){
		hud.drawHud(g, multiplier);
	}

	public void draw(Graphics g) { //pass something from loop


		int currentSprite = 0;

		if (!isFacingForward()){
			currentSprite = currentSprite + 3;
		}

		if (isAttacking()){
			currentSprite = currentSprite + 6;

			if(getAttackAnimationCounter()>2){
				setAttackAnimationCounter(0);
			}

			currentSprite = currentSprite + getAttackAnimationCounter();

			g.drawImage(sprites[currentSprite],getxPosition(),getyPosition(),null);

			long deltaTime = 0L;
			while(deltaTime <= 55000000){
				long start = System.nanoTime();
				long end = System.nanoTime();
				deltaTime += (end - start);
			}

			setAttackAnimationCounter(getAttackAnimationCounter()+1);

		}else if(isMoving()){
			if (getTimesMoved()<500){//switch between walking
				currentSprite = currentSprite + 1;
			}else{
				currentSprite = currentSprite + 2;
				if(getTimesMoved()>1000){
					setTimesMoved(-1);
				}
			}
			setTimesMoved(getTimesMoved() + 1);
			g.drawImage(sprites[currentSprite],getxPosition(),getyPosition(),null);

		}else if(!isMoving()){

			g.drawImage(sprites[currentSprite],getxPosition(),getyPosition(),null);

		}

	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}


	public void setxPosition(int x) {
		this.xPosition = x-120;
	}

	public void setyPosition(int y) {
		this.yPosition = y-180;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public PlayerHud getHud() {
		return hud;
	}

	public void setHud(PlayerHud hud) {
		this.hud = hud;
	}




}

