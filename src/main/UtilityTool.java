package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {

	public BufferedImage scaleImage(BufferedImage original, int width , int height) {
		
		BufferedImage scaledImage = new BufferedImage(width , gp.tileSize , tile[0].image.getType());
		Graphics2D g2 = scaledImage.createGraphics(); 
		g2.drawImage(tile[0].image,0,0,gp.tileSize,gp.tileSize,null); 
	}
}
