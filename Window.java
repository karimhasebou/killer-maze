package puzzle2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Window extends JFrame{
	private JPanel mainScreen;

	public Window(){
		super(GAME_TITLE);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);

		loadConfig();
	}
	// load frame settings
	private void loadConfig(){
		File settingsFile = new File(Main.RESOURCE_DIRECTORY,"CONFIG.txt");
		
		System.out.println(settingsFile.getAbsolutePath());

		Scanner config = null;
		try{
			config = new Scanner(settingsFile);

			while(config.hasNext()){
				String[]setting = config.nextLine().split("\\s+");

				if(setting[0].equals(SCREEN_RESOLUTION)){
					setSize(Integer.parseInt(setting[1])
						,Integer.parseInt(setting[2]));
				}

			}
		}catch(IOException e){
			System.out.println(e);
		}catch(IndexOutOfBoundsException e){
			System.out.println(e+"config file tampered with");
		}finally{
			if(config != null)
				config.close();
		}
	}


	public void putMainScreen(JPanel panel){
		if(mainScreen != null)
			remove(mainScreen);
		mainScreen = panel;
		add(mainScreen,BorderLayout.CENTER);
	}

	public void removeMainScreen(){
		if(mainScreen != null)
			remove(mainScreen);
	}
	
	private final static String SCREEN_RESOLUTION = "SCREEN_RESOLUTION";
	private final static String GAME_TITLE = "Killer Maze";
}