package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerExitStatusProgram;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerExitStatusProgram}
 */
public class RenderTriggerExitStatusProgram implements RenderTrigger{
    private TriggerInterface trigger = null;
    @Override
    public void render(VBox parent) {
        // Elementi per la selezione del file
        VBox box= new VBox();
        box.setId("vbox-file");
        TextField choosedFile= new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a program");
        Button fileButton= new Button("Choose file");
        fileButton.setOnAction( (ActionEvent actionEvent) -> {
            FileChooser fc= new FileChooser();
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text documents (*.txt)", "*txt"),
                    new FileChooser.ExtensionFilter("Jar file (*.jar)", "*.jar")
            );
            File file = new FileChooser().showOpenDialog(null);
            String filePath= (file==null) ? "" : file.getPath();
            choosedFile.setText(filePath);
        });

        // Elementi per la scelta del valore intero di confronto
        TextField valueField= new TextField();
        valueField.setPromptText("Number to compare with the exit status");
        valueField.textProperty().addListener( (observable, oldValue, newValue) -> {
            if ( !newValue.matches("\\d*") )
                valueField.setText(newValue.replaceAll("\\D", ""));
        });
        // Elementi per gli argomenti da linea di comando
        TextArea argsField= new TextArea();
        argsField.setPromptText("Optional args...");

        box.getChildren().addAll(choosedFile, fileButton);



        // Pulsante di aggiunta
        Button addTriggerButton= new Button("Add Trigger");
        addTriggerButton.setOnAction( (ActionEvent actionEvent) -> {
            File program= new File( choosedFile.getText() );
            int valueToCompare= Integer.parseInt( valueField.getText() );
            String args[]= argsField.getText().split("\\s+");
            trigger= new TriggerExitStatusProgram(program, valueToCompare, args);
            ((Stage) addTriggerButton.getScene().getWindow()).close();
        });

        // Binding con addTriggerButton
        addTriggerButton.disableProperty().bind(Bindings.or( choosedFile.textProperty().isEmpty(), valueField.textProperty().isEmpty() ));
        // Aggiunta nodi a parent
        parent.getChildren().addAll(box, argsField,valueField, addTriggerButton);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
