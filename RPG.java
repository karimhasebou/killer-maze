package puzzle2;

import java.util.*;
import java.lang.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;

class RPG extends Weapon{
    private final static int DEFAULT_AMMO = 2;
    private final static String WEAPON_TEXTURE = "RPG_PICKUP";
    private final static String BULLET_TEXTURE = "ROCKET";
    private final static int PROJECTILE_SPEED = 10;

    public RPG(Scene scene,Scene.Location pickupLocation,boolean showAsPickup){
        super(scene,pickupLocation,showAsPickup);

        BufferedImage weaponTexture = scene.getTexture(WEAPON_TEXTURE);
        BufferedImage bulletTexture = scene.getTexture(BULLET_TEXTURE);

        setWeaponProperties(weaponTexture,DEFAULT_AMMO);
        setBulletProperties(bulletTexture,PROJECTILE_SPEED);
    }

    public Type getType(){
        return Type.RPG;
    }
}
