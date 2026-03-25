package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentHandler {

    GamePanel gp;
    Lighting lighting;
    Lamp lamp;

    public EnvironmentHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {

//        lighting = new Lighting(gp, 560);
        lamp = new Lamp(gp, 300);
    }

    public void update() {
//        lamp.update();
    }

    public void draw(Graphics2D g2) {

//        lighting.draw(g2);
        lamp.draw(g2);
    }




}
