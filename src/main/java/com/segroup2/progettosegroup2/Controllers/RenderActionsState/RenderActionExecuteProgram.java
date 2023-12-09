package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionExecuteProgram;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionExecuteProgram implements RenderAction{
    private ActionInterface action;

    @Override
    public void render(VBox parent) {
        // Elementi per la selezione del file
        HBox box= new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        TextField choosedFile= new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a program");
        Button fileButton= new Button("Choose file");
        fileButton.setOnAction( (ActionEvent actionEvent) -> {
            FileChooser fc= new FileChooser();
            File file = new FileChooser().showOpenDialog(null);
            String filePath= (file==null) ? "" : file.getPath();
            choosedFile.setText(filePath);
        });
        box.getChildren().addAll(choosedFile, fileButton);

        // Elementi per gli argomenti da linea di comando
        HBox boxLineArgs= new HBox();
        boxLineArgs.setAlignment(Pos.CENTER);
        TextField argsField= new TextField();
        argsField.setPromptText("Optional args...");
        boxLineArgs.getChildren().add(argsField);

        // Pulsante di aggiunta
        Button addTriggerButton= new Button("Add Trigger");
        addTriggerButton.setOnAction( (ActionEvent actionEvent) -> {
            File program= new File( choosedFile.getText() );
            String args[]= argsField.getText().split("\\s+");
            action= new ActionExecuteProgram(program, args);
            ((Stage) addTriggerButton.getScene().getWindow()).close();
        });

        // Binding con addTriggerButton
        addTriggerButton.disableProperty().bind( choosedFile.textProperty().isEmpty() );
        // Aggiunta nodi a parent
        parent.getChildren().addAll(box, boxLineArgs, addTriggerButton);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
