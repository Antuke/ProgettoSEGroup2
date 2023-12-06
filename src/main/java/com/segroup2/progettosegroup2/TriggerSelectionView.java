package com.segroup2.progettosegroup2;

import com.segroup2.progettosegroup2.Triggers.*;
import com.segroup2.progettosegroup2.Triggers.Equation.TriggerAnd;
import com.segroup2.progettosegroup2.Triggers.Equation.TriggerNot;
import com.segroup2.progettosegroup2.Triggers.Equation.TriggerOr;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;


public class TriggerSelectionView extends Application {

    private ComboBox<TriggerEnum> triggersList;
    private TextArea triggerDefinitionResume;
    private Button addTriggerBtn;
    private TriggerInterface tempTrigger;
    private TriggerInterface trigger;
    private CheckBox notCheckBox;

    public static void main(String[] args) {
        launch(args);
    }

    public TriggerSelectionView(){
        trigger = null;
        tempTrigger = null;
        triggersList = new ComboBox<>();
        triggersList.setItems(FXCollections.observableArrayList(TriggerEnum.values()));
        triggersList.setPromptText("Seleziona un trigger");
        triggerDefinitionResume = new TextArea();
        triggerDefinitionResume.setEditable(false);
        triggerDefinitionResume.setWrapText(true);

    }

    public TriggerInterface createView(){
        Stage stage = new Stage();
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setDividerPositions(0.80);
        Scene scene = new Scene(root,400,300);

        // Creazione UpperPane
        VBox upperPane = new VBox();
        upperPane.getChildren().add(triggerDefinitionResume);

        // Creazione BottomPane
        HBox bottomPane = new HBox();
        bottomPane.setSpacing(20);
        bottomPane.setAlignment(Pos.CENTER);
        Button andBtn = new Button("And");
        Button orBtn = new Button("Or");
        notCheckBox = new CheckBox("Not");
        Button simpleTriggerBtn = new Button("Simple");
        Button addTriggerBtn = new Button("Conferma Trigger");


        andBtn.setOnAction(this::andAction);
        orBtn.setOnAction(this::orAction);
        simpleTriggerBtn.setOnAction(this::createSimpleTrigger);
        addTriggerBtn.setOnAction(e->stage.close());

        bottomPane.getChildren().addAll(simpleTriggerBtn, andBtn, orBtn, notCheckBox, addTriggerBtn);

        root.getItems().addAll(upperPane, bottomPane);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
        return trigger;
    }

    private void createSimpleTrigger(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        trigger = tempTrigger;
        updateTriggerDefinitionResume();
    }

    private void notAction(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        TriggerInterface triggerNot = new TriggerOr();
        trigger.add(trigger);
        trigger=triggerNot;
        updateTriggerDefinitionResume();
    }

    private void orAction(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        TriggerInterface triggerOr = new TriggerOr();
        trigger.add(trigger);
        if(notCheckBox.isSelected())
            trigger.add(new TriggerNot(tempTrigger));
        else
            trigger.add(tempTrigger);

        trigger = triggerOr;
        updateTriggerDefinitionResume();
    }

    private void andAction(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        TriggerInterface triggerAnd = new TriggerAnd();
        triggerAnd.add(trigger);
        if (notCheckBox.isSelected())
            triggerAnd.add(new TriggerNot(tempTrigger));
        else
            triggerAnd.add(tempTrigger);
        trigger = triggerAnd;
        updateTriggerDefinitionResume();
    }

    private void updateTriggerDefinitionResume(){
        triggerDefinitionResume.clear();
        triggerDefinitionResume.setText(trigger.toString());
    }
    private void createTriggerDefinitionView() {
        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(20);
        VBox triggerChoice = new VBox();
        triggerChoice.setAlignment(Pos.CENTER);
        triggerChoice.setSpacing(20);

        triggersList.setOnAction(e->{
            //Prima di caricare la nuova view elimino quella già presente
            triggerChoice.getChildren().clear();
            switch (triggersList.getValue()){
                case TRIGGER_TIME_OF_DAY -> renderTriggerTimeOfDay(triggerChoice);
                case TRIGGER_DATE -> renderTriggerDate(triggerChoice);
                case TRIGGER_DAY_OF_WEEK -> renderTriggerDayOfWeek(triggerChoice);
                case TRIGGER_DAY_OF_MONTH -> renderTriggerDayOfMonth(triggerChoice);
                case TRIGGER_FILE_SIZE -> renderFileSizeView(triggerChoice);
                case TRIGGER_FILE_EXISTS -> renderFileExistView(triggerChoice);
            }
        });
        main.getChildren().addAll(triggersList,triggerChoice);

        Stage s = new Stage();
        Scene scene = new Scene(main, 300,300);
        s.setScene(scene);
        s.setTitle("Trigger definition");
        s.setAlwaysOnTop(true);
        s.showAndWait();
    }

    // Mostra un textfield in cui viene inserito il nome di un file selezionato tramite un pulsante
    private void renderFileExistView(VBox root) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        TextField choosedFile = new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a file");
        Button file = new Button("File");

        file.setOnAction(e->pickFile(choosedFile));

        box.getChildren().addAll(choosedFile, file);
        addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            tempTrigger = new TriggerFileExists(new File(choosedFile.getText()));
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        root.getChildren().addAll(box,addTriggerBtn);
    }

    // Mostra un textfield dove viene inserito il nome di un file selezionato tramite un pulsante e un secondo textfield dove inserire la dimensione del file
    private void renderFileSizeView(VBox root) {
        // Elementi per la selezione del file
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        TextField choosedFile = new TextField();
        choosedFile.setEditable(false);
        choosedFile.setPromptText("Choose a file");
        Button file = new Button("File");
        file.setOnAction(e->pickFile(choosedFile));
        box.getChildren().addAll(choosedFile, file);

        // Elementi per la dimensione del file
        HBox boxFileSize = new HBox();
        boxFileSize.setAlignment(Pos.CENTER);
        TextField sizeField = new TextField();
        VBox fileChoice = new VBox();
        fileChoice.setAlignment(Pos.CENTER);
        fileChoice.setSpacing(20);

        ComboBox<String> fileSizeUnit = new ComboBox<>();
        fileSizeUnit.setItems(FXCollections.observableArrayList("GB","MB","KB","B"));
        boxFileSize.getChildren().addAll(sizeField, fileSizeUnit);

        addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            int size = Integer.parseInt(sizeField.getText());
            int mul = switch (fileSizeUnit.getValue()){
                case "GB" -> 1024*1024*1024;
                case "MB" -> 1024*1024;
                case "KB" -> 1024;
                case "B"  -> 1;
                default -> 1;
            };
            tempTrigger = new TriggerFileSize(new File(choosedFile.getText()), size*mul);
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        root.getChildren().addAll(box,boxFileSize,addTriggerBtn);
    }

    private void renderTriggerDayOfMonth(VBox root) {
        ComboBox<Integer> dayOfMonth = new ComboBox<>();
        Integer[] list = new Integer[31];
        for (int i=0;i<31;i++)
            list[i]=i+1;
        dayOfMonth.setItems(FXCollections.observableArrayList(list));
        addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            tempTrigger = new TriggerDayOfMonth(dayOfMonth.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        root.getChildren().addAll(dayOfMonth,addTriggerBtn);
    }

    private void renderTriggerDayOfWeek(VBox root) {
        ComboBox<DayOfWeek> list = new ComboBox<>();
        list.setItems(FXCollections.observableArrayList(DayOfWeek.values()));
        addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            tempTrigger = new TriggerDayOfWeek(list.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        root.getChildren().addAll(list, addTriggerBtn);
    }

    private void renderTriggerDate(VBox root) {
        DatePicker date = new DatePicker();
        addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            tempTrigger = new TriggerDate(date.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        root.getChildren().addAll(date, addTriggerBtn);
    }

    private void renderTriggerTimeOfDay(VBox root) {
        LocalTime time = LocalTime.now();

        TextField hour = new TextField();
        hour.setText(String.valueOf(time.getHour()));
        // Controllo sui valori inseriti nella casella delle ore
        hour.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                hour.setText(newValue.replaceAll("\\D", ""));
                return;
            }

            if(!newValue.isBlank() )
                if (Integer.parseInt(newValue) > 23 || newValue.length() > 2)
                    hour.setText(newValue.substring(0, newValue.length() - 1));
        });

        TextField minute = new TextField();
        minute.setText(String.valueOf(time.getMinute()));
        // Controllo sui valori inseriti nella casella minuti
        minute.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                minute.setText(newValue.replaceAll("\\D", ""));
                return;
            }

            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 59 || newValue.length() > 2)
                    minute.setText(newValue.substring(0, newValue.length() - 1));
        });

        Label l = new Label(":");

        HBox box = new HBox();
        box.setSpacing(20);
        box.getChildren().addAll(hour,l,minute);

        addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            int hh = Integer.parseInt(hour.getText());
            int mm = Integer.parseInt(minute.getText());
            tempTrigger = new TriggerTime(hh,mm);
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        root.getChildren().addAll(box, addTriggerBtn);
    }

    private File pickFile(TextField field) {
        File file = new FileChooser().showOpenDialog(null);
        field.setText(file.getPath());
        return file;
    }

    @Override
    public void start(Stage stage) throws Exception {
        createView();
    }
}
