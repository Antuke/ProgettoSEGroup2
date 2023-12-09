package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerFileExists;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerFileExists}
 */
public class RenderTriggerFileExists implements RenderTrigger{
    private TriggerInterface trigger = null;

    @Override
    public void render(VBox parent) {
        VBox box = new VBox();
        box.setId("vbox-file");
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
        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setId("pref-width");
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
