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
		
		tile = new Tile[50]; // increase the tiles whenever u want
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		
		loadMap("/maps/worldV2.txt");
	}
	public void getTileImage() {  // in this we have to add tiles
		    // we don't use the tile 0 to 9 but I've set a placeholder image so we can prevent NullPointer exception happens when we scan array
		
		    // PLACEHOLDER
			setup(0, "grass", false);
			setup(1, "grass", false);
			setup(2, "grass", false);
			setup(3, "grass", false);
			setup(4, "grass", false);
			setup(5, "grass", false);
			setup(6, "grass", false);
			setup(7, "grass", false);
			setup(8, "grass", false);
			setup(9, "grass", false);
			
			// PLACEHOLDER
			setup(10,"grass",false);
			setup(11,"grass2",false);
			setup(12,"water00",true);
			setup(13,"water01",true);
			setup(14,"water02",true);
			setup(15,"water03",true);
			setup(16,"water04",true);
			setup(17,"water05",true);
			setup(18,"water06",true);
			setup(19,"water07",true);
			setup(20,"water08",true);
			setup(21,"water09",true);
			setup(22,"water10",true);
			setup(23,"water11",true);
			setup(24,"water12",true);
			setup(25,"water13",true);
			
			setup(26,"road00",false);
			setup(27,"road01",false);
			setup(28,"road02",false);
			setup(30,"road03",false);
			setup(31,"road04",false);
			setup(32,"road05",false);
			setup(33,"road06",false);
			setup(34,"road07",false);
			setup(35,"road08",false);
			setup(36,"road09",false);
			setup(37,"road10",false);
			setup(38,"road11",false);
			setup(39,"road12",false);
			setup(40,"earth",false);
			setup(41,"wall1",true);
			setup(42,"tree",true);
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


