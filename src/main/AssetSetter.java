package main;

import entity.NPC_Ayden;
import monster.MON_EVILBILL;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // INSTANTIATED THESE 2 KEYS AND SET THEIR DEFAULT LOCATION.
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 26 * gp.tileSize;
        gp.obj[0].worldY = 10 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 41 * gp.tileSize;
        gp.obj[1].worldY = 11 * gp.tileSize;

        // INSTANTIATED THIS DOOR AND SET ITS DEFAULT LOCATION.
        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 13 * gp.tileSize;
        gp.obj[2].worldY = 14 * gp.tileSize;

        gp.obj[6] = new OBJ_Door(gp);
        gp.obj[6].worldX = 20 * gp.tileSize;
        gp.obj[6].worldY = 38 * gp.tileSize;

        gp.obj[5] = new OBJ_Door2(gp); // faces horizontally
        gp.obj[5].worldX = 36 * gp.tileSize;
        gp.obj[5].worldY = 63 * gp.tileSize;

        gp.obj[7] = new OBJ_TallGrass(gp);
        gp.obj[7].worldX = 30 * gp.tileSize;
        gp.obj[7].worldY = 63 * gp.tileSize;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Ayden(gp);
        gp.npc[0].worldX = gp.tileSize*30;
        gp.npc[0].worldY = gp.tileSize*70;
    }

    public void setMonster() {
        gp.monster[0] = new MON_EVILBILL(gp);
        gp.monster[0].worldX = gp.tileSize*26;
        gp.monster[0].worldY = gp.tileSize*43;
    }
}
