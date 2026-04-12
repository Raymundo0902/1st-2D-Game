package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = TYPE_LIGHT;
        name = "Lantern";
        // change the image to the actual lantern image right now its using a rock image for quick testing.
        down1 = setup("/objects/lantern", gp.tileSize, gp.tileSize);
        description = "Reveal the darkness";
        lightRadius = 200;
    }
}
