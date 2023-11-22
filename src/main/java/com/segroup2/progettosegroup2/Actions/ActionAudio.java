package com.segroup2.progettosegroup2.Actions;

import java.io.File;
import java.net.URISyntaxException;

import static java.lang.Thread.sleep;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Allows playing a default audio file
 */
public class ActionAudio implements ActionInterface {

    private String defaultAudioPath = "/com/segroup2/progettosegroup2/Audio/default_audio.wav";

    /**
     * It can throw all MediaException errors. More info on online documentation {@link javafx.scene.media.MediaException.Type}
     * Brief overview of possible errors
     * <ul>
     *  <li>MEDIA_CORRUPTED</li>
     *  <li>MEDIA_INACCESSIBLE</li>
     *  <li>MEDIA_UNAVAILABLE</li>
     *  <li>MEDIA_UNSPECIFIED</li>
     *  <li>MEDIA_UNSUPPORTED</li>
     *  <li>OPERATION_UNSUPPORTED</li>
     *  <li>PLAYBACK_ERROR</li>
     *  <li>PLAYBACK_HALTED</li>
     *  <li>UNKNOWN</li>
     * </ul>
     * @return true if the operation was successful otherwise false
     */

    @Override
    public boolean execute() {
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = new MediaPlayer(new Media(getClass().getResource(defaultAudioPath).toURI().toString()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer.play();

        //Need to wait some time to define whether the play operation was successful or not
        while( mediaPlayer.getStatus().equals(MediaPlayer.Status.UNKNOWN) || mediaPlayer.getStatus().equals(MediaPlayer.Status.READY) ){
            try{
                sleep(10);
            }catch(InterruptedException e) {}
        }

        return( mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING) );
    }

    public String toString(){
        return "Play dell'audio default";
    }
}