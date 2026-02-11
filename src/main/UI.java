package main;

import entity.Entity;
import object.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageTime = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0; // arrow pointer of selection in main menu
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    public int imgSpriteCounter = 0;
    public int imgSpriteNum = 1;
    public int playerType; // 1 for sally, 2 for chad

    // INVENTORY DESIGN
    public int slotCol = 0; // indicates the cursors current position on inventory window
    public int slotRow = 0;
    final int MAX_COL = 4;
    final int MAX_ROW = 3;

    // INVENTORY ITEMS
    public boolean isKey = false;
    public boolean isRake = false;

    public UI (GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawInventory();
        }

    }

    public void drawPlayerLife() {

        int x = gp.tileSize;
        int y = gp.tileSize;

        // PLAYER'S HEALTH BAR

        // OUTLINE OF HEALTH BAR
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(x - 2, (y / 2) - 1 , (gp.tileSize * 4) + 4, 23);

        if(gp.player.curLife == 4) {
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(x, y / 2, gp.tileSize * 4, 20);
        }
        if(gp.player.curLife == 3) {

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(x, y / 2, gp.tileSize * 3, 20);

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(x*4, y / 2, gp.tileSize, 20);
        }
        else if(gp.player.curLife == 2) {

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(x, y / 2, gp.tileSize * 2, 20);

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(x*3, y / 2, gp.tileSize*2, 20);
        }
        else if (gp.player.curLife == 1){
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(x, y / 2, gp.tileSize, 20);

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(x*2, y / 2, gp.tileSize*3, 20);
        }
    }

    public void drawTitleScreen(){
        // DRAW MAIN MENU BACKGROUND IMAGE
        BufferedImage menuImage = null;
        try{
            menuImage = ImageIO.read(getClass().getResourceAsStream(  "/titleScreenImage/titleScreen.png"));
            g2.drawImage(menuImage,0, 0, gp.screenWidth, gp.screenHeight, null);


        }catch (IOException e){
            e.printStackTrace();
        }

        if(titleScreenState == 0) { // MAIN MENU

            // TITLE AND FONT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 95F));
            String text = "Pinewood Camp";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*2;

            // SHADOW
            g2.setColor(Color.darkGray);
            g2.drawString(text, x+4, y+4);
            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            String text2 = "Stalker";
            int x2 = getXforCenteredText(text2);
            int y2 = gp.tileSize*4;

            // SHADOW
            g2.setColor(Color.darkGray);
            g2.drawString(text2, x2+3, y2+3);
            // MAIN COLOR
            g2.setColor(Color.red);
            g2.drawString(text2, x2, y2);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.setColor(Color.white);
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*4;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }

        }

        else if(titleScreenState == 1) { // choose character menu
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));

            String text = "Select your Character";
            int x = gp.tileSize/2;
            int y = gp.tileSize*2;
            g2.drawString(text, x ,y);

            // PLAYER SELECTION
            text = "Sally";
            x = gp.tileSize;
            y += gp.tileSize*2;
            g2.drawString(text, x+gp.tileSize, y);
            if(commandNum == 0) { // IF ARROW POINTING AT SALLY, DO THE BELOW:
                g2.drawString(">", x, y);
                playerType = 1;
                imgSpriteCounter++; // SWITCH SPRITE VARIATIONS EVERY 12 FRAMES
                if(imgSpriteCounter > 12) {
                    if(imgSpriteNum == 1) {
                        imgSpriteNum = 2;
                    }
                    else if(imgSpriteNum == 2) {
                        imgSpriteNum = 1;
                    }
                    imgSpriteCounter = 0;
                }
                drawSpriteVariation(imgSpriteNum);

            }

            text = "Chad";
            x = gp.tileSize;
            y += gp.tileSize+10;
            g2.drawString(text, x+gp.tileSize, y);
            if(commandNum == 1) {
                g2.drawString(">", x, y);
                playerType = 2;
                imgSpriteCounter++;
                if(imgSpriteCounter > 12) {
                    if(imgSpriteNum == 1) {
                        imgSpriteNum = 2;
                    }
                    else if(imgSpriteNum == 2) {
                        imgSpriteNum = 1;
                    }
                    imgSpriteCounter = 0;
                }
                drawSpriteVariation(imgSpriteNum);
            }

            text = "Back";
            x = gp.tileSize;
            y += gp.tileSize*5;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }

        }

    }

    public void drawSpriteVariation(int spriteNum) {
        int x = gp.tileSize;
        int y = gp.tileSize*4;

        if(playerType == 1) {

            BufferedImage sallydown1 = null;
            try{
                sallydown1 = ImageIO.read(getClass().getResourceAsStream(  "/girl_player/sally_down1.png"));

            }catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage sallydown2 = null;
            try{
                sallydown2 = ImageIO.read(getClass().getResourceAsStream(  "/girl_player/sally_down2.png"));

            }catch (IOException e){
                e.printStackTrace();
            }

            switch(spriteNum) {
                case 1:
                    g2.drawImage(sallydown1, x*9, y-30, gp.tileSize*7,gp.tileSize*7, null);
                    break;
                case 2:
                    g2.drawImage(sallydown2, x*9, y-30, gp.tileSize*7, gp.tileSize*7, null);
            }
        }
        else if(playerType == 2) {
            switch(spriteNum) {
                case 1:
                    g2.drawImage(gp.player.down1, x*9, y, gp.tileSize*6,gp.tileSize*6, null);
                    break;
                case 2:
                    g2.drawImage(gp.player.down2, x*9, y, gp.tileSize*6, gp.tileSize*6, null);
            }
        }

    }

    public void drawInventory() {

        // FRAME WINDOW
        final int frameX = gp.tileSize * 9;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 4;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOTS
        final int slotXStart = frameX + 20; // from the frameX corner and shift right by 20 pixels
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 4;


        // DRAW PLAYER'S CURRENT ITEMS & SLOT BOX
        for(int i = 0; i < gp.player.inventory.size(); i++) {

            if(i % MAX_COL == 0 && i != 0) { // SINCE 4 IS OUT MAX_COL, ANYTHING PERFECTLY DIVISIBLE BY 4 WITH REMAINDER 0 WILL JUMP TO NEW ROW.
                                            // BETTER PRACTICE THAN JUST COMPARING i TO AN INTEGER IN CASE THERE'S LATER CHANGES. 4, 8, 12, 16 ALL WORKS WITH DIVISION BY 4.
                slotY += slotSize;
                slotX = slotXStart;
            }

            // DRAW SLOT BOX
            Color c = new Color(75, 47, 31);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(8)); // setStroke(new BasicStroke(int)) defines the width of outlines of graphics which are rendered with a Graphics 2D
            g2.fillRoundRect(slotX, slotY, slotSize - 5, slotSize -2, 10, 10);

            // DRAW ITEMS
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;
        }

        // CURSOR
        int cursorX = slotXStart + (slotSize * slotCol); // X AND Y GIVES US THE COORDS OF THE CURSOR BOX
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = (gp.tileSize * 5) + 10;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize *3;
//        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); UNCOMMENT AND DELETE FROM FOR LOOP IF YOU WANT BOX TO ALWAYS APPEAR

        // DRAW DESCRIPTION TEXT
//        g2.setColor(Color.white);
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        String interactItem = " 'G' to drop ";


        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()) {

            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); // BOX ONLY GETS DRAWN WHEN THE CURSOR IS ON AN ITEM

            g2.setColor(Color.white);
            for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) { // .split CONVERTS THE STRING INTO AN ARRAY OF SUBSTRINGS.

                g2.drawString(line, textX, textY);
                textY += 32; // MOVE DOWN TO THE NEXT LINE
            }

            g2.drawString(interactItem, textX, textY);

        }

    }

    public void drawPauseScreen() {

        g2.setColor(new Color(255, 255, 255));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) { // split method turns this string into a temporary array
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public int getItemIndexOnSlot() {
        // GETS CORRECT, CURRENT HOVERED ITEM INDEX. STARTS FROM ZERO.
        return slotCol + (slotRow * MAX_COL);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        // OUTERMOST BORDER
        Color c = new Color(43,26,23);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(8)); // setStroke(new BasicStroke(int)) defines the width of outlines of graphics which are rendered with a Graphics 2D
        g2.drawRoundRect(x, y, width+1, height+1, 30, 30);

        // ACTUAL RECTANGLE
        c = new Color(100,50,27, 240);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30);

        // INSIDE BORDER
        c = new Color(43,26,23);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // setStroke(new BasicStroke(int)) defines the width of outlines of graphics which are rendered with a Graphics 2D
        g2.drawRoundRect(x+10, y+10, width-20, height-20, 25, 25);

    }

    public int getXforCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
