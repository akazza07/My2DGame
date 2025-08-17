package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp) {
		
		this.gp = gp;
		name = "Chest";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/obj/chest.png"));
		
	}catch(IOException e) {
		e.printStackTrace();
	}

	}
}
