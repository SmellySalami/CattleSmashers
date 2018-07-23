import java.awt.Graphics;

public abstract class Enemy extends Character {

	Enemy() {
		this.spriteColumnNum = 4;
		this.spriteRowNum = 2;
	}

	abstract void loadSprites();
	
	public void seek (){

	}
	
	public void draw(Graphics g) { //pass something from loop

		int currentSprite = 0;

		if (!isFacingForward()){
			currentSprite = currentSprite + 2;
		}


		if (isAttacking()){
			currentSprite = currentSprite + 4;

			if(getAttackAnimationCounter()>1){
				setAttackAnimationCounter(0);
			}

			//System.out.println(getAttackAnimationCounter());

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
			//System.out.println("moving");
			if (getTimesMoved()<500){//switch between walking
				//something here
			}else{
				currentSprite = currentSprite + 1;
				if(getTimesMoved()>1000){
					setTimesMoved(-1);
				}
			}
			setTimesMoved(getTimesMoved() + 1);
			g.drawImage(sprites[currentSprite],getxPosition(),getyPosition(),null);

		}else if(!isMoving()){
			//g.drawImage(sprites[currentSprite],getxPosition(),getyPosition(),null);
			g.drawImage(sprites[0],getxPosition(),getyPosition(),null);
		}

	}
	
}
