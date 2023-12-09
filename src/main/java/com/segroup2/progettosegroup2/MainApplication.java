package com.segroup2.progettosegroup2;

import com.segroup2.progettosegroup2.Managers.PersistanceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 403);
        stage.setTitle("Simple IFTT by gruppo2 Unisa");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e -> new PersistanceManager().save());
    }

    public static void main(String[] args) {
        launch();
    }
}