package tetris.Helper.lib;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
/**
 *
 * @author https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * EDITED By Remi
 * @see <a href="https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html"> https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html </a>
 *
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */
public enum Sound {
   CHANGE_BLOCK("ressources/Sounds/changeblock.wav"), //Changing block
   MOVE("ressources/Sounds/move.wav"); // move cursor
   
   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   // Constructor to construct each element of the enum with its own sound file.
   Sound(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         
        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
         
         // Get a clip resource.
         clip = (Clip)AudioSystem.getLine(info);
         
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
         
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
   }
   
   // Optional static method to pre-load all the sound files.
   static public void init() {
      values(); // calls the constructor for all the elements
   }
}