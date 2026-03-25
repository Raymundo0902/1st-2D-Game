package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lamp {

    BufferedImage lampFilter;
    GamePanel gp;
    int circleSize;
    int worldX, worldY;
    int screenX;
    int screenY;
    float[] fraction = new float[12];
    Color[] color = new Color[12];

    public Lamp(GamePanel gp, int circleSize) {
        this.gp = gp;
        this.circleSize = circleSize;
        this.worldX = 46 * gp.tileSize;
        this.worldY = 59 * gp.tileSize;

        // Just create the blank canvas once
        lampFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);

        color[0] = new Color(1f,0.77f,0.56f,0.25f);
        color[1] = new Color(1f,0.77f,0.56f,0.25f);
        color[2] = new Color(1f,0.77f,0.56f,0.25f);
        color[3] = new Color(1f,0.77f,0.56f,0.25f);
        color[4] = new Color(1f,0.77f,0.56f, 0.25f);
        color[5] = new Color(1f,0.77f,0.56f,0.25f);
        color[6] = new Color(1f,0.77f,0.56f,0.25f);
        color[7] = new Color(1f,0.77f,0.56f,0.25f);
        color[8] = new Color(1f,0.77f,0.56f,0.25f);
        color[9] = new Color(0,0,0,0.2f);
        color[10] = new Color(0,0,0,0.2f);
        color[11] = new Color(0,0,0,0.2f);


        // 0f means middle, 1f means the edge of circle
        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

    }

    public void update() {

    }

    public void draw(Graphics2D g2) {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Clear and redraw the filter every frame
            Graphics2D gFilter = (Graphics2D) lampFilter.getGraphics();

            // Reset the image (make it black/transparent again)
            gFilter.setComposite(AlphaComposite.Clear);
            gFilter.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            gFilter.setComposite(AlphaComposite.SrcOver);

            // Center the gradient on the lamp's CURRENT screen position
            int centerX = screenX + gp.tileSize/2;
            int centerY = screenY + gp.tileSize/2;

            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, circleSize/2, fraction, color);
            gFilter.setPaint(gPaint);
            gFilter.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            gFilter.dispose();

            g2.drawImage(lampFilter, 0, 0, null);

        }
    }

}
