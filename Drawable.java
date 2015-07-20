package puzzle2;

import java.awt.*;

interface Drawable{
	void draw(Graphics g); // for drawing
	Scene.Location getGridPosition();

	void update();
	Type getType();

	enum Type {ENEMY,PLAYER,PICKUP,COIN,RPG,GUN,FIRE};
}
