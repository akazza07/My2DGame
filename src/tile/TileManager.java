package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		
		loadMap("/maps/world01.txt");
	}
	public void getTileImage() {  // in this we have to add tiles
		
			setup(0,"grass",false);
			setup(1,"wall3",true);
			setup(2,"water",true);
			setup(3,"mat1",false);
			setup(4,"tree",true);
			setup(5,"sand1",false);
			setup(6,"wall2",true);
			setup(7,"banner",false);
			setup(8,"flower",false);
			setup(9,"wall1",true);
		
		}
	public void setup(int index , String imageName , boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
					
		}catch(IOException e) {
			e.printStackTrace();
			
			
		}
		
		
	}
	
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			// we used this inputstream to import this tile file
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			// we're gonna use this bufferedReader to read the content of the text file 
			// its just a format to read this text file
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine(); // read a line of text.
				
				while(col < gp.maxWorldCol) {
					
					String number[] = line.split(" "); // split the string at a space
					
					int num = Integer.parseInt(number[col]);// we use as an index for number[] array
					
					mapTileNum[col][row] = num;// we store the extracted number in the maptileNum[][]
					col++;                       
				}
				if(col == gp.maxWorldCol) {// continue this until everything in the number[] is stored in the mapTileNum[][] 
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			
		}
	}
	public void draw(Graphics2D g2) {  // this is little bit complex whole constuctor
		
	//	g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[1].image,48, 0, gp.tileSize, gp.tileSize, null);
	//	g2.drawImage(tile[2].image,96, 0, gp.tileSize, gp.tileSize, null);
		
		
		int worldCol = 0;
		int worldRow = 0;
	
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];    
			// this map data has been stored in the mapTileNum[][]
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
					// basically this statement for bondary to the screen
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
				
			       g2.drawImage(tile[tileNum].image,screenX,screenY,null);
			       
			}
			worldCol++;
		
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
			
				worldRow++;
				
			}
		}
	}
}


