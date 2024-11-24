package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
 
	// Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // 16x3(scale)=48
    
    public final int tileSize = originalTileSize * scale; // 48x48 tile 
    public final int maxScreenCol = 16;  // horizontal tile
    public final int maxScreenRow = 12;  // vertically tile    and the ratio is 4/3
    public final int screenWidth = tileSize * maxScreenCol;   // 48x16=768 pixels 
    public final int screenHeight = tileSize * maxScreenRow;  // 48x12=576 pixels
    
    // FPS
    int FPS = 60;
    // 
    TileManager tileM = new TileManager(this);
    KeyHandler KeyH = new KeyHandler();
    Thread gameThread; // starting the Game clock
    
    Player player = new Player(this,KeyH);
    // set player's default position
    // int playerX = 100;
    // int playerY = 100;
    // int playerSpeed = 4;
    
    // create constructor
    
    public GamePanel() {
    	this.setPreferredSize(new Dimension(screenWidth,screenHeight) );
    	this.setBackground(Color.black);
    	this.setDoubleBuffered(true); // if set to true , all the drawing from this component will be done in an off-screen painting buffer.
    	                              // in short , enabling this can improve game's rendering performance.
    	this.addKeyListener(KeyH);
    	this.setFocusable(true);
    	
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
//			 // long currentTime = System.nanoTime(); // Return the current value of the running java virtual machine's high-resolution time source, in nanoseconds.
//			 // long currentTime2 = System.currentTimeMillis();
//			 // System.out.println("current Time: "+currentTime);
//			 // 1 UPDATE : Update information such as character positions
//			 update();
//			 // 2 DRAW : Draw the screen with the updated information 
//			 repaint();
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
		int timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: "+ drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
	}
	public void update() {
		
		player.update();
	}
	public void paintComponent(Graphics g) {  // java in-build method // graphics = a class that has many functions to draw objects on the screen.
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; // Graphics2D class extends the Graphics class to provide more sophisticated controls over geometry , coordinate transformation , color management , and text layout.
		
		tileM.draw(g2); // make sure this line firstly implement than player draw line.
		player.draw(g2);
		
		g2.dispose(); // dispose of this graphics context and release any system resources that it is using. But without this line the program still work , this is good for practice
		
	}
}
