package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionDeleteFile;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionDeleteFile implements RenderAction{

    private ActionInterface action  = null;
    private File file = null;

    @Override
    public void render(VBox parent) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        TextField fileFiled = new TextField();
        fileFiled.setPromptText("Choose a file...");
        fileFiled.setEditable(false);
        Button chooseFile = new Button("Choose File");
        chooseFile.setOnAction(e->{
            file = new FileChooser().showOpenDialog(null);
            if (file != null)
                fileFiled.setText(file.getPath());
        });

        box.getChildren().addAll(fileFiled, chooseFile);

        Button addActionBtn = new Button("Add Action");
        addActionBtn.setOnAction(e->{
            action = new ActionDeleteFile(file);
            ((Stage) addActionBtn.getScene().getWindow()).close();
        });

        addActionBtn.disableProperty().bind(fileFiled.textProperty().isEmpty());

        parent.getChildren().addAll(box, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
