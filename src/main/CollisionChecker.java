package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) { // not player but entity because we will use this method to check enemies, npc's collision as well.

        // these four variables just store the coordinates of where the collision box is. this helps find their col and row nums
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;


        // FORMULA FOR THE 4 BELOW IS: TILE INDEX = WORLDCOORDS / TILESIZE
        int entityLeftCol = entityLeftWorldX/gp.tileSize; // which column the left side of the hitbox is in.
        int entityRightCol = entityRightWorldX/gp.tileSize; // which column the right side of the hitbox is in
        int entityTopRow = entityTopWorldY/gp.tileSize; // which row the top side of the hitbox is in
        int entityBottomRow = entityBottomWorldY/gp.tileSize; // which row the bottom side of the hitbox is in

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; // predicting where entity hitbox will be after he takes step
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // checks the left side point and stores in
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // if one is true then player is hitting solid tile which means he cannot move that direction
                    entity.collisionOn = true; // if its false we dont do nothing
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // if one is true then player is hitting solid tile which means he cannot move that direction
                    entity.collisionOn = true; // if its false we dont do nothing
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // if one is true then player is hitting solid tile which means he cannot move that direction
                    entity.collisionOn = true; // if its false we dont do nothing
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // if one is true then player is hitting solid tile which means he cannot move that direction
                    entity.collisionOn = true; // if its false we dont do nothing
                }
                break;

        }
    }
}
