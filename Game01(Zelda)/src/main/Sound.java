package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.applet.Applet;
import java.applet.AudioClip;

import javax.sound.sampled.*;

public class Sound {

    private Clip clip;

    public static final Sound musicBackground = new Sound("music.wav");

    private Sound(String name) {
        try {
            URL url = Sound.class.getResource(name);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            if (clip != null) {
                clip.setFramePosition(0); // Reinicia o som para o início
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        try {
            if (clip != null) {
                clip.setFramePosition(0); // Reinicia o som para o início
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso:
        Sound.musicBackground.loop();
    }
}
