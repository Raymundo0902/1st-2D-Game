package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/fanfare.wav");
        soundURL[3] = getClass().getResource("/sound/powerup.wav");
        soundURL[4] = getClass().getResource("/sound/unlock.wav");
        soundURL[5] = getClass().getResource("/sound/horrorAmbience.wav"); // make this for horror sound
        soundURL[6] = getClass().getResource("/sound/nightAmbience.wav");
        soundURL[7] = getClass().getResource("/sound/arcadeSelect.wav");
        soundURL[8] = getClass().getResource("/sound/mainGameSong.wav");
        soundURL[9] = getClass().getResource("/sound/rakeSwing.wav");
        soundURL[10] = getClass().getResource("/sound/grassCut.wav");
        soundURL[11] = getClass().getResource("/sound/receiveDamage.wav");
        soundURL[12] = getClass().getResource("/sound/dialogueBox.wav");
        soundURL[13] = getClass().getResource("/sound/cursorMovement.wav");
        soundURL[14] = getClass().getResource("/sound/cursorSelect.wav");
        soundURL[15] = getClass().getResource("/sound/pickupRock.wav");
    }

    public void setFile(int i) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
        }
    }
    public void play () {

        clip.start();
    }
    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {

        clip.stop();
    }
}
