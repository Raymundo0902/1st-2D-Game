package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_EVILBILL extends Entity {

    public MON_EVILBILL(GamePanel gp) {
        super(gp);

        name = "Evil Bill";
        speed = 4;
        maxLife = 4;
        curLife = maxLife;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x; // reason we create solidAreaDefaultX,Y is so we can recall the default values of solidArea.x and y because we will change solidArea.x and y later.
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() { // load and scale monster images

        up1 = setup("/monster/monster_up1");
        up2 = setup("/monster/monster_up2");
        down1 = setup("/monster/monster_down1");
        down2 = setup("/monster/monster_down2");
        left1 = setup("/monster/monster_left1");
        left2 = setup("/monster/monster_left2");
        right1 = setup("/monster/monster_right1");
        right2 = setup("/monster/monster_right2");
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
}
