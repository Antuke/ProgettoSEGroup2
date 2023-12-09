package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionCopyFile;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Launcher;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionCopyFile implements RenderAction{
    private ActionInterface action = null;
    private File file = null;
    private File folder = null;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();
    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        VBox fileBox = new VBox();
        fileBox.setId("vbox-file");
        TextField fileField = new TextField();
        fileField.setPromptText("Choose a file...");
        fileField.setEditable(false);
        Button chooseFile = new Button("Choose File");
        chooseFile.setOnAction(e->{
            file = new FileChooser().showOpenDialog(null);
            if (file!=null)
                fileField.setText(file.getPath());
        });
        fileBox.getChildren().addAll(fileField,chooseFile);

        VBox dirBox = new VBox();
        dirBox.setId("vbox-file");
        TextField dirField = new TextField();
        dirField.setPromptText("Choose a directory...");
        dirField.setEditable(false);
        Button chooseDir = new Button("Choose dir");
        chooseDir.setOnAction(e->{
            folder = new DirectoryChooser().showDialog(null);
            if (folder!=null)
                dirField.setText(folder.getPath());
        });
        dirBox.getChildren().addAll(dirField,chooseDir);

        Button addActionBtn = new Button("Add Action");
        addActionBtn.setId("pref-width");
        addActionBtn.setOnAction(e->{
            action = new ActionCopyFile(file,folder);
            ((Stage) addActionBtn.getScene().getWindow()).close();
        });

        addActionBtn.disableProperty().bind(fileField.textProperty().isEmpty().or(dirField.textProperty().isEmpty()));

        parent.getChildren().addAll(fileBox, dirBox, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
