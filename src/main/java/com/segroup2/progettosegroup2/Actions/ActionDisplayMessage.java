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

public class ActionDisplayMessage implements ActionInterface, Serializable {
    private final String message;

    public ActionDisplayMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.message = message;
    }

    @Override
    public boolean execute() {
        CompletableFuture<Boolean> executeResult = new CompletableFuture<>();
        Platform.runLater(()->{
            Stage stage = new Stage();
            try {
                Label label = new Label(message);
                label.setWrapText(true);
                Button stopBtn = new Button("Close");

                stopBtn.setOnAction(e->{
                    stage.close();
                });
                VBox vbox = new VBox(label,stopBtn);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);
                // Add padding to VBox for better aesthetics
                vbox.setStyle("-fx-padding: 10;");

                Scene scene = new Scene(vbox, 400, 300);
                stage.setTitle("Message Action");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setAlwaysOnTop(true);
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

    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }


    public String toString() {
        return "Display message: " + message;
    }
}
