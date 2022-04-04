import javax.sound.sampled.*;
import java.io.File;

/**
 * The class that plays the background music or audios.
 */
public class Music {
    /**
     * The pointer of the music.
     */
    Clip clip;

    /**
     * @param source Address of music.
     * @param loop Always repeat music when true, play once when false.
     */
    void play(String source, boolean loop)
    {
        try
        {
            // create a music player
            clip = AudioSystem.getClip();
            // file to playable file
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(source));
            // open it with the music player
            clip.open(audioInput);
            if (loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                clip.start();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Stop playing.
     */
    void close(){
        clip.close();
    }
}