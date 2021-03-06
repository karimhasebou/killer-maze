package puzzle2;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public abstract class Character implements WeaponHolder{
	private Image[] sprite;
	private Scene.Location gridPosition; // grid based
	private Scene.Location pixelPosition; // pixel based
	private Scene scene;
	private Weapon weapon;

	private int tileSize;
	private int spriteSelector;
	private int transitionSpeed;
	private boolean isMoving = false;

	public Character(Image sprite,Scene.Location gridPosition,Scene scene,int transitionSpeed){
		int spritesPerRow = ((BufferedImage)sprite).getWidth()/SPRITE_SIZE;
		int spritesPerCol = ((BufferedImage)sprite).getHeight()/SPRITE_SIZE;
		this.sprite = new BufferedImage[spritesPerRow*spritesPerCol];

		for(int i = 0,count = 0; i < spritesPerCol;i++)
			for(int j = 0; j < spritesPerRow;j++)
				this.sprite[count++] = ((BufferedImage)sprite).getSubimage(j*SPRITE_SIZE, i*SPRITE_SIZE
						, SPRITE_SIZE, SPRITE_SIZE);

		this.gridPosition = gridPosition;
		this.scene = scene;

		float resolutionMultiplier = scene.getResolutionMultiplier();
		tileSize =  scene.getTileSize();
		this.transitionSpeed = (int) (transitionSpeed*resolutionMultiplier);

		pixelPosition = new Scene.Location(gridPosition.x*tileSize,gridPosition.y*tileSize);
		spriteSelector = 0;
	}

	public Scene.Location getGridPosition(){
		return new Scene.Location(gridPosition.x,gridPosition.y);
	}

	public Scene.Location getpixelPosition(){
		return new Scene.Location(pixelPosition.x,pixelPosition.y);
	}

	protected Image[] getSprite(){
		return sprite;
	}

	public void setGridPosition(Scene.Location loc){
		gridPosition = loc;
	}

	protected void setPixelPosition(Scene.Location loc){
		pixelPosition = loc;
	}

	protected int getSpriteSelector(){
		return spriteSelector;
	}

	protected void setSpriteSelector(int spriteSelector){
		this.spriteSelector = spriteSelector;
	}

	public void draw(Graphics g){
		g.drawImage(sprite[spriteSelector],pixelPosition.x,pixelPosition.y,tileSize,tileSize,null);
	}

	public void moveTo(Scene.Location loc){
		setGridPosition(loc);
		setPixelPosition(new Scene.Location(loc.x*tileSize,loc.y*tileSize));
	}

	public Type getType(){
		return Type.CHARACTER;
	}

	public void update(){
		isMoving = true;
		if(gridPosition.x*tileSize > pixelPosition.x){ // move right
			pixelPosition.x += transitionSpeed;

			if(gridPosition.x*tileSize < pixelPosition.x)
				pixelPosition.x = gridPosition.x*tileSize;

			spriteSelector++;

			if(spriteSelector < 6  || spriteSelector > 8)
				spriteSelector = 6;

		}else if(gridPosition.x*tileSize  < pixelPosition.x){ // move left
			pixelPosition.x -= transitionSpeed;

			if(gridPosition.x*tileSize > pixelPosition.x)
				pixelPosition.x = gridPosition.x*tileSize;

			spriteSelector++;

			if(spriteSelector < 3 || spriteSelector > 5)
				spriteSelector = 3;

		}else if(gridPosition.y*tileSize  < pixelPosition.y){ // move up
			pixelPosition.y -= transitionSpeed;

			if(gridPosition.y*tileSize  > pixelPosition.y)
				pixelPosition.y = gridPosition.y*tileSize;

			spriteSelector++;

			if(spriteSelector < 9 || spriteSelector > 11)
				spriteSelector = 9;

		}else if(gridPosition.y*tileSize  > pixelPosition.y){ // move down
			pixelPosition.y += transitionSpeed;

			if(gridPosition.y*tileSize  < pixelPosition.y)
				pixelPosition.y = gridPosition.y*tileSize;

			spriteSelector++;

			if(spriteSelector < 0|| spriteSelector > 2)
				spriteSelector = 0;
		}else{
			isMoving = false;
		}
	}

	protected void moveDown(){
		if(!scene.canMove(new Scene.Location(gridPosition.x,gridPosition.y+1)) || isMoving)
			return;
		gridPosition.y++;
	}
	protected void moveUp(){
		if(!scene.canMove(new Scene.Location(gridPosition.x,gridPosition.y-1)) || isMoving)
			return;
		gridPosition.y--;
	}
	protected void moveLeft(){
		if(!scene.canMove(new Scene.Location(gridPosition.x-1,gridPosition.y)) || isMoving)
			return;
		gridPosition.x--;
	}
	protected void moveRight(){
		if(!scene.canMove(new Scene.Location(gridPosition.x+1,gridPosition.y)) || isMoving)
			return;
		gridPosition.x++;
	}

	public boolean isMoving(){
		return isMoving;
	}

	public Scene getScene(){
		return scene;
	}

	public Direction getFacingDirection(){
		switch(spriteSelector/3){
			case 0:
				return Direction.DOWN;
			case 1:
				return Direction.LEFT;
			case 2:
				return Direction.RIGHT;
			default:
				return Direction.UP;
		}
	}
	public void setWeapon(Weapon weapon){
		if(this.weapon != null && weapon.getType() == this.weapon.getType()){
				this.weapon.addAmmo(weapon.getAmmoLeft());
				Console.println("Ammo increased to "+this.weapon.getAmmoLeft());
		}else
			this.weapon = weapon;
	}

	protected void fireWeapon(){
		if(weapon != null)
			weapon.fire();
	}

	public void notifyAmmoFinished(){
		weapon = null;
	}

	protected Weapon getWeapon(){
		return weapon;
	}

	private final static int SPRITE_SIZE = 32;
}
