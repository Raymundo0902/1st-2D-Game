package entity;

import main.GamePanel;

public class Projectile extends Entity{

    public int showTime = 45; // HOW LONG A PROJECTILE IS DISPLAYED ON SCREEN

    public Projectile(GamePanel gp) {
        super(gp);

    }

    public void setInfo(int worldX, int worldY, String direction) { // THIS IS ONLY CALLED FROM THE ROCKS FROM PROJECTILE LIST NOT THE ROCKS FROM obj[]. THOSE ROCKS FROM NORMAL ARRAY JUST
                                                                    // CALL THE UPDATE AND DRAW METHOD UNTIL THEY'RE PICKED UP. ALL ROCKS ARE FALSE BUT WHEN U PRESS F THE PICKED UP ROCKS WILL BE ALIVE
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        alive = true;
    }

    public void update() { // CALLED 60X A SECOND , ADD AN IF STATEMENT WHERE THIS ONLY GOES THROUGH WHEN ROCK IS PICKED UP

        if(alive == true) { // MAKES ALL ROCKS MOVE, FIX IT SO ONLY THE PICKED UP ONE MOVES

            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;

                case "left":

                    worldX -= speed; // HORIZONTAL MOTION - ALWAYS HAPPENS
                    // VERTICAL ARC
                    if (showTime > 30) worldY -= 2; // STRONG UPWARD
                    else if (showTime > 25) worldY -= 1; // SLOWER UPWARD
                    else if (showTime > 20) worldY += 1; // SLOWER FALLING
                    else worldY += 2; // FALL FASTER
                    break;

                case "right":

                    worldX += speed;

                    if (showTime > 30) worldY -= 2;
                    else if (showTime > 25) worldY -= 1;
                    else if (showTime > 20) worldY += 1;
                    else worldY += 2;
                    break;
            }

            showTime--; // SINCE UPDATE GETS CALLED 60X PER SECOND, IT WILL DISAPPEAR ABOUT 1.2 SECONDS
            if (showTime <= 0) {
                alive = false;
                System.out.println("STOP SHOWING ROCK!\t");
                showTime = 45;
            }

            spriteCounter++;
            if (spriteCounter > 8) {

                if (spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 1;
                spriteCounter = 0;
            }
        }

        else { // STATIC UPDATE ON LOCATION WHEN NOT PICKED UP


        }


    }



}
