package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][]; // 2d array

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10]; // we gonna create 10 kinds of tiles, water tile, grass tile, etc. If needed more, increase more indicies (size)
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // will put all the numbers in the map01.txt in this mapTileNum array

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try{
            InputStream is = getClass().getResourceAsStream(filePath); //import the txt file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // reads the content of the txt file

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow) { // maxScreenCol and or maxScreenRow is the limit of this loop cause theres no text data beyond that boundary

                String line = br.readLine(); // .readLine() reads a single line of text one by one and puts it in the String line, put them into the String numbers[] array. EX: numbers[0] = 0 numbers[1] = 0 numbers [2] = 1

                while(col < gp.maxScreenCol) {

                    String numbers[] = line.split(" "); // .split(" ") split the string at a space.

                    int num = Integer.parseInt(numbers[col]); // use col as an index for numbers[] array. we are changing this string to integer here so we can use them as a number.

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e) {

        }
    }
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) { // maxScreenCol = 16 and maxScreenRow = 12

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

    }
}
