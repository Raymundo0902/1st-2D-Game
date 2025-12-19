package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool { // like a toolbox class so whenever you come up with a convienient function you put it in this class.

    public BufferedImage scaledImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); // int width, int height, int imageType
        Graphics2D g2 = scaledImage.createGraphics(); // Creates a Graphics2D which can be used to draw into this BufferedImage
        g2.drawImage(original, width, height,null); // draw tile[0].image into the scaledImage(BufferedImage)that this Graphics2D is linked to
        g2.dispose();

        return scaledImage;
    }

}
