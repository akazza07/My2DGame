package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
  
	GamePanel gp;
	KeyHandler KeyH;
	 public Player(GamePanel gp , KeyHandler KeyH) {
		 
		 this.gp = gp;
		 this.KeyH = KeyH;
		 
		 setDefaultValues();
		 getPlayerImage();
	 }
	public void setDefaultValues() {
		
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right2.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		
		}
	}
	public void update() {
		
		if(KeyH.upPressed == true || KeyH.downPressed == true || // manually start moving character only this statement
				KeyH.leftPressed == true || KeyH.rightPressed == true) {
			
			
			
			if(KeyH.upPressed == true) {
				direction = "up";
				y -= speed;	
			}
			else if (KeyH.downPressed == true) {
				direction = "down";
				y += speed;
			}
			else if (KeyH.leftPressed == true) {
				direction = "left";
				x -= speed;
			}
			else if (KeyH.rightPressed == true) {
				direction = "right";
				x += speed;
			}
			
			
			spriteCounter++;  // basically it means the player changes in every 10 frames
			if(spriteCounter > 12) { // motion of character by how much speed moving
				if(spriteNum == 1 ) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}  
		}
		
	 }
	
	public void draw(Graphics2D g2) {
  //      g2.setColor(Color.white);
		
  //		g2.fillRect(x, y, gp.tileSize, gp.tileSize); // this need to public 
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
			image = up1;
		}   
			if(spriteNum == 2){
			image = up2;
		}
			
		break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}   
				if(spriteNum == 2){
				image = down2;
				}
		break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}   
				if(spriteNum == 2){
				image = left2;
				}
		break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}   
				if(spriteNum == 2){
				image = right2;
				}
		break;
		}
		g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
	}
}
