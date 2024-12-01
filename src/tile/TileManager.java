package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

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
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall3.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/mat1.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand1.png"));
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall2.png"));
			tile[6].collision = true;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/banner.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/flower.png"));
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall1.png"));
			tile[9].collision = true;
			
			
	}catch(IOException e) {
		e.printStackTrace();
	
	}
  }
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine(); // read a line of text.
				
				while(col < gp.maxWorldCol) {
					
					String number[] = line.split(" "); // split the string at a space
					
					int num = Integer.parseInt(number[col]); // we use as an index for number[] array
					
					mapTileNum[col][row] = num; // we store the extracted number in the maptileNum[][]
					col++;                       
				}
				if(col == gp.maxWorldCol) {    // continue this until everything in the number[] is stored in the mapTileNum[][] 
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
			
			int tileNum = mapTileNum[worldCol][worldRow];    // this map data has been stored in the mapTileNum[][]
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // basically this statement for bondary to the screen
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
				
			       g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
			       
			}
			worldCol++;
		
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
			
				worldRow++;
				
			}
		}
	}
}


