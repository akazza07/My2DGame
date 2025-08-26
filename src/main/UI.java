package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.OBJ_Heart;
import object.SuperObject;
public class UI {
	
   GamePanel gp;
   Graphics2D g2;
   Font maruMonica , purisaB;
   BufferedImage heart_full , heart_half , heart_blank;
   //Font arial_40 , arial_80B;
   // BufferedImage keyImage;
   public boolean messageOn = false;
   public String message = "";
   int messageCounter = 0;
   public boolean gameFinished = false;
   public String currentDialogue = "";
   public int commandNum = 0;
   
   // double playTime;
   // DecimalFormat dFormat = new DecimalFormat("#0.00");
   
   // means , we display up to tow places of decimal 
   public UI(GamePanel gp) {
	   this.gp = gp;
	   
     try {
    	 
        InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
		purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
		maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		// Add another font if u want too
	} 
     catch (FontFormatException e) {
		e.printStackTrace();
	} 
     catch (IOException e) {
		e.printStackTrace();
	}
    // CREATE HUD OBJECT
     SuperObject heart = new OBJ_Heart(gp);
     heart_full = heart.image;
     heart_half = heart.image2;
     heart_blank = heart.image3;
   }
   public void showMessage(String text) {
	   
	   message = text;
	   messageOn = true;
   }
   public void draw(Graphics2D g2) {
	   
	   this.g2 = g2;
	   
	   //g2.setFont(purisaB);
	   g2.setFont(maruMonica);
	   g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	   g2.setColor(Color.white);
	   // TITLE STATE
	   if(gp.gameState == gp.titleState) {
		   drawTitleScreen();
	   }
	   // PLAY STATE
	   if(gp.gameState == gp.playState) {
		   drawPlayerLife();
	   // PAUSE STATE   
	   }if(gp.gameState == gp.pauseState) {
		   drawPlayerLife();
		   drawPauseScreen();
	   }
	   // DIALOGUE STATE
	   if(gp.gameState == gp.dialogueState) {
		   drawPlayerLife();
		   drawDialogueScreen();
	   }
/*	   if(gameFinished == true ) {
		   
		   g2.setFont(arial_40);                   
		   g2.setColor(Color.white);
		   
		   String text;
		   int textLength;
		   int x;
		   int y;
		   
		   text = "You found the treasure!";
		   textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		   x = gp.screenWidth/2 - textLength/2 ;
		   y = gp.screenHeight/2 - (gp.tileSize*3);
		   g2.drawString(text,x,y);
		  
		   
		   text = "Your Time is : " + dFormat.format(playTime) + "!";
		   textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		   x = gp.screenWidth/2 - textLength/2 ;
		   y = gp.screenHeight/2 + (gp.tileSize*4);
		   g2.drawString(text,x,y);
		   
		   
		   g2.setFont(arial_80B);                   
		   g2.setColor(Color.yellow);
			
		   text = "Congratulations!";
		   textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();   
		   x = gp.screenWidth/2 - textLength/2 ;
		   y = gp.screenHeight/2 + (gp.tileSize*2);
		   g2.drawString(text,x,y);
		   
		  
		   gp.gameThread = null ;
	       } else {
		   g2.setFont(arial_40);                   // Dont instantiate it in the loop
		   g2.setColor(Color.white);
		   g2.drawImage(keyImage , gp.tileSize/2 , gp.tileSize/2 , gp.tileSize , gp.tileSize , null);
		   g2.drawString("x "+ gp.player.hasKey,74,65);
		   
		   // TIME 
		   
		   playTime  += (double)1/60;
		   g2.drawString("Time:"+ dFormat.format(playTime), gp.tileSize*11 , 65);
		   
		   // MESSAGE
		   if(messageOn == true) {
			   
			   g2.setFont(g2.getFont().deriveFont(30F));
			   g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
			   
			   messageCounter++;
			   
			   if(messageCounter > 120) { // 2sec
				   messageCounter = 0;
				   messageOn = false;
			   }
		   }
	   } */
   }
   public void drawPlayerLife() {
	   
   //  gp.player.life = 5;
	   
	   int x = gp.tileSize/2;
	   int y = gp.tileSize/2;
	   int i = 0;
	   
	   // DRAW MAX HEART
	   while(i<gp.player.maxLife/2) { // TWO lifes means one heart so that's why we divide this max by 2
		   g2.drawImage(heart_blank, x, y , null);
		   i++;
		   x += gp.tileSize; 
	   }
	   // RESET
	   x = gp.tileSize/2;
	   y = gp.tileSize/2;
	   i = 0;
	   // DRAW CURRENT LIFE
	   while(i<gp.player.life) {
		   g2.drawImage(heart_half,x,y,null);
		   i++;
		   if(i<gp.player.life) {
			   g2.drawImage(heart_full, x, y,null);
		   }
		   i++;
		   x += gp.tileSize;
	   }
   }
   public void drawTitleScreen() {
	   
	   g2.setColor(new Color(0,0,0));
	   g2.fillRect(0, 0 , gp.screenWidth, gp.screenHeight);
	   // TITLE NAME
	   g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
	   String text = "Treasure Hunter";
	   int x = getXforCenteredText(text);
	   int y = gp.tileSize*3;
	   
	   // SHADOW
	   g2.setColor(Color.gray);
	   g2.drawString(text,x+5,y+5);
	   // MAIN COLOR
	   g2.setColor(Color.white);
       g2.drawString(text, x, y);
       // CHARACTER IMAGE
       x = gp.screenWidth/2 - (gp.tileSize*2)/2 ; // center position 
       y += gp.tileSize*2;
       g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2,null);
       
       // MENU
       g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
       
       text = "NEW GAME";
       x = getXforCenteredText(text);
       y += gp.tileSize*3.5;
       g2.drawString(text, x, y);
       if(commandNum == 0) {
    	   g2.drawString(">",x-gp.tileSize,y);
       }
       
       text = "LOAD GAME";
       x = getXforCenteredText(text);
       y += gp.tileSize;
       g2.drawString(text, x, y);
       if(commandNum == 1) {
    	   g2.drawString(">",x-gp.tileSize,y);
       }
       
       text = "QUIT";
       x = getXforCenteredText(text);
       y += gp.tileSize;
       g2.drawString(text, x, y);
       if(commandNum == 2) {
    	   g2.drawString(">",x-gp.tileSize,y);
       }
       
   }
   public void drawPauseScreen() {
	   
	   g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));  // increase font size by integer
	   String text = "PAUSED";
	   int x = getXforCenteredText(text);
	   int y = gp.screenHeight/2;
	   
	   g2.drawString(text, x, y);
   }
   public void drawDialogueScreen() {
	   
	   // WINDOW
	   int x = gp.tileSize*2;
	   int y = gp.tileSize/2;
	   int width = gp.screenWidth - (gp.tileSize*4);
	   int height = gp.tileSize*4;
	   
	   drawSubWindow(x,y,width,height);
	   
	   g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
	   x += gp.tileSize;
	   y += gp.tileSize;
	   
	   for(String line : currentDialogue.split("\n")) {
	   g2.drawString(line,x,y);
	   y += 40;
	   }
   }
   public void drawSubWindow(int x , int y , int width , int height ) {
	   
	   Color c = new Color(0,0,0,210); // opacity of dialogue box 
	   // new Color(int,int,int) = Create RGB color
	   g2.setColor(c);
	   g2.fillRoundRect(x, y, width, height, 35, 35);
	   
	   c = new Color(255,255,255); // this is the RGB number for white
	   g2.setColor(c);
	   g2.setStroke(new BasicStroke(5)); // Defines the width of outlines of graphics which are rendered with a Graphics2D
	   g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
   }
   public int getXforCenteredText(String text) {
	   int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
	   int x = gp.screenWidth/2 - length/2;
	   return x;
   }
}
