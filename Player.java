package puzzle2;

import java.awt.*;
import java.awt.event.*;

class Player extends Character implements KeyListener{
	private final static int TRANSITION_SPEED = 5;

	public Player(Image sprite,Scene.Location gridPosition,Scene scene){
		super(sprite,gridPosition,scene,TRANSITION_SPEED);
	}

	public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_UP)
            moveUp();
        else if(keyCode == KeyEvent.VK_DOWN)
            moveDown();
        else if(keyCode == KeyEvent.VK_RIGHT)
            moveRight();
        else if(keyCode == KeyEvent.VK_LEFT)
            moveLeft();
		else if(keyCode == KeyEvent.VK_SPACE)
			fireWeapon();
    }


    public void keyReleased(KeyEvent e) {
    }

	@Override
	public void notifyAmmoFinished(){
		super.notifyAmmoFinished();
		Console.println("Ammo finished");
	}

	@Override
	public void fireWeapon(){
		super.fireWeapon();
		Weapon weapon = getWeapon();
		if(weapon != null)
			Console.println(weapon.getAmmoLeft()+" round (\'s\') left");
	}

}
