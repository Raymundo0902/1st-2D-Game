package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile true tile size that will be displayed on screen. public so any other packages can access it
    public final int maxScreenCol = 16; // 16 tiles horizontally
    public final int maxScreenRow = 12; // 12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 86;

    // FPS
    int FPS = 60;


    // "this" as the argument is passing the reference to the exact GamePanel object thats currently running. (NOT THE OBJECT, NOT A COPY, NOT A NEW OBJECT)
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound(); // sound effect
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread; // thread is something you can start/stop. once thread started it keeps the program running

    // ENTITY AND OBJECT
    public Player player = new Player(this,keyH); // passes the gamepanel and keyhandler reference to objects inside the Player class. so Player class can get the things it needs from both classes.
    public SuperObject obj[] = new SuperObject[10]; // can display 10 objects at the same time. EX: if pickup object A then it disappears from screen and another object can fill in that vacant slot
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState; // game state is like a title screen state, play, pause, etc
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel () {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // if true, all drawings from this component will be done in an offscreen painting buffer (smoother visual updates & eliminates flickering) in short, improves rendering performance
        this.addKeyListener(keyH); // this GamePanel will recognize the key input
        this.setFocusable(true); // with this, the GamePanel can be "focused" to receive key input.

    }

    public void setupGame() { // created this method so we can add other setup stuff in the future

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(5); // since we want to play the blue boy adventure music we set the index to 0 because it sends that over to the parameter at Sound class
        stopMusic();
        gameState = playState;
    }


    public void startGameThread() {

        gameThread = new Thread(this); // this = the GamePanel class. So we're passing the GamePanel class to the thread constructor. This is how you instantiate a Thread.
        gameThread.start(); // automatically call run method
    }


    // as long the game loop continues it will continue to call update and then repaint
    @Override
    public void run() { // when we start the gameThread the run method is automatically called. in this run method we will create the game loop which is the core of the game.

        double drawInterval = 1000000000/FPS; // 0.01666 seconds - how many nanoseconds one frame should take
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) { // as-long as gameThread exists, it will repeat the process thats in the while loop

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; // measures how far behind or on time the loop is -- ensures update() and movement are consistent, prevents slow frames from slowing down the entire game world
            timer += (currentTime - lastTime); // add how many nanoseconds passed during each frame.
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint(); // schedule a paint event which eventually calls the paintComponenet() method
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) { // this condition only happens once per second -- happens when the while loop has iterated roughly around 60 times
                //System.out.println("FPS:"+ drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }



    public void update() {
        if(gameState == playState) {
            player.update(); // it's like a nested updates, when this main update method is called it calls the player update method so the player can be updated thus more organized clean code.
            // NPC
            for(int i = 0; i < npc.length; i++ ){
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState) {
            // nothing, no updating player info while paused
        }
    }

    public void paintComponent(Graphics g) { // the "Graphics" is a class that has many functions to draw objects on the screen

        super.paintComponent(g); //whenever you use the paintComponent method on JPanel you need to type this line. super means the parent class of this class in this case parent is JPanel since GamePanel is a subclass of it.
        Graphics2D g2 = (Graphics2D)g; // Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.


        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }


        // TILE
        tileM.draw(g2); // put this above player because if not, background tiles will hide the player character


        // OBJECT. WE USE FOR LOOP BECAUSE IT CAN BECOME A LONG LINE OF CODES FOR JUST OBJECTS IF WE HAVE MANY TO DISPLAY
        for(int i = 0; i < obj.length; i++) { // length is 10 so scan from 0-9 and check if there's any object inside the array
            if(obj[i] != null) { // checks if slot is null or not
                obj[i].draw(g2, this);
            }
        }
        // NPC
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // PLAYER
        player.draw(g2); // when paintComponent called, so it player.draw() which will draw the character. make it more cleaner code


        // UI - SET IT BELOW tiles and player draw methods so it doesn't get covered
        ui.draw(g2);

        // DEBUG
        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400); // shows how much time has passed
            System.out.println("Draw Time: "+ passed);
        }


        g2.dispose(); // Dispose of this graphics context and release any system resources that it is using. Disposes Graphics2D, programing still works without this line but it is good practice to save memory.
    }


    public void playMusic(int i) { // for music we use loop because it is obviously a continuous sound

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) { // for sound effects we dont call loop cause its just a one time occurance

        se.setFile(i);
        se.play();
    }
}

