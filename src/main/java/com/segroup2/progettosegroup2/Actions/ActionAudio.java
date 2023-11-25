package com.segroup2.progettosegroup2.Actions;


import com.segroup2.progettosegroup2.MainApplication;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Allows playing a default audio file
 */
public class ActionAudio implements ActionInterface, Serializable {
    /**
     * La funzione si occupa di avviare il file audio "default_audio.wav" e di visualizzare a video
     * una DialogBox contenente una label che mostra il nome del file audio riprodotto e un pulsante
     * che permette di chiudere la finestra e interrompere la riproduzione dell'audio prima che questo sia
     * terminato o nel caso in cui termina l'audio la finestra viene chiusa automaticamente.
     *
     * @return boolean: True quando l'esecuzione termina con successo, False se la riproduzione del
     * file audio lancia un'eccezione.
     */
    @Override
    public boolean execute() {
        CompletableFuture<Boolean> executeResult = new CompletableFuture<>();
        Platform.runLater(()->{
            Stage stage = new Stage();
            try {
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(MainApplication.class.getResource("Audio/default_audio.wav").toString()));
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setOnEndOfMedia(stage::close);

                Label label = new Label("Playing default audio file default_audio.wav");
                Button stopBtn = new Button("Stop");

                stopBtn.setOnAction(e->{
                    mediaPlayer.stop();
                    stage.close();
                });
                VBox vbox = new VBox(label,stopBtn);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);

                Scene scene = new Scene(vbox, 300, 200);
                stage.setTitle("Audio Action");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            }catch (Exception exception){
                executeResult.complete(false);
            }
            executeResult.complete(true);
        });

        try {
            return executeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(){
        return "Play dell'audio default";
    }
}