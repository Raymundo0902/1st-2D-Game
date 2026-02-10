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

        tile = new Tile[100]; // we gonna create 10 kinds of tiles, water tile, grass tile, etc. If needed more, increase more indicies (size)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // will put all the numbers in the map01.txt in this mapTileNum array basically saying 16colx12row

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

            // PLACEHOLDERS, JUST SO WE CAN AVOID USING 1 DIGIT NUMS ON MAP SO ITS NOT UNBALANCED
            setup(0, "darkgrass1", false); // change to index 10
            setup(1, "darkgrass1", false); // change to index 10
            setup(2, "darkgrass1", false); // change to index 10
            setup(3, "darkgrass1", false); // change to index 10
            setup(4, "darkgrass1", false); // change to index 10
            setup(5, "darkgrass1", false); // change to index 10
            setup(6, "darkgrass1", false); // change to index 10
            setup(7, "darkgrass1", false); // change to index 10
            setup(8, "darkgrass1", false); // change to index 10
            setup(9, "darkgrass1", false); // change to index 10

            // MINUS 10 OUT OF ALL TO RETURN THE ORIGINAL INDEX OF SINGLE DIGIT
            setup(10, "darkgrass1", false); // change to index 10
            setup(11, "topMainWall", true); // change to index 11
            setup(12, "water", true); // change to index 12
            setup(13, "earth", false); // change to index 13
            setup(14, "darkTree", true); // change to index 14
            setup(15, "gravelRoad1", false); // change to index 15
            setup(16, "48x48ConcreteTile", false); // change to index 16 // col 47-48 one of those are having issues for this tile index. gives AWT-EventQueue-0 Exception
            setup(17, "48x48DarkConcreteTile", false); // change to index 17
            setup(18, "leftMainWall", true); // change to index 18
            setup(19, "rightMainWall", true); // change to index 19
            setup(20, "bottomMainWall", true); // change to index 20
            setup(21, "verticalTwoWalls", true); // change to index 21
            setup(22, "horizontalTwoWalls", true); // change to index 22
            setup(23, "bLeftCornerWall", true); // change to index 23
            setup(24, "bRightCornerWall", true); // change to index 24
            setup(25, "tLeftCornerWall", true); // change to index 25
            setup(26, "tRightCornerWall", true); // change to index 26
            setup(27, "bLeftCornerWall2", true); // change to index 27
            setup(28, "tRightCornerWall2", true); // change to index 28
            setup(29, "LHorizontalTwoWalls2", true); // change to index 29
            setup(30, "RHorizontalTwoWalls2", true); // change to index 30
            setup(31, "rDoubleCornerWall", true); // change to index 31
            setup(32, "grayWall", true); // change to index 32
            setup(33, "helpMeWall", true); // change to index 33
            setup(34, "wall", true); // change to index 34
            setup(35, "darkgrass2", false); // change to index 34
            setup(36, "leftGravelRoad", false); // change to index 34
            setup(37, "rightGravelRoad", false); // change to index 34
            setup(38, "topGravelRoad", false); // change to index 34
            setup(39, "bottomGravelRoad", false); // change to index 34
            setup(40, "tRightGravelRoad", false); // change to index 34
            setup(41, "bLeftCornerGravelRoad", false);
            setup(42, "bRightCornerGravelRoad", false);
            setup(43, "tLeftCornerGravelRoad", false);
            setup(44, "tRightCornerGravelRoad", false);
//            setup(45, "litFireplace", true);
            setup(46, "deadNPC", false);
            setup(47, "topWater", true);
            setup(48, "bottomWater", true);
            setup(49, "bLeftWaterCorner", true);
            setup(50, "bRightWaterCorner", true);
            setup(51, "leftWater", true);
            setup(52, "rightWater", true);
            setup(53, "tLeftWaterCorner", true);
            setup(54, "tRightWaterCorner", true);
            setup(55, "bRightGravelRoad", false);
            setup(56, "LBridge", false);
            setup(57, "RBridge", false);
            setup(58, "bridge", false);
            setup(59, "bLeftGravelRoad", false);
            setup(60, "tLeftGravelRoad", false);
            setup(61, "sand1", false);
            setup(62, "sandShoreRight", true);
            setup(63, "tLeftWaterCorner2", true);
            setup(64, "tRightWaterCorner2", true);
            setup(65, "bLeftWaterCorner2", true);
            setup(66, "bRightWaterCorner2", true);
            setup(67, "tRightGrassToSandWaterCorner", true);
            setup(68, "tRightGrassToSandCorner", false);
            setup(69, "bRightGrassToSandCorner", false);
            setup(70, "sandToGrassRight", false);
            setup(71, "bRightGrassToSandCorner2", false);
            setup(72, "tRightGrassToSandCorner2", false);
            setup(73, "bRightGrassToSandWaterCorner", true);
            setup(74, "water2", true);
            setup(75, "bridge2", false);
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
