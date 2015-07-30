package puzzle2;

import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MapCreator extends JPanel{
    private int[][] grid;
    private final static int minSize = 32;

    private ArrayList<BufferedImage> walls;
    private ArrayList<BufferedImage> land;

    public MapCreator(int xTiles,int yTiles){
        super();
        setBackground(Color.BLACK);
        grid =  new int[yTiles][xTiles];
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        int height = getHeight(),width = getWidth();

        for(int i = 0; i < width;i += width/grid[0].length)
            g.drawLine(i,0,i,height);
        for(int i = 0; i < height;i += height/grid.length){
            g.drawLine(0,i,width,i);
            System.out.println(height);
        }
    }
}
