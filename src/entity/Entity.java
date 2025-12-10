package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

// THIS CLASS STORES VARIABLES THAT WILL BE USED IN PLAYER, MONSTER AND NPC CLASSES.
public class Entity {

    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // BufferedImage describes an Image with an accessible buffer of image data. (we use this to store our image files)
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea; // create a invisible or abstract rectangle and store data like x,y width and height
    public boolean collisionOn = false;
}
