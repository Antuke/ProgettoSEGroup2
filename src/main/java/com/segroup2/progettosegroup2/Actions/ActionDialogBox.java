package com.segroup2.progettosegroup2.Actions;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ActionDialogBox extends Application implements ActionInterface {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinWidth(400);
        anchorPane.setMinHeight(250);

        Label lblMessageBody = new Label("Orario del giorno");
        AnchorPane.setTopAnchor(lblMessageBody, 100.0);
        AnchorPane.setLeftAnchor(lblMessageBody, 0.0);
        AnchorPane.setRightAnchor(lblMessageBody, 0.0);
        lblMessageBody.setAlignment(Pos.CENTER);

        Label lblMessageTitle = new Label("Messaggio di Avviso");
        AnchorPane.setTopAnchor(lblMessageTitle, 50.0);
        AnchorPane.setLeftAnchor(lblMessageTitle, 0.0);
        AnchorPane.setRightAnchor(lblMessageTitle, 0.0);
        lblMessageTitle.setAlignment(Pos.CENTER);

        Button btnClose = new Button("Chiudi");
        AnchorPane.setTopAnchor(btnClose, 150.0);
        AnchorPane.setLeftAnchor(btnClose, 0.0);
        AnchorPane.setRightAnchor(btnClose, 0.0);
        btnClose.setPrefWidth(350.0);
        btnClose.setOnMouseClicked(event -> btnCloseClicked());

        anchorPane.getChildren().addAll(lblMessageBody, lblMessageTitle, btnClose);

        Scene scene = new Scene(anchorPane, 400, 250);
        primaryStage.setTitle("DialogBox di Avviso");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void btnCloseClicked() {
        stage.close(); //Invoco la close sullo stage, Platform.exit(); non funziona in quanto chiude anche il programma principale
    }


    //Ovviamente il main è da eliminare, ma, finchè si prevedono modifiche all'interfaccia, lo stesso verrà preservato ai fini di test.
    public static void main(String[] args) {
        launch(args);
    }

    public boolean execute() {
        Platform.runLater(() -> {
            stage = new Stage();
            start(stage);
        });
        return true;
    }


    public String toString() {
        return "Apertura finestra con testo default";
    }
}



