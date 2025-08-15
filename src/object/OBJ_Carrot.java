package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Carrot extends SuperObject{
	public OBJ_Carrot() {
		name = "Carrot";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/obj/carrot.png"));
		
	}catch(IOException e) {
		e.printStackTrace();
	}
	}

}
