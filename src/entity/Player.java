package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
  
	//GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
//	public int hasKey = 0;
	int standCounter = 0;
	public boolean attackCanceled = false;
	
	
	 public Player(GamePanel gp , KeyHandler KeyH) {
		 
		 super(gp);// this means we are calling the contuctor of the superclass of this class
		 
		// this.gp = gp;
		 this.KeyH = KeyH;
		 
		 screenX = gp.screenWidth/2 - (gp.tileSize/2); 
		 screenY = gp.screenHeight/2 - (gp.tileSize/2); // this return halfway of screen
		 
		 solidArea = new Rectangle();
		 solidArea.x = 8; // we made solid area under the character tile ,
		                  // so we easily pass through the collision tile.
		 solidArea.y = 8;
		 solidAreaDefaultX = solidArea.x;
		 solidAreaDefaultY = solidArea.y;
		 solidArea.width = 32;
		 solidArea.height = 32;
		 
		 attackArea.width = 36;
		 attackArea.height = 36;
		 
		 setDefaultValues();
		 getPlayerImage();
		 getPlayerAttackImage();
	 }
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		 direction = "down";
		 
		 // PLAYER STATUS
		 level = 1;
		 maxLife = 6;
		 life = maxLife; // in this game one life means half heart and two life means full heart so six lifes three hearts
	     strength = 1; // the more strength he has , the more damage he gives
	     dexterity = 1; // the more dexterity he has , the less damage he recieves.
	     exp = 0;
	     nextLevelExp = 5;
	     coin = 0;
	     currentWeapon = new OBJ_Sword_Normal(gp);
	     currentShield = new OBJ_Shield_Wood(gp);
	     attack = getAttack(); // the total attack value is decided bty strength and weapon
	     defence = getDefence(); // the total defence value is decided by dexterity and shield
	}
	public int getAttack() {
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefence() {
		return defence = dexterity * currentShield.defenceValue;
	}
	public void getPlayerImage() {

		up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize);
		
	}
	public void getPlayerAttackImage() {
		
		attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
		attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
		attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
		attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
		attackLeft1 = setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
		attackLeft2 = setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
		attackRight1 = setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
		attackRight2 = setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
		
	}

	public void update() {
	        if(attacking == true) {
	           attacking(); 	
	        } 
	        // Basically we bypass this key input while he is attacking
	        else if(KeyH.upPressed == true || KeyH.downPressed == true || 
					// manually start moving character only this statement
					KeyH.leftPressed == true || KeyH.rightPressed == true || KeyH.enterPressed == true) {
				
				if (KeyH.upPressed == true) {direction = "up";}
		   else if (KeyH.downPressed == true) {direction = "down";}
		   else if (KeyH.leftPressed == true) {direction = "left";}
		   else if (KeyH.rightPressed == true) {direction = "right";}
				
				// check tile collision 
				
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				// check object collision 
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);
				
				// check NPC collision
				int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
				interactNPC(npcIndex);
				
				// check Monster collision
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				contactMonster(monsterIndex);
				
				// CHECK EVENT
				gp.eHandler.checkEvent();
				
				 // if collision is false , player can move
				
				if (collisionOn == false && gp.KeyH.enterPressed == false) {
					
					switch(direction) {
					case "up":  worldY -= speed;break;
					case "down": worldY += speed;break;
					case "left": worldX -= speed;break;
					case "right": worldX += speed;break;
					}
				}
				if(KeyH.enterPressed == true && attackCanceled == false) {
					gp.playSE(7);
					attacking = true;
					spriteCounter = 0;
				}
				attackCanceled = false;
				
				gp.KeyH.enterPressed = false;
				
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
			else {
				
				standCounter++;
				
				if(standCounter == 20) {
					spriteNum = 1;
					standCounter = 0;
				}
			}
		// This needs to be outside of the key if statement !
			if(invincible == true) {
				invincibleCounter++;
				if(invincibleCounter > 60) {
					invincible = false;
					invincibleCounter =0 ;
				}
			}
		}
	
	public void attacking() {
		
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum =1 ;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// Save the current worldX , worldY , solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// Adjust player's worldX/Y for the attackArea
			switch(direction) {
			case"up":worldY -= attackArea.height; break;
			case"down":worldY += attackArea.height; break;
			case"left":worldX -= attackArea.width; break;
			case"right":worldX += attackArea.width; break;
			}
			// attackArea become solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			// Check monster colision with the updated worldX , worldY and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
			damageMonster(monsterIndex);
			
			// After checking collision , restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			
			
	/*		String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case"Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a Key!!");
				break;
			case"Door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
				
					hasKey--;
					gp.ui.showMessage("You opened the door!");
				}else {
					gp.ui.showMessage("You need a Key!");
				}	
				break;
				
			case "Carrot":
				gp.playSE(2);
				speed += 1.7;  // How much power get the player
				gp.obj[i] = null;
				gp.ui.showMessage("Speed Up!");
				break;
			
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			} 
			**/
		}		
	}
	
	public void interactNPC(int i ) {
		
//		System.out.println("u are hitting on an npc");
		if(gp.KeyH.enterPressed == true ) {
			if(i != 999) {
				        attackCanceled = true;
						gp.gameState = gp.dialogueState;
						gp.npc[i].speak();	
				}
		    }
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			if(invincible == false) {
				gp.playSE(6);
				life -= 1;
				invincible = true;
			}	
		}
	}
	public void damageMonster(int i) {
		if(i != 999) {
			if(gp.monster[i].invincible == false) {
				
				gp.playSE(5);
				gp.monster[i].life -= 1;
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0 ) {
					gp.monster[i].dying = true;
				}
			}
		}
	}
	public void draw(Graphics2D g2) {
  //      g2.setColor(Color.white);
		
  //	  g2.fillRect(x, y, gp.tileSize, gp.tileSize); // this need to public 
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}   
				if(spriteNum == 2){image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}   
				if(spriteNum == 2){image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}   
				if(spriteNum == 2){image = down2;}	
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;}   
				if(spriteNum == 2){image = attackDown2;}	
			}
		    break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}   
				if(spriteNum == 2){image = left2;}
			}
		    if(attacking == true) {
		    	tempScreenX = screenX - gp.tileSize;
		    	if(spriteNum == 1) {image = attackLeft1;}   
				if(spriteNum == 2){image = attackLeft2;}
		    }
		    break;
		case "right":
			if(attacking == false ) {
				if(spriteNum == 1) {image = right1;}   
				if(spriteNum == 2){image = right2;}
			}
			if(attacking == true ) {
				if(spriteNum == 1) {image = attackRight1;}   
				if(spriteNum == 2){image = attackRight2;}
			}
		    break;
		}
		if(invincible == true ) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image,tempScreenX,tempScreenY,null);
		
		// Reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG
		//g2.setFont(new Font("Arial",Font.PLAIN,26));
		//g2.setColor(Color.white);
		//g2.drawString("Invincible:"+invincibleCounter, 10, 400);
	}
}
