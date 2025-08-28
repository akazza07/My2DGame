package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "Heart";
		image = setup("/obj/heart_full");
		image2 = setup("/obj/heart_half");
		image3 = setup("/obj/heart_blank");

	}

}
