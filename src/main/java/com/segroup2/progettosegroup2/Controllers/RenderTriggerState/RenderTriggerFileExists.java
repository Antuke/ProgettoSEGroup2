package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerFileExists;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderTriggerFileExists implements RenderTrigger{
    private TriggerFileExists trigger = null;

    @Override
    public void render(VBox parent) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        TextField choosedFile = new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a file");
        Button fileBtn = new Button("File");

        fileBtn.setOnAction(e-> {
            File file = new FileChooser().showOpenDialog(null);
            choosedFile.setText(file.getPath());
        });

        box.getChildren().addAll(choosedFile, fileBtn);
        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            trigger = new TriggerFileExists(new File(choosedFile.getText()));
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        addTriggerBtn.disableProperty().bind(choosedFile.textProperty().isEmpty());
        parent.getChildren().addAll(box,addTriggerBtn);

    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
