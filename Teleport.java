package puzzle2;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Teleport implements Drawable{
    private Teleport otherDoor;
    private BufferedImage image;
    private Scene.Location location;
    private Scene scene;
    private int tileSize;
    private boolean isStandingOn = false;

    public Teleport(BufferedImage image,Scene.Location location,Scene scene){
        this.image = image;
        this.location = location;
        this.scene = scene;
        tileSize = scene.getTileSize();
    }

    public void setOtherPort(Teleport destination){
        otherDoor = destination;
        destination.otherDoor = this;
    }

    public void teleport(Character traveler){
        if(isStandingOn)
            return;
        isStandingOn = true;
        otherDoor.preventTeleport();
        traveler.moveTo(otherDoor.location.clone());
    }

    public void draw(Graphics g){
        g.drawImage(image,tileSize*location.x,tileSize*location.y,tileSize,tileSize,null);
    }

    public Scene.Location getGridPosition(){
        return location;
    }

    public void update(){
        Scene.Location playerLoc = scene.getPlayerLocation();
        if(!playerLoc.equals(location) && !playerLoc.equals(otherDoor.location))
            isStandingOn = false;
    }

    public Type getType(){
        return Type.TELEPORT;
    }

    private void preventTeleport(){
        isStandingOn = true;
    }
}
