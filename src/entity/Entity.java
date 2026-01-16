package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// THIS CLASS STORES VARIABLES THAT WILL BE USED IN PLAYER, MONSTER AND NPC CLASSES.
public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // BufferedImage describes an Image with an accessible buffer of image data. (we use this to store our image files)
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // create a invisible or abstract rectangle and store data like x,y width and height. is the default solidArea but can be changed within the children classes
    public int solidAreaDefaultX, solidAreaDefaultY; // the blueprint and the subclasses will have their own values
    public boolean collisionOn = false;
    public int actionLockCounter = 0; // USUALLY FOR NPC, MAKE THEM LOCK INTO A IMAGE FOR A SPECIFIC AMOUNT OF FRAMES
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = player, 1 = npc, 2 = monster

    // CHARACTER STATUS
    public int maxLife;
    public int curLife;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }


    public void setAction() {

    }
    public void speak() {

        if(dialogues[dialogueIndex] == null) { // if there's no more text, go back to first dialogue to prevent "NullPointerException"
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }


    }
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this); // if finds wall collision = true
        gp.cChecker.checkObject(this, false); // if finds specific solid object, collision = true
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this); // if hits player, set collision = true

        if(this.type == 2 && contactPlayer == true) {
            if(gp.player.invincible == false) {
                // we can give damage
                gp.player.curLife -= 1;
                gp.player.invincible = true;
            }
        }

        // IF COLLISON IS FALSE, PLAYER CAN MOVE
        if(collisionOn == false) {
            switch(direction) {
                case "up":
                    if(gp.npc[0].worldY >= 2880) { // cannot go past row 60 aka y = 60
                        worldY -= speed;
                    }
                    break;
                case "down":
                    if(gp.npc[0].worldY <= 3408) { // cannot go past row 71 aka y = 71
                        worldY += speed;
                    }
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }

        }

        spriteCounter++;
        if(spriteCounter > 12) { // this means Entity's image changes every 12 frames
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; // find out its screenX and Y and if its in the camera frame then draw it
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch(direction) { // based on this direction we will pick an image from below
                case "up":
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            // COLLISION VISUALS
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }

    }

    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream( imagePath +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
