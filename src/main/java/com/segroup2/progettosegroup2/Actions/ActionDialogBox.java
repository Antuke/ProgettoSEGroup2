package com.segroup2.progettosegroup2.Actions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ActionDialogBox extends Application implements ActionInterface {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ActionDialogBox.class.getResource("/com/segroup2/progettosegroup2/dialog-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("DialogBoxMessage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public boolean execute() {
        launch();
        return true;
    }
}

