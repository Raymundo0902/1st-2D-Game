package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity{

    GamePanel gp; // reference field
    KeyHandler keyH; // reference field

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp; // composition
        this.keyH = keyH; // composition

        setDefaultValues();
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

        g2.fillRect(x, y, gp.tileSize, gp.tileSize); // Draw a rectangle and fills it with the specified color.
    }
}
