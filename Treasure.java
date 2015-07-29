package puzzle2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

public class Treasure extends Pickup{

    public Treasure(Image Treasure,Scene.Location loc,Scene scene){
		super(Treasure,loc,scene);
	}

	public void pickedUp(){
        getScene().gameOver(true);
	}
}
