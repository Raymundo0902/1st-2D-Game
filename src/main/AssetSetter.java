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
        gp.obj[7].worldX = 13 * gp.tileSize;
        gp.obj[7].worldY = 74 * gp.tileSize;

        gp.obj[3] = new OBJ_TallGrass(gp);
        gp.obj[3].worldX = 14 * gp.tileSize;
        gp.obj[3].worldY = 74 * gp.tileSize;

        gp.obj[4] = new OBJ_TallGrass(gp);
        gp.obj[4].worldX = 15 * gp.tileSize;
        gp.obj[4].worldY = 74 * gp.tileSize;

        gp.obj[8] = new OBJ_TallGrass(gp);
        gp.obj[8].worldX = 13 * gp.tileSize;
        gp.obj[8].worldY = 75 * gp.tileSize;

        gp.obj[9] = new OBJ_TallGrass(gp);
        gp.obj[9].worldX = 14 * gp.tileSize;
        gp.obj[9].worldY = 75 * gp.tileSize;

        gp.obj[10] = new OBJ_TallGrass(gp);
        gp.obj[10].worldX = 15 * gp.tileSize;
        gp.obj[10].worldY = 75 * gp.tileSize;

        gp.obj[11] = new OBJ_TallGrass(gp);
        gp.obj[11].worldX = 12 * gp.tileSize;
        gp.obj[11].worldY = 75 * gp.tileSize;

        gp.obj[12] = new OBJ_Rock(gp);
        gp.obj[12].worldX = 21 * gp.tileSize;
        gp.obj[12].worldY = 52 * gp.tileSize;

        gp.obj[13] = new OBJ_Rock(gp);
        gp.obj[13].worldX = 23 * gp.tileSize;
        gp.obj[13].worldY = 54 * gp.tileSize;

        gp.obj[14] = new OBJ_Rock(gp);
        gp.obj[14].worldX = 24 * gp.tileSize;
        gp.obj[14].worldY = 54 * gp.tileSize;

        gp.obj[15] = new OBJ_Rock(gp);
        gp.obj[15].worldX = 24 * gp.tileSize;
        gp.obj[15].worldY = 55 * gp.tileSize;

        gp.obj[16] = new OBJ_Campfire(gp);
        gp.obj[16].worldX = 21 * gp.tileSize;
        gp.obj[16].worldY = 56 * gp.tileSize;

        gp.obj[17] = new OBJ_Campfire(gp);
        gp.obj[17].worldX = 13 * gp.tileSize;
        gp.obj[17].worldY = 77 * gp.tileSize;

        gp.obj[18] = new OBJ_Campfire(gp);
        gp.obj[18].worldX = 48 * gp.tileSize;
        gp.obj[18].worldY = 39 * gp.tileSize;

        gp.obj[19] = new OBJ_Rake(gp);
        gp.obj[19].worldX = 49 * gp.tileSize;
        gp.obj[19].worldY = 58 * gp.tileSize;

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
