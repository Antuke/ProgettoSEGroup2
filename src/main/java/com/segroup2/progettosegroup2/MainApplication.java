package com.segroup2.progettosegroup2;

import com.segroup2.progettosegroup2.Managers.RulesManager;
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
        stage.show();
        stage.setOnCloseRequest(e ->  RulesManager.getInstance().save()
        );
        /*Prima di chiudere l'applicazione salvo (necessario per salvare la variabile isFired delle regole*/
        /*In alternativa si può pensare di salvare una regola ogni volta che la variabile isFired è eseguita*/
        /*
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                StoreService ss = new StoreService(RulesManager.getInstance().getRules(),RulesManager.savePath);
                ss.start();
                ss.setOnSucceeded(e->{
                    System.out.println("Salvato con successo");
                    System.exit(0);
                });

                ss.setOnFailed(e->{
                    System.out.println("Errori durante il procedimento di salvataggio automatico");
                    System.exit(1);
                });
            }
        });
        */
    }

    public static void main(String[] args) {
        launch();
    }
}