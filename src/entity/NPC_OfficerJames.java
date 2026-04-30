package entity;

import main.GamePanel;

import java.awt.*;

public class NPC_OfficerJames extends Entity{
    public NPC_OfficerJames(GamePanel gp) {
        super(gp);
        direction = "left";
        speed = 2;
        getNPCImage();
        setDialogue();
        name = "james";
        type = TYPE_NPC;

        solidArea = new Rectangle(); // values below are what parts of the character will be solid
        solidArea.x = -50;
        solidArea.y = -10;
        solidAreaDefaultX = solidArea.x; // reason we create solidAreaDefaultX,Y is so we can recall the default values of solidArea.x and y because we will change solidArea.x and y later.
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize;
        solidArea.height = (gp.tileSize * 2);
    }

    public void getNPCImage() {
        left1 = setup("/npc/mainOfficer", gp.tileSize, gp.tileSize + 10);
    }


    public void setDialogue() {
        // Dialogue
        dialogues[0] = "First day here and YOU'RE LATE?!?";
        dialogueSize++;
        dialogues[1] = "I'm just bursting your bubble, calm down lol";
        dialogueSize++;
        dialogues[2] = "Anywho, welcome to Pinewood Camp!";
        dialogueSize++;
        dialogues[3] = "Before giving you the keys to your cabin, sign in to the\ncomputer to the top left of you. Password is.. password..";
        dialogueSize++;
        dialogues[4] = "Once you login, you'll select the cabin available and\ni'll hand you the keys.";
        dialogueSize++;
    }

    @Override
    public void update() {
    }

    public void setAction() {
    }

    public void speak() {
        // DO THIS CHARACTER SPECIFIC STUFF
        super.speak();

    }
}
