package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
          
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		type = type_shield;
		name = "Blue Shield";
		down1 = setup("/obj/shield_blue",gp.tileSize,gp.tileSize);
		defenceValue = 2;
		description = "[ " + name + " ]\nA Shiny Blue shield.";
	}
}
