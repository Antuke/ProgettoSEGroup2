package com.segroup2.progettosegroup2.Actions;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.segroup2.progettosegroup2.HelloApplication;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Allows playing a default audio file
 */
public class ActionAudio implements ActionInterface {
    @Override
    public boolean execute() {
        CompletableFuture<Boolean> executeResult = new CompletableFuture<>();
        Platform.runLater(()->{
            Stage stage = new Stage();
            try {
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(HelloApplication.class.getResource("Audio/default_audio.wav").toString()));
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setOnEndOfMedia(stage::close);

                Label label = new Label("Playing default audio file Mu_haha");
                Button stopBtn = new Button("Stop");

                stopBtn.setOnAction(e->{
                    mediaPlayer.stop();
                    stage.close();
                });
                VBox vbox = new VBox(label,stopBtn);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);

                Scene scene = new Scene(vbox, 200, 100);
                stage.setTitle("Audio Action");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.showAndWait();

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