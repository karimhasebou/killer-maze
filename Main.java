package puzzle2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

class Main{
	private final static Window window = new Window();
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				window.add(new Console(window),BorderLayout.EAST);
				window.setVisible(true);
			}
		});
	}

	public final static String RESOURCE_DIRECTORY = "puzzle2/Resources/";
}