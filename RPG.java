class RPG extends Weapon{
    private final static int DEFAULT_AMMO = 2;
    private final static String WEAPON_TEXTURE = "RPG_PICKUP";
    private final static String BULLET_TEXTURE = "ROCKET";
    private final static int PROJECTILE_SPEED = 10;

    public RPG(Scene scene,Scene.Location pickupLocation,boolean showAsPickup){
        super(Scene scene,Scene.Location pickupLocation,boolean showAsPickup);

        Image weaponTexture = scene.getTexture(WEAPON_TEXTURE);
        Image bulletTexture = scene.getTexture(BULLET_TEXTURE);

        setWeaponProperties(weaponTexture,DEFAULT_AMMO);
        setBulletProperties(bulletTexture,PROJECTILE_SPEED);
    }

    public Type getType(){
        return Weapon.Type.RPG;
    }
}
