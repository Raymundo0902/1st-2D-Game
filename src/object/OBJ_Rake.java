package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Rake extends Entity {

    public OBJ_Rake(GamePanel gp) {
        super(gp);

        type = TYPE_RAKE;
        name = "Rake";
        down1 = setup("/objects/rake", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\n Rusty, good ol' rake.";

    }


    public void update() {
        // is a static image so it just doesnt update
    }

}
