package main;

import object.OBJ_Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    //BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageTime = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0; // arrow pointer of selection in main menu
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    public int imgSpriteCounter = 0;
    public int imgSpriteNum = 1;

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
       // OBJ_Key key = new OBJ_Key(gp);
       // keyImage = key.image;
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

        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

    }

    public void drawTitleScreen(){
        if(titleScreenState == 0) { // MAIN MENU
            BufferedImage menuImage = null;
            try{
                menuImage = ImageIO.read(getClass().getResourceAsStream(  "/titleScreenImage/Title Screen.png"));

            }catch (IOException e){
                e.printStackTrace();
            }

            // MAIN MENU, TITLE AND FONT
            g2.drawImage(menuImage,0, 0, gp.screenWidth, gp.screenHeight, null);
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

            String text2 = "Massacre";
            int x2 = getXforCenteredText(text2);
            int y2 = gp.tileSize*4;

            // SHADOW
            g2.setColor(Color.darkGray);
            g2.drawString(text2, x2+3, y2+3);
            // MAIN COLOR
            g2.setColor(Color.red);
            g2.drawString(text2, x2, y2);

//            // CHARACTER IMAGE
//            x = gp.screenWidth/2 - (gp.tileSize*2)/2; // makes sure sprite always is drawn in the middle, despite its size change
//            y += gp.tileSize*2;
//            g2.drawImage(gp.player.down1, x, y+20, gp.tileSize*2, gp.tileSize*2, null);

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
//                g2.drawImage(gp.player.down1, x*10, y, gp.tileSize*6,gp.tileSize*6, null); // DEFAULT POSITION
                g2.drawString(">", x, y);
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
            }

            text = "Shipley";
            x = gp.tileSize;
            y += gp.tileSize+10;
            g2.drawString(text, x+gp.tileSize, y);
            if(commandNum == 2) {
                g2.drawString(">", x, y);
            }

            text = "Back";
            x = gp.tileSize;
            y += gp.tileSize*5;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x-gp.tileSize, y);
            }

        }

    }

    public void drawSpriteVariation(int spriteNum) {
        int x = gp.tileSize;
        int y = gp.tileSize*4;

        switch(spriteNum) {
            case 1:
                g2.drawImage(gp.player.down1, x*9, y, gp.tileSize*6,gp.tileSize*6, null);
                break;
            case 2:
                g2.drawImage(gp.player.down2, x*9, y, gp.tileSize*6, gp.tileSize*6, null);
        }
    }


    public void drawPauseScreen() {

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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // setStroke(new BasicStroke(int)) defines the width of outlines of graphics which are rendered with a Graphics 2D
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public int getXforCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
