package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionDeleteFile;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Launcher;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionDeleteFile implements RenderAction{

    private ActionInterface action  = null;
    private File file = null;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();

    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        VBox box = new VBox();
        box.setId("vbox-file");

        TextField fileField = new TextField();
        fileField.setPromptText("Choose a file...");
        fileField.setEditable(false);
        Button chooseFile = new Button("Choose File");
        chooseFile.setOnAction(e->{
            file = new FileChooser().showOpenDialog(null);
            if (file != null)
                fileField.setText(file.getPath());
        });

        box.getChildren().addAll(fileField, chooseFile);

        Button addActionBtn = new Button("Add Action");
        addActionBtn.setId("pref-width");
        addActionBtn.setOnAction(e->{
            action = new ActionDeleteFile(file);
            ((Stage) addActionBtn.getScene().getWindow()).close();
        });

        addActionBtn.disableProperty().bind(fileField.textProperty().isEmpty());

        parent.getChildren().addAll(box, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
