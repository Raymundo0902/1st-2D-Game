package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Map extends Entity {


    public OBJ_Map(GamePanel gp) {
        super(gp);

        down1 = setup("/objects/map", gp.tileSize, gp.tileSize);
        description = "pinewood's best map";
    }
}
