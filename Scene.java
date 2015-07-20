package puzzle2;

import java.util.*;
import java.util.regex.Pattern;
import java.lang.*;
import java.net.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;
import javax.imageio.*;

public class Scene extends JPanel{
	private ArrayList<Drawable> drawables;
	private HashMap<String,BufferedImage> textures;

	private int tileSize;
	private int yTileCount,xTileCount;
	private int[][] grid;
	private int[] wallRange;
	private float resolutionMultiplier;
	private Player player;
	private volatile boolean gameOn = true;

	public Scene(String map,int sceneShortestLength){
		super();
		setFocusable(true);

		drawables = new ArrayList<Drawable>();
		textures = new HashMap<String,BufferedImage>();

		resolutionMultiplier = sceneShortestLength/500.0f;
		tileSize = (int) (25*resolutionMultiplier);

		loadMap(map);
		startGameLoop();
	}
	/** reads map file and loads textures used
	*/
	private void loadMap(String map){
		File file = new File(Main.RESOURCE_DIRECTORY+"Map/",map+".map");

		Scanner mapContent = null;
		try{
			mapContent = new Scanner(file);

			while(mapContent.hasNextLine()){
				String[] settings = mapContent.nextLine().split("\\s+");
				
				if(settings[0].equals(MAP_TEXTURES))
					loadMapTextures(settings);
				else if(settings[0].equals(MAP_OBJECTS))
					loadObjectTextures(settings);
				else if(settings[0].equals(GRID))
					loadGrid(mapContent,settings);
				else if(settings[0].equals(ENEMY) || settings[0].equals(PLAYER) || settings[0].equals(COIN))
					loadSprite(settings);
				else if(settings[0].equals(WALL_RANGE)){
					wallRange = new int[2];
					wallRange[0] = Integer.parseInt(settings[1]);
					wallRange[1] = Integer.parseInt(settings[2]);
				}
			}
		}catch(IOException e){
			System.out.println(e);
			System.exit(-1);
		}finally{
			if(mapContent != null)
				mapContent.close();
		}
	}

	/**  array of textures to load (index 0 skipped as it is used as an identifier)
	*/
	private void loadMapTextures(String[] textures) throws IOException,FileNotFoundException{
		for(int i = 1; i < textures.length;i++){
			BufferedImage image = ImageIO.read(new File(Main.RESOURCE_DIRECTORY+"/Textures/",textures[i]));
			if(image == null)
				throw new FileNotFoundException("Missing texture: "+textures[i]);
			this.textures.put(i+"",image);
		}
	}
	private void loadObjectTextures(String[] textures)throws IOException,FileNotFoundException{
		for(int i = 1; i < textures.length;i++){
			BufferedImage image = ImageIO.read(new File(Main.RESOURCE_DIRECTORY+"/Textures/",textures[i]));
			if(image == null)
				throw new FileNotFoundException("Missing texture: "+textures[i]);
			this.textures.put(textures[i].substring(0,textures[i].length()-4),image);
		}
	}

	/** reads array of integers representing map
	*/
	private void loadGrid(Scanner fileReader,String[] setting){
		xTileCount = Integer.parseInt(setting[1]);
		yTileCount = Integer.parseInt(setting[2]);

		grid = new int[yTileCount][xTileCount];

		for(int y = 0; y < yTileCount;y++)
			for(int x = 0; x < xTileCount;x++)
				grid[y][x] = fileReader.nextInt();
	}

	private void loadSprite(String[] sprite){
		BufferedImage image = textures.get(sprite[0]);

		Location loc = new Location(Integer.parseInt(sprite[1])
			,Integer.parseInt(sprite[2]));

		if(sprite[0].equals(ENEMY))
			drawables.add(new Enemy(image,loc,this));
		else if(sprite[0].equals(PLAYER)){
			player = new Player(image,loc,this);
			addKeyListener(player);
		}
		else if(sprite[0].equals(COIN))
			drawables.add(new Coin(image,loc,this));
	}

	public boolean canMove(Location loc){
		return loc.x >= 0 && loc.x < xTileCount &&
				loc.y >= 0 && loc.y < yTileCount &&  
				(grid[loc.y][loc.x] < wallRange[0] 
				|| grid[loc.y][loc.x] > wallRange[1]);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int y = 0; y < yTileCount;y++)
			for(int x = 0; x < xTileCount;x++)
				g.drawImage(textures.get(grid[y][x]+""),
					x*tileSize,y*tileSize,tileSize,tileSize,null);
		for(Drawable object : drawables)
			object.draw(g);
		player.draw(g);
	}

	public int getTileSize(){
		return tileSize;
	}

	public static class Location{
		public int x;
		public int y;
		public Location(int x,int y){
			this.x = x;
			this.y = y;
		}
		public boolean equals(Location other){
			return x == other.x && y == other.y;
		}
	}

	private void startGameLoop(){
		new Thread(new Runnable(){
			public void run(){
				try{
					while(gameOn){
						for(Drawable object : drawables)
							object.update();
						player.update();
						actOnObjectInCollision();
						repaint();
						Thread.sleep(32);
					}
				}catch(InterruptedException e){
					System.out.println(e);
					System.exit(-1);
				}
				
			}
		}).start();
	}
	/**@return returns null if no path is possible
	*/
	public ArrayList<Location> findPath(Location from,Location to){
		if(from.equals(to))
			return null;

		ArrayList<ArrayList<Location>> paths = 
			new ArrayList<ArrayList<Location>>();
		
		ArrayList<Location> path =
			new ArrayList<Location>();
			path.add(from);
		paths.add(path);

		boolean[][] visited = 
			new boolean[yTileCount][xTileCount];

		int[] dx = {0,0,-1,1};
		int[] dy = {1,-1,0,0};

		int tilesVisited = yTileCount*xTileCount;
		while(tilesVisited > 0){
			for(int pathsLeft = paths.size();pathsLeft > 0;pathsLeft--){
				int pathSize = paths.get(0).size();
				Location lastLoc = paths.get(0).get(pathSize-1);

				for(int i = 0; i < dx.length;i++){
					int x = lastLoc.x + dx[i];
					int y = lastLoc.y + dy[i];

					if(visited[y][x])
						continue;
					if(!canMove(new Location(x,y))){
						tilesVisited--;
						continue;
					}
					visited[y][x] = true;
					tilesVisited--;

					@SuppressWarnings("unchecked") // type of cloned object already known
					ArrayList<Location> newPath = 
						(ArrayList<Location>) paths.get(0).clone();
					Location newLocation = new Location(x,y);
					newPath.add(newLocation);
					paths.add(newPath);
					if(newLocation.equals(to)){
						return newPath;
					}
				}
				paths.remove(0);
			}
		}
		return null;
	}

	public Location getPlayerLocation(){
		return player.getGridPosition();
	}

	private void actOnObjectInCollision(){
		int i = isCollidinWithObject();
		
		if(i == -1)
			return;
		Drawable.Type objectType = drawables.get(i).getType();

		if(objectType == Drawable.Type.ENEMY)
			gameOn = false;
		if(objectType == Drawable.Type.COIN)
			drawables.remove(i);
	}
	/**@return returns index of object player is in collision with, returns -1 if no collision is present
	*/
	private int isCollidinWithObject(){
		Location playerLocation = player.getGridPosition();
		for(int i = drawables.size()-1; i >= 0;i--)
			if(drawables.get(i).getGridPosition().equals(playerLocation))
				return i;
		return -1;
	}


	public float getResolutionMultiplier(){
		return resolutionMultiplier;
	}

	final private static String MAP_TEXTURES = "MAP_TEXTURES";
	final private static String MAP_OBJECTS = "MAP_OBJECTS";
	final private static String GRID = "GRID";
	final private static String ENEMY = "ENEMY";
	final private static String PLAYER = "PLAYER";
	final private static String COIN = "COIN";
	final private static String RPG = "RPG";
	final private static String WALL_RANGE = "WALL_RANGE";
	final private static String BACKGROUND_MUSIC = "BACKGROUND_MUSIC";
}