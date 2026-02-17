package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity{

    KeyHandler keyH; // object reference variable
    public final int screenX; // screenX and Y indicate where we draw player on the screen and never change since its final. player always will be in the center of the camera.
    public final int screenY;
    public int hasKey = 0;
    public int hasRock = 0;
    int standCounter = 0;
    int sprintCounter = 0; // 2 seconds of sprinting till no more stamina
    public BufferedImage rakeUp1, rakeDown1, rakeRight1, rakeLeft1;
    public boolean rakeCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 12;
    public Entity defaultCurrentItem;


    // ITEM ENABLEMENT & INTERACTION - ONLY PLAYER WILL USE SO WE PUT IT HERE INSTEAD OF ITS PARENT
    public boolean rakeSelect = false; // THIS WILL ONLY ALLOW PLAYER TO USE RAKE WHEN IT HAS BEEN SELECTED FROM INVENTORY
    boolean raking = false;
    boolean throwingRock = false;
    public boolean itemDrop = false;

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

        // rakeArea larger values = larger range of rake
        rakeArea.width = 36;
        rakeArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerRakeImage();
        getPlayerThrowImage();
        setItems();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 44; // not where we draw on screen this is players starting position on world map.
        worldY = gp.tileSize * 60;
        speed = 4;
        direction = "down";
        type = TYPE_PLAYER;

        // PLAYER STATUS
        maxLife = 4; // 2 lifes = one full heart
        curLife = maxLife; // players current life
        currentItem = new OBJ_Hands(gp);
        defaultCurrentItem = currentItem;

    }

    // ITEMS THAT SHOULD ALREADY BE IN INVENTORY AT START OF GAME GOES HERE
    public void setItems() {

    }

    public void getPlayerImage() {

        up1 = setup("/girl_player/sally_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/girl_player/sally_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/girl_player/sally_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/girl_player/sally_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/girl_player/sally_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/girl_player/sally_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/girl_player/sally_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/girl_player/sally_right2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerRakeImage() {

        rakeUp1 = setup("/girl_player/sally_rake_up1", gp.tileSize, gp.tileSize*2);
        rakeDown1 = setup("/girl_player/sally_rake_down1", gp.tileSize, gp.tileSize*2);
        rakeLeft1 = setup("/girl_player/sally_rake_left1", gp.tileSize*2, gp.tileSize);
        rakeRight1 = setup("/girl_player/sally_rake_right1", gp.tileSize*2, gp.tileSize);
    }

    public void getPlayerThrowImage() {

        throwLeft1 = setup("/girl_player/throwRockLeft1", gp.tileSize, gp.tileSize);
        throwRight1 = setup("/girl_player/throwRockRight1", gp.tileSize, gp.tileSize);
        throwUp1 = setup("/girl_player/throwRockUp1", gp.tileSize, gp.tileSize);
        throwDown1 = setup("/girl_player/throwRockDown1", gp.tileSize, gp.tileSize);
    }

    public void update() { // this method gets called 60x per second

        if(raking == true) { // bypass the else if key inputs if player is currently raking
            raking();
        }

        // without this, player will move without stopping && enterPressed here is for the purpose of checking npc collision when we just press enter key for dialogue without having to simultaneously move to npc and press enter.
        else if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){

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

            // SPRINTING MECHANICS
            if(keyH.shiftPressed == true) {
                sprintCounter++;
                if(sprintCounter < 180) { // if sprintCounter is less than 3 seconds then sprint
                    speed = 6;
                }
                else if (sprintCounter > 180 && sprintCounter < 360){ // BE TIRED FOR 3 SECONDS
                    speed = 4; // STAYS AT NORMAL SPEED DURING TIRED DURATION
                }
                else if (sprintCounter > 360) { // ONCE PLAYER RESTED FOR 3 SECONDS, START RUNNING
                    sprintCounter = 0;
                }
            }
            else { // IF SHIFT KEY NOT BEING PRESSED PLAYER SHOULD STILL BE RESTING
                if(sprintCounter > 0) { // MAKES SURE SPRINT COUNTER < 0 BECAUSE THAT WOULD BREAK THE SPRINT AND TIRED PERIODS
                    sprintCounter--;
                }
                speed = 4;

            }

            // CHECK TILE COLLISION - collisionOn = false  will be set to true if in the collision methods above detect collision.
            collisionOn = false;
            gp.cChecker.checkTile(this); // since player class is child to its parent class "Entity" checkTile receives this player class as an entity

            // THE VARIABLES THAT HAVE "Index" IN THEIR NAME ARE TO RETRIEVE THE ENTITIES/OBJECT INDEX SO WE CAN USE TO INTERACT, RECEIVE DAMAGE, DO A SPECIFIC EVENT, ETC
            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monIndex);

            // CHECK EVENT COLLISION
            gp.eventH.checkEvent();


            // IF COLLISON IS FALSE, PLAYER CAN MOVE AND WITHOUT THE ENTERPRESSED HERE, PLAYER CAN MOVE WHEN PRESSING ENTER.
            if(collisionOn == false && keyH.enterPressed == false) { // REMOVING ENTERPRESSED HERE REMOVES THE WEIRD HOLD ENTER KEY DOWN BUGGY SCREEN. NOT A PRIORITY THOUGH. POLISH IF YOU WANT LATER

                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }


            if(currentItem.type == TYPE_RAKE) {
                if (keyH.enterPressed == true && rakeCanceled == false) {
                    gp.playSE(9); // swinging rake SE
                    raking = true;
                }
            }

            rakeCanceled = false;

            // RESET IT TO FALSE OR ELSE PLAYER WILL FREEZE BECAUSE IF ENTER WAS PRESSED, THE IF STATEMENT ABOVE WON'T EVER PASS AGAIN CAUSING PLAYER TO FREEZE.
            gp.keyH.enterPressed = false;


            spriteCounter++;
            if(spriteCounter > 12) { // this means player image changes every 7 frames
                if(spriteNum == 1) spriteNum = 2;
                else if(spriteNum == 2) spriteNum = 1;
                spriteCounter = 0;
            }

        }

        else {
            standCounter++; // this starts to increment by one once there's no wasd or arrow key detection.

            if(standCounter == 20) { // standCounter and this if statement helps stop awkward reset when its making the sprite switchover animation--
                spriteNum = 1;      //  looks natural resetting back to normal position. gives it 19 frames to stay on the animation before resetting back to normal sprite state
                standCounter = 0;
            }
        }



        if (gp.keyH.throwPressed == true && itemCooldown == 0 &&
            hasRock > 0 && currentItem.type == TYPE_ROCK) { // START COOLDOWN

            throwingRock = true;
            spriteThrowCounter = 16;
            itemCooldown = itemCooldownMax;
            hasRock--;
            Projectile projectile = new OBJ_Rock(gp);

            projectile.setInfo(worldX, worldY, direction);
            gp.projectileList.add(projectile); // PROJECTILE STORES REFERENCE TO OBJ_ROCK ADDRESS IN HEAP

            inventory.remove(currentItem);
            // RESET BACK TO HANDS TO AVOID THROWING THE OBJECT ROCK THAT WE ALREADY REMOVED FROM INVENTORY
            currentItem = defaultCurrentItem;
        }
        // STOPS YOU FROM SPAMMING THROWS
        if(itemCooldown > 0) itemCooldown--;
        // PREVENTS FROM HOLDING DOWN THE THROW SPRITE ANIMATION WITH F KEY - NEED TO ONLY START DECREASING WHEN F PRESSED
        if(spriteThrowCounter > 0) spriteThrowCounter--;
        else throwingRock = false;


        // this needs to be outside key statement so counter increase even when player isn't moving
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }


    }

    public void itemDrop() {

    }

    public void raking() {

        spriteCounter++;

        if(spriteCounter <= 5) { // peak of raking image (if want more detailed images)
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) { // full raking


            spriteNum = 1; // set to 2 if you have more sprite raking animations

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the rakeArea or else it will need player collision to cut grass even if tip of rake is already colliding with grass.
            switch(direction) {
                case "up": worldY -= rakeArea.height; break; // offset players worldX/Y by the rakeArea
                case "down": worldY += rakeArea.height; break;
                case "left": worldX -= rakeArea.width; break;
                case "right": worldX += rakeArea.width; break;
            }
            // Modify players solidArea to the rakeArea and then check the grass object collision
            solidArea.width = rakeArea.width;
            solidArea.height = rakeArea.height;
            // CHECK GRASS COLLISION WITH THE UPDATED WORLDX, WORLDY AND SOLIDAREA. DEBUG WITH THE CUT GRASS METHOD SO IT CAN WORK
            int objectIndex = gp.cChecker.checkObject(this, true); // left off here
            cutGrass(objectIndex);

            // RESETS PLAYER BACK TO CURRENT COORDINATES OR ELSE PLAYER WILL GO FLYING AROUND
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25) { // reset back to no raking
            spriteNum = 1;
            spriteCounter = 0;
            raking = false;
        }

    }

    public void pickUpObject(int i) {

        int itemIndex;

        if(i != 999) { // if this index is 999 then that means we didn't touch any object. Otherwise, then we did touch an object. the reason for 999 is to make sure its not used by the object array's index

            String objectName = gp.obj[i].name; // refers to the index's string name from each obj subclass

            switch(objectName) { // objectName is the one being evaluated at which must be one of the following below. e.g. Key, Door, Chest, Boots, etc

                case "Rake":
                    gp.playSE(15); // find a better sound like pickup a tool sound effect
                    gp.obj[i] = null;
                    if(inventory.size() != maxInventorySize) {
                        inventory.add(new OBJ_Rake(gp));
                    }
                    break;

                case "Rock":
                    gp.playSE(15);
                    hasRock++;
                    gp.obj[i] = null;
                    if(inventory.size() != maxInventorySize) {
                        inventory.add(new OBJ_Rock(gp));
                    }
                    break;

                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    if(inventory.size() != maxInventorySize) {
                        inventory.add(new OBJ_Key(gp));
                    }
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0 && currentItem.type == TYPE_KEY) { // MUST HAVE SELECTED KEY TO OPEN FROM INVENTORY

                        inventory.remove(currentItem); // GOES THROUGH FOR LOOP AND CHECKS IF THIS REFERENCE POINTS TO SAME OBJECT
                        currentItem.use(this);

                        currentItem = defaultCurrentItem; // SET BACK TO HANDS
                        gp.playSE(4);
                        gp.obj[i] = null; // DISAPPEAR FROM MAP

                        hasKey--; // MAKES IT WHERE I CANNOT OPEN DOORS IF hasKey < 1
                        gp.ui.showMessage("You opened door!");
                        for(int j = 0; j < inventory.size(); j++) {
                            if(inventory.get(j).type == TYPE_KEY) {
                                inventory.remove(j);
                                break; // remove only one key
                            }
                        }
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
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(2);
                    break;
            }
        }
    }

    public void interactNPC(int i) {

        if(gp.keyH.enterPressed == true) {

            if (i != 999) { // from the method that has the default index val, it only will change from 999 if collision was detected - NPC to Player

                rakeCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.playSE(12);
                gp.npc[i].speak();
            }

        }
    }

    public void contactMonster(int i) {

        if (i != 999) {

            if(invincible == false) {
                curLife -= 1;
                invincible = true;
            }
        }
    }

    // DEBUG THIS SO IT WORKS GIVES ILLEGAL EXCEPTION
    public void cutGrass(int i) {

        if (i != 999 && gp.obj[i] instanceof OBJ_TallGrass) {
            if(gp.obj[i].invincible == false) {// put cutting grass functions here, like it'll take 3 attacks from rake to dissapear

                gp.playSE(10); // enter grass cutting sound here
                gp.obj[i].curLife -= 1;
                gp.obj[i].invincible = true;

                if(gp.obj[i].curLife <= 0) {
                    gp.obj[i] = null;
                }
            }
        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot();

        if(itemIndex < inventory.size()) { // NOT SELECTING VACANT SLOTS

            Entity selectedItem = inventory.get(itemIndex); // stores a reference to the current object. could be the reference to the Key, Rake, ... object

            if(selectedItem.type == TYPE_KEY || selectedItem.type == TYPE_ROCK) currentItem = selectedItem;

            if(selectedItem.type == TYPE_RAKE) currentItem = selectedItem;
        }

    }

    public void draw(Graphics2D g2) {

//        g2.setColor(Color.red); // Sets a color to use for drawing objects
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize); // Draw a rectangle and fills it with the specified color.

        BufferedImage image = null;
        // sprite is drawn at top left since shifting player only happens during left or up raking animations. To fix, we offset the draw position.
        // technically the engine still makes the shift but we offset so it looks like theres no visual shifting going on.
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction) { // based on this direction we will pick an image from below
            case "up":
                if(raking == false) {
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                }
                if(raking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1) {image = rakeUp1;}
                }
                if(throwingRock == true) { // USE CONDITION THAT HAS A BOOLEAN VARIABLE THAT DEPENDS ON TIME
                    if(spriteNum == 1) {image = throwUp1;}
                }
                break;
            case "down":
                if(raking == false) {
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                }
                if(raking == true) {
                    if(spriteNum == 1) {image = rakeDown1;}
                }
                if(throwingRock == true) { // USE CONDITION THAT HAS A BOOLEAN VARIABLE THAT DEPENDS ON TIME
                    if(spriteNum == 1) {image = throwDown1;}
                }
                    break;
            case "left":
                if(raking == false) {
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                }
                if(raking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1) {image = rakeLeft1;}
                }
                if(throwingRock == true) { // USE CONDITION THAT HAS A BOOLEAN VARIABLE THAT DEPENDS ON TIME
                    if(spriteNum == 1) {image = throwLeft1;}
                }
                break;
            case "right":
                if(raking == false) {
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                }
                if(raking == true) {
                    if(spriteNum == 1) {image = rakeRight1;}
                }
                if(throwingRock == true) { // USE CONDITION THAT HAS A BOOLEAN VARIABLE THAT DEPENDS ON TIME
                    if(spriteNum == 1) {image = throwRight1;}
                }
                break;
        }

//        // PLAYER'S HEALTH BAR
//        g2.setColor(new Color(255,0,30));
//        g2.fillRect(gp.tileSize, gp.tileSize/2, gp.tileSize*4, 20);

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // makes player look kinda invincible
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        // COLLISION HITBOX VISUAL (DEBUG)
//        g2.setColor(Color.red);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        // INVINCIBLE COUNTER (DEBUG)
//        g2.setFont(new Font("Arial", Font.PLAIN, 25));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible: +"+invincibleCounter, 48, 90);
    }
}
