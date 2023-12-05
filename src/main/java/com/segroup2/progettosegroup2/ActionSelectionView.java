package com.segroup2.progettosegroup2;

import com.segroup2.progettosegroup2.Actions.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ActionSelectionView {
    private VBox mainActionPane;
    private Label defaultLabel;
    private ComboBox<ActionEnum> actionList;
    private Button addAction;
    private TextArea stringToAppend; // Stringa da inserire in append per azione appendToFile
    private File srcSelectedFile; // File principale su cui eseguire le azioni di copy, delete, move, append
    private File dstSelectedFile; // Cartella di destinazione su cui eseguire le azioni di copy, move
    private ActionInterface action;


    public ActionSelectionView(){
        action = null;
        srcSelectedFile = null;
        dstSelectedFile = null;
    }

    public ActionInterface createView(){
        Stage stage = new Stage();
        stage.setResizable(false);
        VBox box = new VBox();
        mainActionPane = new VBox();
        mainActionPane.setAlignment(Pos.CENTER);
        mainActionPane.setSpacing(20);

        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(10));
        box.setSpacing(20);
        Scene scene = new Scene(box, 500,250);
        actionList = new ComboBox<>();

        actionList.setItems(FXCollections.observableArrayList(ActionEnum.values()));
        actionList.setPromptText("Select an action");
        box.getChildren().add(actionList);
        box.getChildren().add(mainActionPane);
        stage.setScene(scene);
        stage.setTitle("Action Chooser");

        // Scelgo quali elementi creare e visualizzare in base all'azione che sto selezionando
        actionList.setOnAction(e->{
            switch (actionList.getValue()){
                case ACTION_DEFAULT_DIALOGBOX :
                    renderActionDialogBox();
                    break;
                case ACTION_DEFAULT_AUDIO :
                    renderActionAudioView();
                    break;
                case ACTION_APPEND_TO_FILE :
                    renderActionAppendToFile();
                    break;
                case ACTION_DELETE_FILE:
                    renderActionDeleteFile();
                    break;
                case ACTION_COPY_FILE:
                case ACTION_MOVE_FILE:
                    renderActionTwoFile();
                    break;
                default:
                    mainActionPane.getChildren().clear();
            }
        });

        addAction = new Button("Add Action");
        addAction.disableProperty().bind(actionList.valueProperty().isNull());
        box.getChildren().add(addAction);
        addAction.setOnAction(e->{
            switch (actionList.getValue()){
                case ACTION_DEFAULT_DIALOGBOX :
                    action = new ActionDialogBox();
                    break;
                case ACTION_DEFAULT_AUDIO :
                    action = new ActionAudio();
                    break;
                case ACTION_APPEND_TO_FILE :
                    action = new ActionAppendToFile(stringToAppend.getText(), srcSelectedFile);
                    break;
                case ACTION_COPY_FILE :
                    action = new ActionCopyFile(srcSelectedFile,dstSelectedFile);
                    break;
                case ACTION_DELETE_FILE:
                    action = new ActionDeleteFile(srcSelectedFile);
                    break;
                case ACTION_MOVE_FILE:
                    action = new ActionMoveFile(srcSelectedFile,dstSelectedFile);
                    break;
                default:
                    action = null;

            }
            stage.close();
        });
        stage.showAndWait();
        return action;
    }


    private void renderActionAudioView(){
        addAction.disableProperty().unbind();
        defaultLabel = new Label("Playing default audio file: default_audio.wav");
        mainActionPane.getChildren().clear();
        mainActionPane.getChildren().add(defaultLabel);
    }
    private void renderActionDialogBox(){
        addAction.disableProperty().unbind();
        defaultLabel = new Label("Messaggio di default : Promemoria");
        mainActionPane.getChildren().clear();
        mainActionPane.getChildren().add(defaultLabel);
    }
    // Render della view dove un'azione necessita di un file e una cartella come parametri dell'operazione da eseguire
    private void renderActionTwoFile(){
        mainActionPane.getChildren().clear();
        addAction.disableProperty().unbind();

        HBox line1 = new HBox();
        line1.setSpacing(10);
        line1.setAlignment(Pos.CENTER);
        TextField fileName1 = new TextField();
        fileName1.setEditable(false);
        fileName1.setPrefWidth(200);
        fileName1.setPromptText("Seleziona il file sorgente dell'azione");
        Button button1 = new Button();
        button1.setOnAction(e->{
            filePicker(fileName1);
        });
        button1.setText("Scegli un file");
        line1.getChildren().addAll(fileName1,button1);

        HBox line2 = new HBox();
        line2.setSpacing(10);
        line2.setAlignment(Pos.CENTER);
        TextField fileName2 = new TextField();
        fileName2.setPromptText("Seleziona il file destinazione dell'azione");
        fileName2.setEditable(false);
        fileName2.setPrefWidth(200);
        Button button2 = new Button();
        button2.setText("Scegli un file");
        button2.setOnAction(e->{
            directoryPicker(fileName2);
        });
        line2.getChildren().addAll(fileName2,button2);

        mainActionPane.getChildren().addAll(line1,line2);
        addAction.disableProperty().bind(fileName1.textProperty().isEmpty().and(fileName2.textProperty().isEmpty()));
    }
    // Render della view che gestisce la grafina necessarie per inizializzare un'azione di append to file
    private void renderActionAppendToFile(){
        addAction.disableProperty().unbind();
        mainActionPane.getChildren().clear();
        // Creazione di un container orizzontale per contenere gli elementi della prima riga
        HBox line1 = new HBox();
        line1.setSpacing(10);
        line1.setAlignment(Pos.CENTER_LEFT);

        TextField choosedFile = new TextField();
        choosedFile.setPromptText("Selezionare un file");
        choosedFile.setEditable(false);
        Button fileChooser = new Button();
        fileChooser.setText("Scegli un file");
        fileChooser.setOnAction(e->filePicker(choosedFile));
        line1.getChildren().addAll(choosedFile,fileChooser);

        stringToAppend = new TextArea();
        stringToAppend.setPromptText("Inserire il testo da aggiungere un append al file selezionato");
        mainActionPane.getChildren().addAll(line1,stringToAppend);
        addAction.disableProperty().bind(choosedFile.textProperty().isEmpty().and(stringToAppend.textProperty().isEmpty()));
    }
    private void renderActionDeleteFile(){
        addAction.disableProperty().unbind();
        mainActionPane.getChildren().clear();
        // Creazione di un container orizzontale per contenere gli elementi della prima riga
        HBox line1 = new HBox();
        line1.setSpacing(10);
        line1.setAlignment(Pos.CENTER_LEFT);

        TextField choosedFile = new TextField();
        choosedFile.setPromptText("Selezionare un file");
        choosedFile.setEditable(false);
        Button fileChooser = new Button();
        fileChooser.setText("Scegli un file");
        fileChooser.setOnAction(e->filePicker(choosedFile));
        line1.getChildren().addAll(choosedFile,fileChooser);

        mainActionPane.getChildren().addAll(line1,stringToAppend);
        addAction.disableProperty().bind(choosedFile.textProperty().isEmpty());
    }

    /* La funzione directoryPicker si occupa di selezionare una cartella attraverso l'utilizzo di un
     * DirectoryChooser. Riceve in ingresso un oggetto TextField utilizzato per mostare il path
     * della cartella selezionata.
     */
    private void directoryPicker(TextField line){
        DirectoryChooser dirChooser = new DirectoryChooser();
        dstSelectedFile = dirChooser.showDialog(null);
        line.setText(dstSelectedFile.getPath());
    }

    /* La funzione filePicker si occupa di selezionare un file attraverso l'utilizzo di un
     * FileChooser. Riceve in ingresso un oggetto TextField utilizzato per mostare il path
     * del file selezionato.
    */
    private void filePicker(TextField line) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        srcSelectedFile = fileChooser.showOpenDialog(null);
        line.setText(srcSelectedFile.getPath());
    }

}
