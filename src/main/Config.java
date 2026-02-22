package main;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {

        this.gp = gp;
    }

    public void saveConfig() {

        // BufferedWriter writes what Reader will see in the txt file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Fullscreen
            if(gp.toggleFullScreen == true) {
                bw.write("On");
            }
            if(gp.toggleFullScreen == false) {
                bw.write("Off");
            }
            bw.newLine();

            // Music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SE volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {

        // BufferedReader reads what has been written in the txt file and the game logic decides what to do with that data.
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            // Reads current line, returns as String, moves the internal pointer to the next line.
            String s = br.readLine();

            // Full screen
            if(s.equals("On")) {
                gp.toggleFullScreen = true;
            }
            if(s.equals("Off")) {
                gp.toggleFullScreen = false;
            }

            // Music vol
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s); // Convert string to Integer.

            // SE vol
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s); // Convert string to Integer.

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
