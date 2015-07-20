package puzzle2;

import java.awt.*;
import java.awt.event.*;

class Player extends Character implements KeyListener{
	private final static int TRANSITION_SPEED = 5;

	public Player(Image sprite,Scene.Location gridPosition,Scene scene){
		super(sprite,gridPosition,scene,TRANSITION_SPEED);
	}
	
	public Type getType(){
		return Type.PLAYER;
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
    }

    
    public void keyReleased(KeyEvent e) {
    }

}