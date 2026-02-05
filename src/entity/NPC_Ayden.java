package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Ayden extends Entity {

    public NPC_Ayden(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 2;
        getNPCImage();
        setDialogue();

        solidArea = new Rectangle(); // values below are what parts of the character will be solid
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x; // reason we create solidAreaDefaultX,Y is so we can recall the default values of solidArea.x and y because we will change solidArea.x and y later.
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        type = TYPE_NPC;
    }
    public void getNPCImage() {

        up1 = setup("/npc/ayden_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/ayden_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/ayden_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/ayden_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/ayden_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/ayden_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/ayden_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/ayden_right2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Hello, sir.";
        dialogues[1] = "This camp is nice, bout' to go fishing\nwith the boys!";
        dialogues[2] = "On my way here, I heard some rumors\nabout some killer on the loose at the city\nclose to here-- creepy stuff huh..";
        dialogues[3] = "Welp, have a good one officer!";
    }

    public void setAction() {

        actionLockCounter++;
        if(actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // get random number from 1-100. +1 because it picks from 0-99.

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }

    public void speak() {

        // DO THIS CHARACTER SPECIFIC STUFF
        super.speak();

    }

}
