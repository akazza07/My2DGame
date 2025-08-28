package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Carrot extends Entity{

	public OBJ_Carrot(GamePanel gp) {
		super(gp);
		name = "Carrot";
		down1 = setup("/obj/carrot");
		
	}

}
