package puzzle2;

import puzzle2.Direction;

interface WeaponHolder extends Drawable{

	Direction getFacingDirection();
	void notifyAmmoFinished();
}
