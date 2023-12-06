package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerExitStatusProgram;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerExitStatusProgram}
 */
public class RenderTriggerExitStatusProgram implements RenderTrigger{
    private TriggerExitStatusProgram trigger = null;
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
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Documenti di testo (*.txt)", "*txt"),
                    new FileChooser.ExtensionFilter("Jar file (*.jar)", "*.jar")
            );
            File file = new FileChooser().showOpenDialog(null);
            String filePath= (file==null) ? "" : file.getPath();
            choosedFile.setText(filePath);
        });
        box.getChildren().addAll(choosedFile, fileButton);

        // Elementi per la scelta del valore intero di confronto
        HBox boxValue= new HBox();
        boxValue.setAlignment(Pos.CENTER);
        TextField valueField= new TextField();
        valueField.setPromptText("Number to compare with the exit status");
        valueField.textProperty().addListener( (observable, oldValue, newValue) -> {
            if ( !newValue.matches("\\d*") )
                valueField.setText(newValue.replaceAll("\\D", ""));
        });
        boxValue.getChildren().add(valueField);

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
            int valueToCompare= Integer.parseInt( valueField.getText() );
            String args[]= argsField.getText().split("\\s+");
            trigger= new TriggerExitStatusProgram(program, valueToCompare, args);
            ((Stage) addTriggerButton.getScene().getWindow()).close();
        });

        // Binding con addTriggerButton
        addTriggerButton.disableProperty().bind(Bindings.or( choosedFile.textProperty().isEmpty(), valueField.textProperty().isEmpty() ));
        // Aggiunta nodi a parent
        parent.getChildren().addAll(box, boxValue, boxLineArgs, addTriggerButton);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
