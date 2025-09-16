package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
       GamePanel gp;
       
	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Mana Crystal";
		image = setup("/obj/manacrystal_full",gp.tileSize,gp.tileSize);
		image2 = setup("/obj/manacrystal_blank",gp.tileSize,gp.tileSize);
	}

}
