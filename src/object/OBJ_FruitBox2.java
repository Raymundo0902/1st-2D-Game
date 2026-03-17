package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_FruitBox2 extends Entity {

    public OBJ_FruitBox2(GamePanel gp) {
        super(gp);
        name = "fruitBox2";
        down1 = setup("/objects/fruitBox2", gp.tileSize *2 , gp.tileSize*2);
        collision = true;
        solidArea.x = 14;
        solidArea.y = 18;
        // Must have these below so the solidArea stays at same location after certain player decisions
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32 * 2;
        solidArea.height = (24 * 2);
    }

    @Override
    public void update() {

    }

}
