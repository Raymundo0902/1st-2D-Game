package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // lets the window properly close when user clicks the "x" button
        window.setResizable(true); // false = cannot resize the window. stays one size
        window.setTitle("Pinewood Camp Stalker");

        GamePanel gamePanel = new GamePanel(); // GamePanel object is created on the heap
        window.add(gamePanel);

        window.pack(); // causes the window to be sized to fit the preferred size and layouts of its subcomponents (GamePanel)

        window.setLocationRelativeTo(null); // doesn't specify location of window. will be centered
        window.setVisible(true); // see the window

        gamePanel.setupGame(); // put this method here so it can start before the game thread starts
        gamePanel.startGameThread();

    }

}