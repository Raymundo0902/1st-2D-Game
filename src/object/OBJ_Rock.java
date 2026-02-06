package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile { // SUB CLASS OF PROJECTILE AND ENTITY BUT DIRECT PARENT IS PROJECTILE

    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Rock";
        type = TYPE_ROCK;
        speed = 2;
        maxLife = 40;
        curLife = maxLife;
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
