interface WeaponHolder extends Drawable{

	Direction getFacingDirection();
	void notifyAmmoFinished(Weapon weapon);

	enum Direction{
		LEFT,RIGHT,UP,DOWN
	};
}
