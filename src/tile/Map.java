package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{

    GamePanel gp;
    BufferedImage gameMap[];


    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;

        loadMap("/maps/world01.txt"); // only shows the main world map
        createWorldMap();
    }

    public void createWorldMap(){
        System.out.println("CREATE NEW MAP?"); // does run when leaving to main world
        gameMap = new BufferedImage[1];
        int gameMapWidth = gp.tileSize * gp.maxWorldCol;
        int gameMapHeight = gp.tileSize * gp.maxWorldRow;

        for(int i = 0; i < gameMap.length; i++) {
            gameMap[i] = new BufferedImage(gameMapWidth, gameMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)gameMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                int tileNum = mapTileNum[col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;
                g2.drawImage(tile[tileNum].image, x, y, null);

                col++;
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    public void drawMap(Graphics2D g2) {
//        g2.setColor(Color.black);
//        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        // Draw the map

        int width = 500;
        int height = 500;
        int x = gp.screenWidth/2 - width/2;
        int y = gp.screenHeight/2 - height/2;
        g2.drawImage(gameMap[0], x, y, width, height, null);

    }
}
