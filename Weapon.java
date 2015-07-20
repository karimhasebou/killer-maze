package puzzle2;

import java.util.*;
import java.lang.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;

abstract class Weapon implements Drawable{
	private WeaponHolder weaponHolder;
	private ArrayList<Ammunition> ammunition;
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
		this.ammunition = new ArrayList<Ammunition>(ammo);
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
		}else{
			for(Ammunition bullet : ammunition)
				bullet.draw(g);
		}
	}

	public void update(){
		for(Ammunition bullet : ammunition)
			bullet.update();
	}

	abstract public Type getType();

	public void fire(){
		if(ammo <= 0)
			return;
		ammunition.add(new Ammunition(weaponHolder.getGridPosition(),weaponHolder.getFacingDirection(),bulletTexture));
		ammo--;
	}

	public class Ammunition{
		public Scene.Location loc;
		public Direction dir;
		public BufferedImage bulletTexture;

		public Ammunition(Scene.Location loc,Direction dir,BufferedImage bulletTexture){
			this.loc = loc;
			this.dir = dir;
			this.bulletTexture = bulletTexture;
		}

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
	}
	public Scene.Location getGridPosition(){
		return pickupLocation;
	}
}
