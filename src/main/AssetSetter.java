package main;

import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // INSTANTIATED THESE 2 KEYS AND SET THEIR DEFAULT LOCATION.
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        // INSTANTIATED THIS DOOR AND SET ITS DEFAULT LOCATION.
        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 10 * gp.tileSize;
        gp.obj[2].worldY = 11 * gp.tileSize;

        gp.obj[5] = new OBJ_Door2(); // faces horizontally
        gp.obj[5].worldX = 33 * gp.tileSize;
        gp.obj[5].worldY = 60 * gp.tileSize;

        gp.obj[3] = new OBJ_Chest();
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 8 * gp.tileSize;

        gp.obj[4] = new OBJ_Boots();
        gp.obj[4].worldX = 37 * gp.tileSize;
        gp.obj[4].worldY = 42 * gp.tileSize;
    }


}
