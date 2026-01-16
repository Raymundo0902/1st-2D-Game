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

    public int checkObject (Entity entity, boolean player) { // we recieve either a player, enemy, etc and then check if the entity is player or not

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) { // going to check obj array from SuperObject class

            if(gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;// simulating entity's movement and check where it wil be after it moved.
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(gp.obj[i].solidArea)) { // checks if two rectangles are colliding/touching or not. Which for intersects is the reason we needed to find solidArea's current position
                    if (gp.obj[i].collision == true) {
                        entity.collisionOn = true;
                    }
                    if (player == true) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX; // setting the entity and obj solidAreas back to default otherwise the x and y will keep increasing. Code is at the beginning of if statement
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
    // NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for(int i = 0; i < target.length; i++) { // going to check target array from SuperObject class

            if(target[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch(entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break; // simulating entity's movement and check where it wil be after it moved.
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(target[i].solidArea)) { // checks if two rectangles are colliding/touching or not. Which for intersects is the reason we needed to find solidArea's current position
                    if(target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX; // setting the entity and obj solidAreas back to default otherwise the x and y will keep increasing. Code is at the beginning of if statement
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch(entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break; // simulating entity's movement and check where it wil be after it moved.
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }

        if(entity.solidArea.intersects(gp.player.solidArea)) { // checks if two rectangles are colliding/touching or not. Which for intersects is the reason we needed to find solidArea's current position
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX; // setting the entity and obj solidAreas back to default otherwise the x and y will keep increasing. Code is at the beginning of if statement
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }

}

