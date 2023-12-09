package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionAppendToFile;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Launcher;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RenderActionAppendToFile implements RenderAction{

    private ActionInterface action  = null;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();

    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        /* Box scegli file*/
        HBox box = new HBox();
        box.setId("hbox-file");
        TextField choosedFile = new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a file");
        /* text area */
        TextArea toAppend = new TextArea();
        toAppend.setId("pref-width");
        toAppend.setPromptText("Inserisci qui il messaggio da inserire in append al file selezionato");

        Button fileBtn = new Button("File");
        fileBtn.setOnAction(e-> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            File file = fileChooser.showOpenDialog(null);
            if( file!=null )
                choosedFile.setText(file.getPath());
        });

        box.getChildren().addAll(choosedFile, fileBtn);
        Button addAction = new Button("Add Trigger");
        addAction.setId("pref-width");
        addAction.setOnAction(e->{
            action = new ActionAppendToFile(toAppend.getText(),new File(choosedFile.getText()));
            ((Stage) addAction.getScene().getWindow()).close();
        });
        addAction.disableProperty().bind(Bindings.or(choosedFile.textProperty().isEmpty(),toAppend.textProperty().isEmpty()));
        parent.getChildren().addAll(box,toAppend,addAction);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
