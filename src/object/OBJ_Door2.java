package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door2 extends SuperObject{

    public OBJ_Door2() {

        name = "Door2";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door2.png"));

        }catch(IOException e) {
            e.printStackTrace(); // if try block fails printStackTrace tells us what went on when reading file
        }

        collision = false; // false for now

    }
}
