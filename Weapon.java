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
		this.projectileSpeed = projectileSpeed;
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

	abstract public Type getType();

	public void fire(){
		if(ammo <= 0)
			return;
		scene.addWeaponFire(new Ammunition(weaponHolder.getGridPosition(),weaponHolder.getFacingDirection(),bulletTexture));
		ammo--;
	}

	public class Ammunition implements Drawable{
	    public Scene.Location loc;
	    public Direction dir;
	    public BufferedImage bulletTexture;
	    public Ammunition(Scene.Location loc,Direction dir,BufferedImage bulletTexture){
	        this.loc = loc;
	        this.dir = dir;
	        this.bulletTexture = bulletTexture;

	    }
	    /** default moves bullet in a straight path
	    */
	    public void update(){
	        switch(dir){
	            case LEFT:
	                loc.x--;
	                break;
	            case RIGHT:
	                loc.x++;
	                break;
	            case UP:
	                loc.y--;
	                break;
	            case DOWN:
	                loc.y++;
	                break;
	        }
	    }

	    public void draw(Graphics g){
	        g.drawImage(bulletTexture,loc.x*tileSize,loc.y*tileSize,tileSize,tileSize,null);
	    }

	    public Scene.Location getGridPosition(){
	        return new Scene.Location(loc.x,loc.y);
	    }

	    public Type getType(){
	        return Type.STRAIGHT_BULLET;
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
