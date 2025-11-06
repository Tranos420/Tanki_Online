package tanki.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    public static void play(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Fisier audio lipsa: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
