package com.segroup2.progettosegroup2.Actions;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ActionDialogBox implements ActionInterface , Serializable {

    @Override
    public boolean execute() {
        CompletableFuture<Boolean> executeResult = new CompletableFuture<>();
        Platform.runLater(()->{
            Stage stage = new Stage();
            try {
                Label label = new Label("Promemoria!");
                Button stopBtn = new Button("Stop");

                stopBtn.setOnAction(e->{
                    stage.close();
                });
                VBox vbox = new VBox(label,stopBtn);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);

                Scene scene = new Scene(vbox, 200, 100);
                stage.setTitle("DialogBox Action");
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


    public String toString() {
        return "Apertura finestra con testo default";
    }
}



