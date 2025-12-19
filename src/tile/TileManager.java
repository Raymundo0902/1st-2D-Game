package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

        tile = new Tile[30]; // we gonna create 10 kinds of tiles, water tile, grass tile, etc. If needed more, increase more indicies (size)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // will put all the numbers in the map01.txt in this mapTileNum array basically saying 16colx12row

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

            setup(0, "darkgrass", false);
            setup(1, "topMainWall", true);
            setup(2, "water", true);
            setup(3, "earth", false);
            setup(4, "darkTree", true);
            setup(5, "sand", false);
            setup(6, "48x48ConcreteTile", false);
            setup(7, "48x48DarkConcreteTile", false);
            setup(8, "leftMainWall", true);
            setup(9, "rightMainWall", true);
            setup(10, "bottomMainWall", true);
            setup(11, "verticalTwoWalls", true);
            setup(12, "horizontalTwoWalls", true);
            setup(13, "bLeftCornerWall", true);
            setup(14, "bRightCornerWall", true);
            setup(15, "tLeftCornerWall", true);
            setup(16, "tRightCornerWall", true);
            setup(17, "bLeftCornerWall2", true);
            setup(18, "tRightCornerWall2", true);
            setup(19, "LHorizontalTwoWalls2", true);
            setup(20, "RHorizontalTwoWalls2", true);
            setup(21, "rDoubleCornerWall", true);
            setup(22, "grayWall", true);
            setup(23, "helpMeWall", true);
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize); // calls the UTool and get scaled image
            tile[index].collision = collision; // set collision

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

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
