package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_K4Door extends Entity {

    public OBJ_K4Door(GamePanel gp) {
        super(gp);
        name = "k4door";
        down1 = setup("/objects/k4door", gp.tileSize, gp.tileSize);
        collision = true;
    }

    public void update() {

    }
}
