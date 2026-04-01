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

        solidArea = new Rectangle(); // values below are what parts of the character will be solid
        solidArea.x = -gp.tileSize;
        solidArea.y = -10;
        solidAreaDefaultX = solidArea.x; // reason we create solidAreaDefaultX,Y is so we can recall the default values of solidArea.x and y because we will change solidArea.x and y later.
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize;
        solidArea.height = (gp.tileSize * 2);

        type = TYPE_NPC;
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
        dialogues[4] = "Once you login, you'll select the cabin you're\ndesignated to be at for the summer.";
        dialogueSize++;
        // ENDS HERE AND THEN PLAYER SIGNS IN. GOES BACK TO OFFICER AND GETS KEY -- CLEAR OUT DIALOGUES ARRAY ABOVE AND SET THE BELOW AS THE NEW STUFF.
        // ONLY ONCE PLAYER IS DONE WITH COMPUTER TASKS AND NEEDS TO GET KEYS
        dialogues[5] = "Alright ma'am, you're all set--\nhere's your keys.";
        dialogueSize++;
        dialogues[6] = "And remember, you may not make it out alive....";
        dialogueSize++;
        dialogues[7] = "HAHAHA THE LOOK ON YOUR FACE!\nit's just a joke...";
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
