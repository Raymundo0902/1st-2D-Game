package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentHandler {

    GamePanel gp;
    Lighting lighting;

    public EnvironmentHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {

        lighting = new Lighting(gp, 500);
    }

    public void draw(Graphics2D g2) {

        lighting.draw(g2);
    }



}
