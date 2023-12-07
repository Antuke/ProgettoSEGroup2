package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionAppendToFile;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionAppendToFile implements RenderAction{

    private ActionInterface action  = null;

    @Override
    public void render(VBox parent) {
        /* Box scegli file*/
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        TextField choosedFile = new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a file");

        /* text area */
        TextField toAppend = new TextField();


        Button fileBtn = new Button("File");
        fileBtn.setOnAction(e-> {
            File file = new FileChooser().showOpenDialog(null);
            choosedFile.setText(file.getPath());
        });

        box.getChildren().addAll(choosedFile, fileBtn);
        Button addAction = new Button("Add Trigger");
        addAction.setOnAction(e->{
            action = new ActionAppendToFile(toAppend.getText(),new File(choosedFile.getText()));
            ((Stage) addAction.getScene().getWindow()).close();
        });
        addAction.disableProperty().bind(Bindings.and(choosedFile.textProperty().isEmpty(),toAppend.textProperty().isEmpty()));
        parent.getChildren().addAll(box,toAppend,addAction);
    }

    @Override
    public ActionInterface getAction() {
        return null;
    }
}
