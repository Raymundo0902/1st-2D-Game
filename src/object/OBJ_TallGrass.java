package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_TallGrass extends Entity {

    BufferedImage grassFull, grassMed, grassLow;
    public int grassState;

    public OBJ_TallGrass(GamePanel gp) {

        super(gp);
        name = "tallGrass";
        grassFull = setup("/objects/grassFull", gp.tileSize, gp.tileSize);
        grassMed = setup("/objects/grassMed", gp.tileSize, gp.tileSize);
        grassLow = setup("/objects/grassLow", gp.tileSize, gp.tileSize);
        maxLife = 3;
        curLife = maxLife;
        grassState = 3;

        collision = true; // not required to add this. just a pointer.
    }

    @Override
    public void update() {

        if(curLife == 2) grassState = 2;
        if(curLife == 1) grassState = 1;

        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // find out its screenX and Y.
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // if it's in the camera frame then draw it.
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch(grassState) { // based on this direction we will pick an image from below

                case 3: image = grassFull; break;
                case 2: image = grassMed; break;
                case 1: image = grassLow; break;
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            // COLLISION VISUALS (DEBUG)
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
