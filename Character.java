import java.awt.Rectangle;
import java.awt.image.BufferedImage;

abstract public class Character implements Comparable<Character>{
	private int health;
	private int attackDamage;
	private int exp;
	private int moveSpeed;

	protected int xPosition;
	protected int yPosition;

	private int hitBoxWidth;
	private int hitBoxLength;
	private int hitBoxX;
	private int hitBoxY;
	private Rectangle hitBox;

	private int hurtBoxWidth;
	private int hurtBoxLength;
	private int rightHurtBoxX;
	private int leftHurtBoxX;
	private int hurtBoxY;
	private Rectangle hurtBox;

	protected BufferedImage[] sprites;

	protected int spriteRowNum; 
	protected int spriteColumnNum;
	protected int spriteWidth;
	protected int spriteLength;

	private boolean facingForward; 
	private boolean moving; 
	private boolean attacking;

	private int timesMoved = 0;
	private int attackAnimationCounter;//added

	Character(){

	}

	abstract void basicAttack();

	abstract void loadSprites();

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		//System.out.println("original");
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPostition) {
		//System.out.println("original");
		this.yPosition = yPostition;
	}

	public int getHitBoxWidth() {
		return hitBoxWidth;
	}

	public void setHitBoxWidth(int hitBoxWidth) {
		this.hitBoxWidth = hitBoxWidth;
	}

	public int getHitBoxLength() {
		return hitBoxLength;
	}

	public void setHitBoxLength(int hitBoxLength) {
		this.hitBoxLength = hitBoxLength;
	}

	public BufferedImage[] getSprites() {
		return sprites;
	}

	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}

	public int getSpriteRowNum() {
		return spriteRowNum;
	}

	public void setSpriteRowNum(int spriteRowNum) {
		this.spriteRowNum = spriteRowNum;
	}

	public int getSpriteColumnNum() {
		return spriteColumnNum;
	}

	public void setSpriteColumnNum(int spriteColumnNum) {
		this.spriteColumnNum = spriteColumnNum;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}

	public int getSpriteLength() {
		return spriteLength;
	}

	public void setSpriteLength(int spriteLength) {
		this.spriteLength = spriteLength;
	}

	public boolean isFacingForward() {
		return facingForward;
	}

	public void setFacingForward(boolean facingForward) {
		this.facingForward = facingForward;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public int getTimesMoved() {
		return timesMoved;
	}

	public void setTimesMoved(int timesMoved) {
		this.timesMoved = timesMoved;
	}

	public int getHitBoxX() {
		return hitBoxX;
	}

	public void setHitBoxX(int hitBoxX) {
		this.hitBoxX = hitBoxX;
	}

	public int getHitBoxY() {
		return hitBoxY;
	}

	public void setHitBoxY(int hitBoxY) {
		this.hitBoxY = hitBoxY;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

	public int getHurtBoxWidth() {
		return hurtBoxWidth;
	}

	public void setHurtBoxWidth(int hurtBoxWidth) {
		this.hurtBoxWidth = hurtBoxWidth;
	}

	public int getHurtBoxLength() {
		return hurtBoxLength;
	}

	public void setHurtBoxLength(int hurtBoxLength) {
		this.hurtBoxLength = hurtBoxLength;
	}

	public int getRightHurtBoxX() {
		return rightHurtBoxX;
	}

	public void setRightHurtBoxX(int rightHurtBoxX) {
		this.rightHurtBoxX = rightHurtBoxX;
	}

	public int getLeftHurtBoxX() {
		return leftHurtBoxX;
	}

	public void setLeftHurtBoxX(int leftHurtBoxX) {
		this.leftHurtBoxX = leftHurtBoxX;
	}

	public int getHurtBoxY() {
		return hurtBoxY;
	}

	public void setHurtBoxY(int hurtBoxY) {
		this.hurtBoxY = hurtBoxY;
	}

	public Rectangle getHurtBox() {
		return hurtBox;
	}

	public void setHurtBox(Rectangle hurtBox) {
		this.hurtBox = hurtBox;
	}

	public int getAttackAnimationCounter() {
		return attackAnimationCounter;
	}

	public void setAttackAnimationCounter(int attackAnimationCounter) {
		this.attackAnimationCounter = attackAnimationCounter;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	
	public int compareTo(Character c) {
		int compareItem = c.getyPosition();
		
		return this.yPosition - compareItem;
	}


}
