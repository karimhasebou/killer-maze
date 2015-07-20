package puzzle2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

class Coin implements Drawable{
	private Scene.Location loc;
	private Image coin;
	private Scene scene;
	private int tileSize;
	
	public Coin(Image coin,Scene.Location loc,Scene scene){
		this.loc = loc;
		this.coin = coin;
		this.scene = scene;
		tileSize = scene.getTileSize();
	}
	
	public void draw(Graphics g){
		g.drawImage(coin,tileSize*loc.x,tileSize*loc.y,tileSize,tileSize,null);
	}
	
	public Scene.Location getGridPosition(){
		return new Scene.Location(loc.x,loc.y);
	}

	public Type getType(){
		return Type.COIN;
	}
	public void update(){
		
	}
}
