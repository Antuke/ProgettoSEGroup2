package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionExecuteProgram;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Launcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionExecuteProgram implements RenderAction{
    private ActionInterface action;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();

    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        // Elementi per la selezione del file
        VBox box= new VBox();
        box.setId("vbox-file");
        TextField choosedFile= new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a program");
        Button fileButton= new Button("Choose file");
        fileButton.setOnAction( (ActionEvent actionEvent) -> {
            File file = new FileChooser().showOpenDialog(null);
            String filePath= (file==null) ? "" : file.getPath();
            choosedFile.setText(filePath);
        });
        box.getChildren().addAll(choosedFile, fileButton);

        // Elementi per gli argomenti da linea di comando
        TextArea argsField= new TextArea();
        argsField.setPromptText("Optional args...");

        // Pulsante di aggiunta
        Button addTriggerButton= new Button("Add Trigger");
        addTriggerButton.setId("pref-width");
        addTriggerButton.setOnAction( (ActionEvent actionEvent) -> {
            File program= new File( choosedFile.getText() );
            String args[]= argsField.getText().split("\\s+");
            action= new ActionExecuteProgram(program, args);
            ((Stage) addTriggerButton.getScene().getWindow()).close();
        });

        // Binding con addTriggerButton
        addTriggerButton.disableProperty().bind( choosedFile.textProperty().isEmpty() );
        // Aggiunta nodi a parent
        parent.getChildren().addAll(box, argsField, addTriggerButton);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
