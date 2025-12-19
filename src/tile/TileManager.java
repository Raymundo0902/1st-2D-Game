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
    public Tile[] tile;
    public int mapTileNum[][]; // 2d array

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[20]; // we gonna create 10 kinds of tiles, water tile, grass tile, etc. If needed more, increase more indicies (size)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // will put all the numbers in the map01.txt in this mapTileNum array basically saying 16colx12row

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/darkgrass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/topMainWall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/darkTree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/48x48ConcreteTile.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/48x48DarkConcreteTile.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftMainWall.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightMainWall.png"));
            tile[9].collision = true;

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bottomMainWall.png"));
            tile[10].collision = true;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/verticalTwoWalls.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/horizontalTwoWalls.png"));
            tile[12].collision = true;

            // CORNERS
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bLeftCornerWall.png"));
            tile[13].collision = true;

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bRightCornerWall.png"));
            tile[14].collision = true;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tLeftCornerWall.png"));
            tile[15].collision = true;

            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tRightCornerWall.png"));
            tile[16].collision = true;

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bLeftCornerWall2.png"));
            tile[17].collision = true;

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tRightCornerWall2.png"));
            tile[18].collision = true;

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try{
            InputStream is = getClass().getResourceAsStream(filePath); // loads the txt file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // prepared to read and will read one line of text when .readLine() is called

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) { // becomes false only because of row. Row keeps getting updated and never set back to 0.

                String line = br.readLine(); // .readLine() reads one entire line of text and puts it in the String line EX: "1 0 0 0 1 0 1"

                while(col < gp.maxWorldCol) { // "while you haven't reached the end of this row"

                    String numbers[] = line.split(" "); // .split(" ") = EX: string line = "1 0 0 1" so then here it becomes array of strings like from numbers[0] = "1" ... numbers[3] = "1"... IT KEEPS GETTING UPDATED IN INNER LOOP BUT ITS LIKE NOTHING HAPPENED SINCE WE'RE STILL AT THE FIRST LINE OF TEXT.

                    int num = Integer.parseInt(numbers[col]); // use col as an index for numbers[] array. we are changing this string to integer here so we can use them as a number. this happens once each iteration of the inner loop

                    mapTileNum[col][row] = num; // stores num one by one by each iteration of the inner loop
                    col++; // move to next tile in this row
                }
                if(col == gp.maxWorldCol) { // once col == 16, set it back to 0 aka to the beginning of tile map, then move on to next row aka drop down 1 and repeat.
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e) {

        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { // maxWorldCol = 50 and maxWorldRow = 50

            int tileNum = mapTileNum[worldCol][worldRow];

            // WORLD POSITION
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            // SCREEN POSITION
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&    // ONLY DRAW TILES THAT ARE CLOSE ENOUGH TO THE PLAYER ( inside the camera) TO APPEAR ON SCREEN. NOT REQUIRED BUT RECOMMENDED FOR BETTER GAME PERFORMANCE
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // CAMERA DOESNT HAVE A x or y WORLD POSITION WHICH IS WHY
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
