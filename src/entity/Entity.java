package entity;

import java.awt.image.BufferedImage;

public class Entity { // this stores variables that will be used in player , monster and NPC classes.
    
	public int x,y;
	public int speed;
	
	public BufferedImage up1 , up2 , down1 , down2 , left1 , left2 , right1 , right2 ; // BufferedImage = it describes an image with an accessible buffer of image data. (we use this to store our image files)
	
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
