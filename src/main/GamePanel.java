package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public final class GamePanel extends JPanel implements Runnable{
    // Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // 16x3(scale)=48
    
    public final int tileSize = originalTileSize * scale; // 48x48 tile 
    public final int maxScreenCol = 20;  // horizontal tile
    public final int maxScreenRow = 12;  // vertically tile    and the ratio is 4/3
    public final int screenWidth = tileSize * maxScreenCol;   // 960 pixels 
    public final int screenHeight = tileSize * maxScreenRow;  // 48x12=576 pixels
    
    // WORLD SETTINGS
    public final int maxWorldCol = 50;  
    public final int maxWorldRow = 50; 
    //public final int WorldWidth = tileSize * maxWorldCol;   
    //public final int WorldHeight = tileSize * maxWorldRow;  
    
    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage  tempScreen;
    Graphics2D g2;
    
    // FPS
    int FPS = 60;
    
    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler KeyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread; // starting the Game clock
    
    // ENTITY AND OBJECT
    public Player player = new Player(this,KeyH);
    public Entity obj[] = new Entity[20]; // we prepared 10 slots during the game 
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public InteractiveTile iTile[] = new InteractiveTile[50];
    public ArrayList<Entity>projectileList = new ArrayList<>();
    public ArrayList<Entity>particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    // GAME STATE
       public int gameState;
       public final int titleState = 0 ;
       public final int playState = 1;
       public final int pauseState = 2;
       public final int dialogueState = 3;
       public final int characterState = 4;
    // set player's default position
    // int playerX = 100;
    // int playerY = 100;
    // int playerSpeed = 4;
    
    // create constructor
    
    public GamePanel() {
    	this.setPreferredSize(new Dimension(screenWidth,screenHeight) );
    	this.setBackground(Color.black);
    	this.setDoubleBuffered(true); // if set to true , 
    	              // all the drawing from this component will be done in an off-screen painting buffer.
    	                              // in short , enabling this can improve game's rendering performance.
    	this.addKeyListener(KeyH);
    	this.setFocusable(true);
    	
    }
    public void setupGame() {
    	aSetter.setObjects();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	aSetter.setInteractiveTile();
    	//playMusic(0);
    	gameState = titleState;
    	
    	tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
    	g2 = (Graphics2D)tempScreen.getGraphics();
    	
    	setFullScreen();
    }
    public void setFullScreen() {
    	
    	// GET LOCAL SCREEN DEVICE
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(Main.window);
    	
    	// GET FULL SCREEN WIDTH AND HEIGHT
    	screenWidth2 = Main.window.getWidth();
    	screenHeight2 = Main.window.getHeight();
    	
    }
    public void startGameThread() {
    	gameThread = new Thread(this);
       	gameThread.start();
                           // its automatically call this run methods |
       	                   //                                       <--
    }
	@Override
	
//	public void run() {   // create a game loop which will be the core of our game
		 // Sleep method
		 
//		double drawInterval = 1000000000/FPS; // this 1 seconds 
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		 while(gameThread != null) {
//			
////    long currentTime = System.nanoTime();
//      Return the current value of the running java virtual machine's high-resolution time source, in nanoseconds.
////    long currentTime2 = System.currentTimeMillis();
////    System.out.println("current Time: "+currentTime);
////    1 UPDATE : Update information such as character positions
//	    update();
// //  2 DRAW : Draw the screen with the updated information 
//      repaint();
//			 
//			 
//			 try {
//			      double remainingTime = nextDrawTime - System.nanoTime();
//			      remainingTime = remainingTime/1000000;
//			 
//			 if(remainingTime < 0) {
//				 remainingTime = 0;
//			 }
//			
//				Thread.sleep((long)remainingTime);
//				
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		 }
//	}
	
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		// Return the current value of the running Java Virtual Machine's high-resolution time source
					// , in nanoseconds
		int timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				drawToTempScreen(); // draw everything to the buffered image
				drawToScreen(); // draw the buffered image to the screen
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				//System.out.println("FPS: "+ drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
	}
	public void update() {
		
		if(gameState == playState) {
			// PLAYER
			player.update();
			//NPC
			for(int i = 0 ; i < npc.length ; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i = 0; i < monster.length ; i++) {
				if(monster[i]!= null) {
					if(monster[i].alive == true && monster[i].dying == false) {
					monster[i].update();
				}
					if(monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;
					}
				}
			}
			for(int i = 0; i < projectileList.size() ; i++) {
				if(projectileList.get(i)!= null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
				}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
			for(int i = 0; i < particleList.size() ; i++) {
				if(particleList.get(i)!= null) {
					if(particleList.get(i).alive == true) {
						particleList.get(i).update();
				}
					if(particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}
			for(int i = 0; i < iTile.length;i++) {
				if(iTile[i] != null) {
					iTile[i].update();
				}
			}
		}
		if(gameState == pauseState) {
			// nothing
		}
		player.update();
	}
	public void drawToTempScreen() {
		
		// DEBUG function
		long drawStart = 0;
		if(KeyH.showDebugText == true ) {
			drawStart = System.nanoTime();
		}
		// TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}
		// OTHER
		else{
			// tile
			tileM.draw(g2); // make sure this line firstly implement than player draw line.
			
			// INTERACTIVE TILE
			for(int i = 0 ; i < iTile.length ; i++) {
				if(iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}
			// ADD ENTITIES TO THE LIST
            entityList.add(player);
            for(int i = 0 ; i < npc.length ; i++) {
            	if(npc[i] != null) {
            		entityList.add(npc[i]);
            	}
            }
            for(int i = 0 ; i < obj.length ; i++) {
            	if(obj[i] != null) {
            		entityList.add(obj[i]);
            	}
            }
            for(int i = 0 ; i < monster.length ; i++) {
            	if(monster[i] != null) {
            		entityList.add(monster[i]);
            	}
            }
            for(int i = 0 ; i < projectileList.size();  i++) {
            	if(projectileList.get(i) != null) {
            		entityList.add(projectileList.get(i));
            	}
            }
            for(int i = 0 ; i < particleList.size();  i++) {
            	if(particleList.get(i) != null) {
            		entityList.add(particleList.get(i));
            	}
            }
		    // PLAYER
			//player.draw(g2);
			
            // SORT
            Collections.sort(entityList,new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
            	
            });
            
            // DRAW ENTITIES
            for(int i = 0 ; i < entityList.size(); i++) {
            	entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            
            //for(int i = 0 ; i < entityList.size(); i++) {
            //entityList.remove(i); }
            
            // when you remove an entity from the list the array size also becomes -1 so it kind of break this loop's condition
            entityList.clear();
			// UI
			ui.draw(g2);
		}
		// DEBUG function
		if(KeyH.showDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			
			g2.setFont(new Font("Arial",Font.PLAIN,20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 400;
			int lineHeight = 20;
			
			g2.drawString("WorldX "+ player.worldX , x, y); y += lineHeight;
			g2.drawString("WorldY "+ player.worldY , x, y); y += lineHeight;
			g2.drawString("Col " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
			g2.drawString("Row " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
			g2.drawString("Draw Time: " + passed , x , y);
		}
	}
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2,null);
		g.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
		
	}
}
