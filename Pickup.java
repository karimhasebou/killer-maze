package puzzle2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

abstract public class Pickup implements Drawable{
    private Scene.Location loc;
    private Image pickup;
    private Scene scene;
    private int tileSize;

    public Pickup(Image pickup,Scene.Location loc,Scene scene){
        this.loc = loc;
        this.pickup = pickup;
        this.scene = scene;
        tileSize = scene.getTileSize();
    }

    public void draw(Graphics g){
        g.drawImage(pickup,tileSize*loc.x,tileSize*loc.y,tileSize,tileSize,null);
    }

    public Scene.Location getGridPosition(){
        return new Scene.Location(loc.x,loc.y);
    }

    public Type getType(){
        return Type.PICKUP;
    }

    public void update(){

    }

    protected Scene getScene(){
        return scene;
    }

    abstract public void pickedUp();
}
