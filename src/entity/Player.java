package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    KeyHandler keyH; // object reference variable
    public final int screenX; // screenX and Y indicate where we draw player on the screen and never change since its final. player always will be in the center of the camera.
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;


    public Player(GamePanel gp, KeyHandler keyH) { // SAME AS (gamePanel Reference, keyH Reference)

        super(gp); // calling the constructor of the superclass of this class -- and passing this gp.
        this.keyH = keyH; // now points to the same KeyHandler object

        screenX = gp.screenWidth/2 - (gp.tileSize/2); // these two return the halfway point of the screen. subtract a half tile length from both screenX and screenY
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(); // values below are what parts of the character will be solid
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x; // reason we create solidAreaDefaultX,Y is so we can recall the default values of solidArea.x and y because we will change solidArea.x and y later.
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 44; // not where we draw on screen this is players starting position on world map.
        worldY = gp.tileSize * 60;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {

        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
    }


    public void update() { // this method gets called 60x per second
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) { // without this, player will move without stopping

            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECK TILE COLLISON
            collisionOn = false;
            gp.cChecker.checkTile(this); // since player class is child to its parent class "Entity" checkTile receives this player class as an entity

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);


            // IF COLLISON IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false) {

                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
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
            if(spriteCounter > 12) { // this means player image changes every 7 frames
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
        else {
            standCounter++; // this starts to increment by one once theres no wasd or arrow key detection.

            if(standCounter == 20) { // standCounter and this if statement helps stop awkward reset when its making the sprite switchover animation--
                spriteNum = 1;      //  looks natural resetting back to normal position. gives it 19 frames to stay on the animation before resetting back to normal sprite state
                standCounter = 0;
            }
        }

    }

    public void pickUpObject(int i) {

        if(i != 999) { // if this index is 999 then that means we didn't touch any object. Otherwise, then we did touch an object. the reason for 999 is to make sure its not used by the object array's index

            String objectName = gp.obj[i].name; // refers to the index's string name from each obj subclass

            switch(objectName) { // objectName is the one being evaluated at which must be one of the following below. e.g. Key, Door, Chest, Boots, etc
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(4);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened door!");
                    }
                    else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Door2": // horizontal door
                    if(gp.obj[i].worldX == gp.obj[5].worldX && gp.obj[i].worldY == gp.obj[5].worldY) {
                        gp.obj[i].collision = false; // set to false since starting door must be open since no keys available
                    }
                    else if(hasKey > 0) {
                        gp.playSE(4);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened door!");
                    }
                    else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots": // makes player faster
                    gp.playSE(3);
                    gp.obj[i] = null;
                    speed += 1;
                    gp.ui.showMessage("Speed!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(2);
                    break;
            }
        }
    }

    public void interactNPC(int i) {
        if(i != 999) { // from the method that has the default index val, it only will change from 999 if collision was detected - NPC to Player

            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
    }



    public void draw(Graphics2D g2) {

//        g2.setColor(Color.red); // Sets a color to use for drawing objects
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize); // Draw a rectangle and fills it with the specified color.

        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY,null);
        // COLLISION HITBOX VISUAL (NOT NEEDED)
        //g2.setColor(Color.red);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

    }
}
