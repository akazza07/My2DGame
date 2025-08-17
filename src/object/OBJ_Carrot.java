package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Carrot extends SuperObject{
	
	GamePanel gp;
	public OBJ_Carrot(GamePanel gp) {
		name = "Carrot";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/obj/carrot.png"));
		    uTool.scaleImage(image , gp.tileSize , gp.tileSize);
	}catch(IOException e) {
		e.printStackTrace();
	}
	}

}
