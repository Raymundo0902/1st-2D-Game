package entity;

import main.GamePanel;

public class Projectile extends Entity{

    public int showTime = 30; // HOW LONG A PROJECTILE IS DISPLAYED ON SCREEN
    public boolean isDisplayable = true;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void setInfo(int worldX, int worldY, String direction) { // ADD ANOTHER PARAMETER "Entity user" IF FUTURE CASE OTHER ENTITIES CAN USE PROJECTILES

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
    }

    public void update() {

        switch(direction) {

            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        showTime--; // SINCE UPDATE GETS CALLED 60X PER SECOND, IT WILL DISAPPEAR ABOUT 1.2 SECONDS
        if(showTime <= 0) {
            System.out.println("BEFORE" + isDisplayable);
            isDisplayable = false;
            System.out.println("SHOULD NOT SHOW" + isDisplayable);
        }



        spriteCounter++;
        if(spriteCounter > 8) {

            if(spriteNum == 1) spriteNum = 2;
            else if(spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }


    }

}
