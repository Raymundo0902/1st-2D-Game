package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp; // reference field
    KeyHandler keyH; // reference field

    public final int screenX; // screenX and Y indicate where we draw player on the screen and never change since its final. player always will be in the center of the camera.
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp; // composition
        this.keyH = keyH; // composition
        screenX = gp.screenWidth/2 - (gp.tileSize/2); // these two return the halfway point of the screen. subtract a half tile length from both screenX and screenY
        screenY = gp.screenHeight/2 - (gp.tileSize/2);;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23; // not where we draw on screen this is players starting position on world map.
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png")); // revert back to normal player not ayden as its just test for now.
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { // this method gets called 60x per second
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {

            if(keyH.upPressed == true) {
                direction = "up";
                worldY -= speed;
            }
            else if(keyH.downPressed == true) {
                direction = "down";
                worldY += speed;
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
                worldX -= speed;
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;
            if(spriteCounter > 7) { // this means player image changes every 7 frames
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }

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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
    }
}
