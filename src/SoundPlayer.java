import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private Clip clip;

    public void playSound(String soundFilePath) {
        try {
            // Open audio stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath));

            // Get a clip resource
            clip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream
            clip.open(audioInputStream);

            // Play the sound
            clip.start();

            // Optionally, you can wait for the sound to finish
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);

            // Close resources
            clip.close();
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
