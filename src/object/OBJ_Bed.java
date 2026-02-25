package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bed extends Entity {


    public OBJ_Bed(GamePanel gp) {
        super(gp);
        name = "Bed";
        down1 = setup("/objects/bed", gp.tileSize, gp.tileSize*2);
        collision = true;
    }

    @Override
    public void update() {

    }
}
