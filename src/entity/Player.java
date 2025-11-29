package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {

        if(keyH.upPressed == true) {
            y -= speed;
        }
        else if(keyH.downPressed == true) {
            y += speed;
        }
        else if(keyH.leftPressed == true) {
            x -= speed;
        }
        else if(keyH.rightPressed == true) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.red); // Sets a color to use for drawing objects

        g2.fillRect(playerX, playerY, tileSize, tileSize); // Draw a rectangle and fills it with the specified color.
    }
}
