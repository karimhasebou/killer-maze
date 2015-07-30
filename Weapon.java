package puzzle2;

import java.util.*;
import java.lang.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;

abstract class Weapon implements Drawable{
	private WeaponHolder weaponHolder;
	private BufferedImage weaponIcon;
	private BufferedImage bulletTexture;
	private Scene.Location pickupLocation;
	private Scene scene;
	private boolean showAsPickup;
	private int projectileSpeed;
	private int ammo;
	private int tileSize;

	public Weapon(Scene scene,Scene.Location pickupLocation,boolean showAsPickup){
		this.scene = scene;
		this.pickupLocation = pickupLocation;
		this.showAsPickup = showAsPickup;
		this.tileSize = scene.getTileSize();
	}

	public void setBulletProperties(BufferedImage bulletTexture,int bulletSpeed){
		this.bulletTexture = bulletTexture;
		this.projectileSpeed = bulletSpeed;
	}

	public void setWeaponProperties(BufferedImage weaponIcon,int ammo){
		this.weaponIcon = weaponIcon;
		this.ammo = ammo;
	}

	public void setWeaponHolder(WeaponHolder weaponHolder){
		this.weaponHolder = weaponHolder;
	}

	public void addAmmo(int extra){
		ammo += extra;
	}

	public int getAmmoLeft(){
		return ammo;
	}

	public void showWeaponOnGrid(boolean show){
		showAsPickup = show;
	}

	public void draw(Graphics g){
		if(showAsPickup){
			g.drawImage(weaponIcon,tileSize*pickupLocation.x,tileSize*pickupLocation.y,tileSize,tileSize,null);
		}
	}

	public void update(){
	}

	public Type getType(){
		return Drawable.Type.WEAPON;
	}

	public void fire(){
		if(ammo <= 0)
			return;
		scene.addWeaponFire(new Ammunition(weaponHolder.getGridPosition(),weaponHolder.getFacingDirection(),bulletTexture));
		if(--ammo == 0)
			weaponHolder.notifyAmmoFinished();
	}

	public class Ammunition implements Drawable{
	    public Scene.Location pixelPosition;
	    public Direction dir;
	    public BufferedImage bulletTexture;

	    public Ammunition(Scene.Location gridPosition,Direction dir,BufferedImage bulletTexture){
	        this.pixelPosition = new Scene.Location(gridPosition.x*tileSize,
				gridPosition.y*tileSize);
	        this.dir = dir;
	        this.bulletTexture = bulletTexture;

			if(dir == Direction.LEFT)
				pixelPosition.x -= tileSize;
			else if(dir ==  Direction.RIGHT)
				pixelPosition.x += tileSize;
			else if(dir ==  Direction.UP)
				pixelPosition.y -= tileSize;
			else
				pixelPosition.y += tileSize;
	    }
	    /** default moves bullet in a straight path
	    */
	    public void update(){
	        switch(dir){
	            case LEFT:
	                pixelPosition.x -= projectileSpeed;
	                break;
	            case RIGHT:
	                pixelPosition.x += projectileSpeed;
	                break;
	            case UP:
	                pixelPosition.y -= projectileSpeed;
	                break;
	            case DOWN:
	                pixelPosition.y += projectileSpeed;
	                break;
	        }
	    }

	    public void draw(Graphics g){
	        g.drawImage(bulletTexture,pixelPosition.x,pixelPosition.y,tileSize,tileSize,null);
	    }

	    public Scene.Location getGridPosition(){
	        return new Scene.Location(pixelPosition.x/tileSize,pixelPosition.y/tileSize);
	    }

	    public Type getType(){
			return Drawable.Type.BULLET;
		}
	}


	// returns -1 and -1 to indicate absence from map
	public Scene.Location getGridPosition(){
		if(showAsPickup)
			return pickupLocation;
		else
			return new Scene.Location(-1,-1);
	}
}
