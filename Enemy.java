package puzzle2;

import java.awt.*;
import java.lang.*;
import java.util.*;

class Enemy extends Character{
	private ArrayList<Scene.Location> pathToGoal;
	private final static int SEARCH_RADIUS = 5;
	private final static int TRANSITION_SPEED = 2;

	public Enemy(Image sprite,Scene.Location gridPosition,Scene scene){
		super(sprite,gridPosition,scene,TRANSITION_SPEED);
	}

	public void update(){
		super.update();

		if(isMoving()){
			return;
		}

		Scene.Location playerPos = super.getScene().getPlayerLocation();
		Scene.Location enemyPosition = super.getGridPosition();

		if((Math.abs(playerPos.x-enemyPosition.x) <= SEARCH_RADIUS &&
		  Math.abs(playerPos.y-enemyPosition.y) <= SEARCH_RADIUS) && (pathToGoal == null || pathToGoal.size() == 0)){
  				pathToGoal = super.getScene().findPath(super.getGridPosition(),playerPos);
		}

		if(pathToGoal == null){

		}else if(pathToGoal.size() != 0){
			Scene.Location nxtStep = pathToGoal.get(0);
			super.setGridPosition(nxtStep);
			pathToGoal.remove(0);
		}
	}
}
