package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // must add the key: typed, pressed and released methods when implementing KeyListener

    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, enterPressed;
    // DEBUG
    boolean checkDrawTime = false;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { // don't use this method but for the bottom two we will.
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode(); // returns integer KeyCode associated with the key in this event. (returns num of the key that was pressed) google the cheat sheet if you need help-- for ex: A is VK_A

        // TITLE STATE
        if(gp.gameState == gp.titleState) { // check current titleState substate inside this if statement

            if(gp.ui.titleScreenState == 0) { // main menu

                if(code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) { // keeps arrow from disappearing from select menu
                        gp.ui.commandNum = 2;
                    }
                }

                if(code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }

                if(code == KeyEvent.VK_ENTER) {
                    // NEW GAME
                    if(gp.ui.commandNum == 0) {
                        gp.playSE(7); // SOUND EFFECT WHEN PRESSING ENTER
                        gp.ui.titleScreenState = 1;
                    }
                    // LOAD GAME
                    if(gp.ui.commandNum == 1) {
                        gp.playSE(7); // SOUND EFFECT WHEN PRESSING ENTER

                    }
                    // QUIT
                    if(gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }

            }

            else if(gp.ui.titleScreenState == 1) { // character pick menu
                if(code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) { // keeps arrow from disappearing from select menu
                        gp.ui.commandNum = 2;
                    }
                }

                if(code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }

                if(code == KeyEvent.VK_ENTER) {
                    // SELECT SALLY
                    gp.playSE(7); // SOUND EFFECT WHEN PRESSING ENTER
                    if (gp.ui.commandNum == 0) {
                        System.out.println("Do specific stuff for Sally");
                        gp.gameState = gp.playState;
                    }
                    // SELECT CHAD
                    if (gp.ui.commandNum == 1) {

                        System.out.println("Do specific stuff for Chad");
                        gp.gameState = gp.playState;
                    }

                    // GO BACK TO MAIN SCREEN
                    if (gp.ui.commandNum == 2) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0; // makes sure the cursor goes back to default pointing at "New Game"

                    }
                }
            }

        }

        // PLAY STATE
        if(gp.gameState == gp.playState) {

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if(code == KeyEvent.VK_SHIFT) { // SPRINTING
                shiftPressed = true;
            }
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;

            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            // DEBUG
            if(code == KeyEvent.VK_T) {
                if(checkDrawTime == false) {
                    checkDrawTime = true;
                }
                else if(checkDrawTime == true) {
                    checkDrawTime = false;
                }
            }
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState) {

            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) { // the boolean variables are set to false because it's telling the computer that no key is being detected thus is the goal of the keyReleased method.

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }

    }
}
