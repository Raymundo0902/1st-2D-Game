package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_TallGrass extends Entity {

    public OBJ_TallGrass(GamePanel gp) {
        super(gp);

        name = "TallGrass";
        down1 = setup("/objects/tallGrassFull", gp.tileSize, gp.tileSize);
//        image2 = setup("/objects/tallGrassMed", gp.tileSize, gp.tileSize);
//        image3 = setup("/objects/tallGrassLow", gp.tileSize, gp.tileSize);
        maxLife = 4;
        curLife = maxLife;



        collision = true; // not required to add this. just a pointer.
    }
}
