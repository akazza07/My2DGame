package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
	}

	public void getImage() {

		up1 = setup("/npc/oldman_up1");
		up2 = setup("/npc/oldman_up2");
		down1 = setup("/npc/oldman_down1");
		down2 = setup("/npc/oldman_down2");
		left1 = setup("/npc/oldman_left1");
		left2 = setup("/npc/oldman_left2");
		right1 = setup("/npc/oldman_right1");
		right2 = setup("/npc/oldman_right2");
		
	}

}
