package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_Rock extends Projectile { // SUB CLASS OF PROJECTILE AND ENTITY BUT DIRECT PARENT IS PROJECTILE

    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Rock";
        type = TYPE_ROCK;
        speed = 6;
        maxLife = 40;
        curLife = maxLife;
        direction = "left";
        description = "[Rock]\nRocks may save your life.";
        collision = true;

        solidArea = new Rectangle(); // values below are what parts of the character will be solid
        solidArea.x = 18;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x; // reason we create solidAreaDefaultX,Y is so we can recall the default values of solidArea.x and y because we will change solidArea.x and y later.
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 12;
        solidArea.height = 12;

        getImage();
    }

    public void getImage() {

        left1 = setup("/projectiles/leftRightThrowRock1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectiles/leftRightThrowRock2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectiles/leftRightThrowRock1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectiles/leftRightThrowRock2", gp.tileSize, gp.tileSize);

        up1 = setup("/projectiles/upDownThrowRock1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectiles/upDownThrowRock2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectiles/upDownThrowRock1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectiles/upDownThrowRock2", gp.tileSize, gp.tileSize);

    }


}
