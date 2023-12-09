package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerFileSize;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerFileSize}
 */
public class RenderTriggerFileSize implements RenderTrigger{
    private TriggerInterface trigger = null;

    @Override
    public void render(VBox parent) {
        // Elementi per la selezione del fileBtn
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        TextField choosedFile = new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a file");
        Button fileBtn = new Button("Choose file");
        fileBtn.setOnAction(e-> {
            File file = new FileChooser().showOpenDialog(null);
            String filePath= (file==null) ? "" : file.getPath();
            choosedFile.setText(filePath);
        });
        box.getChildren().addAll(choosedFile, fileBtn);

        // Elementi per la dimensione del fileBtn
        HBox boxFileSize = new HBox();
        boxFileSize.setAlignment(Pos.CENTER);
        boxFileSize.setSpacing(10);
        TextField sizeField = new TextField();
        sizeField.setPromptText("Choose size");
        sizeField.textProperty().addListener( (observable, oldValue, newValue) -> {
            if ( !newValue.matches("\\d*") )
                sizeField.setText(newValue.replaceAll("\\D", ""));
        });
        VBox fileChoice = new VBox();
        fileChoice.setAlignment(Pos.CENTER);
        fileChoice.setSpacing(20);

        ComboBox<String> fileSizeUnit = new ComboBox<>();
        fileSizeUnit.setItems(FXCollections.observableArrayList("B", "KB", "MB", "GB"));
        fileSizeUnit.getSelectionModel().selectFirst();
        boxFileSize.getChildren().addAll(sizeField, fileSizeUnit);

        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            int size = Integer.parseInt(sizeField.getText());
            int mul = switch (fileSizeUnit.getValue()){
                case "GB" -> 1024*1024*1024;
                case "MB" -> 1024*1024;
                case "KB" -> 1024;
                case "B"  -> 1;
                default -> 1;
            };
            trigger = new TriggerFileSize(new File(choosedFile.getText()), size*mul);
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });

        addTriggerBtn.disableProperty().bind(choosedFile.textProperty().isEmpty().or(fileSizeUnit.valueProperty().isNull().or(sizeField.textProperty().isEmpty())));
        parent.getChildren().addAll(box,boxFileSize,addTriggerBtn);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
