package com.segroup2.progettosegroup2.Controllers;


import com.segroup2.progettosegroup2.Actions.*;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Rules.SingleRule;
import com.segroup2.progettosegroup2.Rules.SleepingRule;
import com.segroup2.progettosegroup2.Triggers.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

public class AddRuleController implements Initializable {

    @FXML
    private StackPane actionParamOneStackPane;

    @FXML
    private StackPane actionParamTwoStackPane;

    @FXML
    private VBox actionParamsVBOX;

    @FXML
    private ComboBox<ActionEnum> actionPickerComboBox;

    @FXML
    private StackPane actionStackPane;

    @FXML
    private Button addRuleBTN;

    @FXML
    private DatePicker datePicker;

    @FXML
    private HBox datePickerHBox;

    @FXML
    private ComboBox<Integer> dayOfMonthPickerComboBox;

    @FXML
    private HBox dayOfMonthPickerHBox;

    @FXML
    private ComboBox<DayOfWeek> dayOfWeekPickerComboBox;

    @FXML
    private HBox dayOfWeekPickerHBox;

    @FXML
    private Label deafultAudioLabel;

    @FXML
    private Label defaultLabelText;

    @FXML
    private TextField hoursTextField;

    @FXML
    private TextField inputTextFieldOne;

    @FXML
    private TextField minutesTextField;

    @FXML
    private RadioButton normalRuleRadioBtn;

    @FXML
    private Button pickFileBTNOne;

    @FXML
    private Button pickFileBTNTwo;

    /**
     * Non effettuare il bind per visibleProperty<br>
     * Utilizzare la variabile {@code pickFileHBoxParamOneValue} in OR.
     * Più oggetti posso mostrare questo oggetto
     */
    @FXML
    private HBox pickFileHBoxParamOne;

    @FXML
    private HBox pickFileHBoxParamTwo;

    @FXML
    private HBox pickTextHBoxParamOne;

    @FXML
    private TextField pickedFileOnePath;

    @FXML
    private TextField pickedFileTwoPath;

    @FXML
    private RadioButton singleTimeRuleRadioBtn;

    @FXML
    private TextField sleepDayField;

    @FXML
    private TextField sleepHourField;

    @FXML
    private TextField sleepMinutesField;

    @FXML
    private RadioButton sleepingRuleRadioBtn;

    @FXML
    private HBox timePickerHBox;

    @FXML
    private HBox pickFileHBoxParamThree;

    @FXML
    private TextField pickedFileThreePath;

    @FXML
    private Button pickFileBTNThree;

    @FXML
    private TextField inputTextFieldThree;

    @FXML
    private ComboBox<TriggerEnum> triggerPickerComboBox;

    @FXML
    private StackPane triggerStackPane;

    private ObservableList<TriggerEnum> triggerPickerComboBoxValue;

    private ObservableList<ActionEnum> actionPickerComboBoxValue;

    private ToggleGroup radioButtonGroup;

    private File selectedFile;

    private File selectedTriggerFile;
    private File selectedDirectory;
    @FXML
    void commitRule(ActionEvent event) {

        var action = switch (actionPickerComboBox.getValue()) {
            case ACTION_DEFAULT_DIALOGBOX -> new ActionDialogBox();
            case ACTION_DEFAULT_AUDIO -> new ActionAudio();
            case ACTION_APPEND_TO_FILE -> new ActionAppendToFIle(inputTextFieldOne.getText(), selectedFile);
            case ACTION_COPY_FILE -> new ActionCopyFile(selectedFile, selectedDirectory);
            case ACTION_DELETE_FILE -> new ActionDeleteFile(selectedFile);
            case ACTION_MOVE_FILE -> new ActionMoveFile(selectedFile, selectedDirectory);
            default -> null;
        };
        var trigger = switch (triggerPickerComboBox.getValue()) {
            case TRIGGER_TIME_OF_DAY -> new TriggerTime(Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()));
            case TRIGGER_DAY_OF_MONTH -> new TriggerDayOfMonth( dayOfMonthPickerComboBox.getValue() );
            case TRIGGER_DAY_OF_WEEK -> new TriggerDayOfWeek( dayOfWeekPickerComboBox.getValue() );
            case TRIGGER_DATE -> new TriggerDate( datePicker.getValue() );
            case TRIGGER_FILE_EXISTS -> new TriggerFileExists(selectedTriggerFile);
            case TRIGGER_FILE_SIZE -> new TriggerFileSize(selectedTriggerFile, Integer.parseInt(inputTextFieldThree.getText()));
        };

        Rule rule = switch (((RadioButton) radioButtonGroup.getSelectedToggle()).getText().toLowerCase()){
            case "normal" ->  new Rule(trigger,action);
            case "single" -> new SingleRule(trigger,action);
            case "sleeping" -> {
                int day = Integer.parseInt(sleepDayField.getText());
                int hh = Integer.parseInt(sleepHourField.getText());
                int mm =Integer.parseInt(sleepMinutesField.getText());
                yield new SleepingRule(trigger,action,day,hh, mm);
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + ((RadioButton) radioButtonGroup.getSelectedToggle()).getText().toLowerCase());
        };
        RulesManager.getInstance().addRule(rule);

        /*Chiudo la finestra Aggiungi regola dopo aver premuto il pulsante*/
        ((Stage) addRuleBTN.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*codice per il controllo di input sbagliati*/
        minutesTextField.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                minutesTextField.setText(newValue.replaceAll("\\D", ""));
                return;
            }

            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 59 || newValue.length() > 2)
                    minutesTextField.setText(newValue.substring(0, newValue.length() - 1));
        });
        hoursTextField.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                hoursTextField.setText(newValue.replaceAll("\\D", ""));
                return;
            }

            if(!newValue.isBlank() )
                if (Integer.parseInt(newValue) > 23 || newValue.length() > 2)
                    hoursTextField.setText(newValue.substring(0, newValue.length() - 1));
        });
        sleepDayField.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                sleepDayField.setText(newValue.replaceAll("\\D*", ""));
            }
        });
        sleepHourField.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                sleepHourField.setText(newValue.replaceAll("\\D", ""));
                return;
            }
            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 23 || newValue.length() > 2)
                    sleepHourField.setText(newValue.substring(0, newValue.length() - 1));
        });
        sleepMinutesField.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                sleepMinutesField.setText(newValue.replaceAll("\\D", ""));
                return;
            }
            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 59 || newValue.length() > 2)
                    sleepMinutesField.setText(newValue.substring(0, newValue.length() - 1));
        });

        /* inizializzazione giorni del mese per ComboBox */
        ObservableList<Integer> dayPickerComboBoxValue= FXCollections.observableArrayList();
        for(int i=1; i<=31; i++)
            dayPickerComboBoxValue.add(i);
        dayOfMonthPickerComboBox.setItems(dayPickerComboBoxValue);

        /* inizializzazione giorni della settimana per ComboBox */
        ObservableList<DayOfWeek> dayOfWeekPickerComboBoxValue= FXCollections.observableArrayList(DayOfWeek.values());
        dayOfWeekPickerComboBox.setItems(dayOfWeekPickerComboBoxValue);

        /* inizializzazione dei trigger e action picker */
        actionPickerComboBoxValue = FXCollections.observableArrayList(ActionEnum.values());
        triggerPickerComboBoxValue = FXCollections.observableArrayList(TriggerEnum.values());

        /* Aggiunta alle comboBox dei trigger e delle azione le possibili scelte */
        actionPickerComboBox.setItems(actionPickerComboBoxValue);
        triggerPickerComboBox.setItems(triggerPickerComboBoxValue);

        /* Aggiunta del ToggleGroup per i radio button e settaggio di un radio button di deafult */
        radioButtonGroup = new ToggleGroup();
        normalRuleRadioBtn.setToggleGroup(radioButtonGroup);
        singleTimeRuleRadioBtn.setToggleGroup(radioButtonGroup);
        sleepingRuleRadioBtn.setToggleGroup(radioButtonGroup);
        normalRuleRadioBtn.setSelected(true);


        bindingsInit();
    }

    private void bindingsInit(){
        /* Bindings trigger */
        timePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_TIME_OF_DAY));
        dayOfMonthPickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DAY_OF_MONTH));
        dayOfWeekPickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DAY_OF_WEEK));
        datePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DATE));

        pickFileHBoxParamThree.visibleProperty().bind(
                triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_FILE_EXISTS)
                        .or(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_FILE_SIZE))
        );
        inputTextFieldThree.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_FILE_SIZE));




        /* Bindings actions */
        defaultLabelText.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_DIALOGBOX));
        deafultAudioLabel.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_AUDIO));
        /* Bindings scrittura in append a un file */
        ObservableBooleanValue pickFileHBoxParamOneValue = actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_APPEND_TO_FILE);
        pickTextHBoxParamOne.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_APPEND_TO_FILE));
        /* Bindings alla copia del file */
        pickFileHBoxParamOneValue= Bindings.or(pickFileHBoxParamOneValue, actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_COPY_FILE));
        /*Bindings azione cancellazione file*/
        pickFileHBoxParamOneValue = Bindings.or(pickFileHBoxParamOneValue,actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DELETE_FILE));
        /* Bindings allo spostamento del file */
        pickFileHBoxParamOneValue= Bindings.or(pickFileHBoxParamOneValue, actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_MOVE_FILE));
        /* Bindings cumulativi tramite OR */
        pickFileHBoxParamOne.visibleProperty().bind(pickFileHBoxParamOneValue);
        pickFileHBoxParamTwo.visibleProperty().bind(
                actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_COPY_FILE)
                        .or(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_MOVE_FILE))
        );


        /* Button Bindings */
        /* Bindings per la scelta dell'ora */
        ObservableBooleanValue pickedTriggerTime = Bindings.and(
                timePickerHBox.visibleProperty(), Bindings.and(
                        hoursTextField.textProperty().isNotEmpty(),
                        minutesTextField.textProperty().isNotEmpty()
                )
        );
        /* Bindings per la scelta del giorno del mese */
        ObservableBooleanValue pickedTriggerDayOfMonth = Bindings.and( dayOfMonthPickerHBox.visibleProperty(), dayOfMonthPickerComboBox.valueProperty().isNotNull() );
        /* Bindings per la scelta del giorno della settimana */
        ObservableBooleanValue pickedTriggerDayOfWeek = Bindings.and( dayOfWeekPickerHBox.visibleProperty(), dayOfWeekPickerComboBox.valueProperty().isNotNull() );
        /* Bindings per la scelta della date */
        ObservableBooleanValue pickedTriggerDate= Bindings.and( datePickerHBox.visibleProperty(), datePicker.valueProperty().isNotNull() );
        /* Bindings per la scelta del file */
        ObservableBooleanValue pickedTriggerFile = pickedFileThreePath.textProperty().isNotEmpty();


        /* Bindings per scelta azione testo default */
        ObservableBooleanValue pickedDefaultDialogBox = actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_DIALOGBOX);
        /* Bindings per scelta azione audio default */
        ObservableBooleanValue pickedDefaultAudio = actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_AUDIO);
        /* Bindings per la scrittura in append a un file */
        ObservableBooleanValue pickedAppendFileAndText = Bindings.and(inputTextFieldOne.textProperty().isNotEmpty(), pickedFileOnePath.textProperty().isNotEmpty());
        /* Bindings per la copia di un file */
        ObservableBooleanValue pickedCopyFile= Bindings.and(pickedFileOnePath.textProperty().isNotEmpty(), pickedFileTwoPath.textProperty().isNotEmpty());
        /* Bindings per la cancellazione di un file */
        ObservableBooleanValue pickedFileToDelete = pickedFileOnePath.textProperty().isNotEmpty();
        /* Bindings per lo spostamento di un file */
        ObservableBooleanValue pickedMoveFile = Bindings.and(pickedFileOnePath.textProperty().isNotEmpty(), pickedFileTwoPath.textProperty().isNotEmpty());


        /* Bindings per i campi della regola sleeping e il radioButton*/
        sleepHourField.editableProperty().bind(sleepingRuleRadioBtn.selectedProperty());
        sleepMinutesField.editableProperty().bind(sleepingRuleRadioBtn.selectedProperty());
        sleepDayField.editableProperty().bind(sleepingRuleRadioBtn.selectedProperty());
        ObservableBooleanValue sleepingParamNotEmpty = Bindings.or(
          sleepingRuleRadioBtn.selectedProperty().not(),
          Bindings.and(
                  sleepingRuleRadioBtn.selectedProperty(),
                  sleepDayField.textProperty().isNotEmpty()
          ).and(sleepHourField.textProperty().isNotEmpty().and(sleepMinutesField.textProperty().isNotEmpty())
          )
        );

        /*Da aggiornare all'aggiunta di ogni trigger e azione */
        ObservableBooleanValue pickedTrigger = Bindings.or(pickedTriggerTime, pickedTriggerDayOfMonth).or(pickedTriggerDayOfWeek).or(pickedTriggerDate).or(pickedTriggerFile);
        ObservableBooleanValue pickedAction = Bindings.or(pickedAppendFileAndText, pickedDefaultDialogBox).or(pickedDefaultAudio).or(pickedCopyFile).or(pickedFileToDelete).or(pickedMoveFile);

        addRuleBTN.disableProperty().bind(
                Bindings.not(
                    Bindings.and(pickedTrigger,pickedAction).and(sleepingParamNotEmpty)
                )
        );
    }

    @FXML
    void pickFileOne(ActionEvent event){
        switch( actionPickerComboBox.getValue() ){
            case ACTION_APPEND_TO_FILE:
                selectedFile= chooseFile(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                setTextField(pickedFileOnePath, selectedFile);
                break;
            case ACTION_COPY_FILE:
                selectedFile= chooseFile(null);
                setTextField(pickedFileOnePath, selectedFile);
                break;
            case ACTION_DELETE_FILE:
                selectedFile= chooseFile(null);
                setTextField(pickedFileOnePath,selectedFile);
                break;
            case ACTION_MOVE_FILE:
                selectedFile= chooseFile(null);
                setTextField(pickedFileOnePath, selectedFile);
                break;

        }
    }

    @FXML
    void pickFileTwo(ActionEvent event){
        switch ( actionPickerComboBox.getValue() ){
            case ACTION_COPY_FILE:
                selectedDirectory= chooseDirectory(null);
                setTextField(pickedFileTwoPath, selectedDirectory);
                break;
            case ACTION_MOVE_FILE:
                selectedDirectory= chooseDirectory(null);
                setTextField(pickedFileTwoPath, selectedDirectory);
                break;
        }
    }

    @FXML
    void pickFileThree(ActionEvent event) {
        switch ( triggerPickerComboBox.getValue() ){
            case TRIGGER_FILE_SIZE:
                selectedTriggerFile = chooseFile(null);
                setTextField(pickedFileThreePath, selectedTriggerFile);
                break;
            case TRIGGER_FILE_EXISTS:
                selectedTriggerFile = chooseFile(null);
                setTextField(pickedFileThreePath, selectedTriggerFile);
                break;
        }
    }

    /**
     * Apre una finestra tramite {@link FileChooser} con titolo di default e restituisce il file selezionato
     * @param filters Specifica i filtri da applicare
     * @return Il file selezionato dall'utente oppure {@code null} se non ha selezionato nulla
     * @see #chooseFile(String, FileChooser.ExtensionFilter...) 
     */
    private File chooseFile(FileChooser.ExtensionFilter... filters){
        return( chooseFile(null, filters) );
    }

    /**
     * Apre una finestra tramite {@link FileChooser} e restituisce il file selezionato
     * @param title Imposta il titolo della finestra. Se non specificato il titolo è quello di default
     * @param filters Specifica i filtri da applicare
     * @return Il file selezionato dall'utente oppure {@code null} se non ha selezionato nulla
     */
    private File chooseFile(String title, FileChooser.ExtensionFilter... filters){
        FileChooser fc = new FileChooser();

        if(title!=null)
            fc.setTitle(title);

        if( filters!=null && filters.length>0 )
            for(FileChooser.ExtensionFilter filter : filters )
                fc.getExtensionFilters().add(filter);

        return( fc.showOpenDialog(null) );
    }

    /**
     * Apre una finestra tramite {@link DirectoryChooser} e restituisce la cartella selezionata
     * @param title Imposta il titolo della finestra. Se non specificato il titolo è quello di default
     * @return La cartella selezionata dall'utente oppure {@code null} se non ha selezionato nulla
     */
    private File chooseDirectory(String title){
        DirectoryChooser dc= new DirectoryChooser();

        if( title!=null )
            dc.setTitle(title);

        return( dc.showDialog(null) );
    }

    /**
     * Imposta come testo di un textField il testo passato come parametro.
     * Se uno dei due valori è {@code null} non fa nulla
     * @param textField {@link TextField} di cui modificare il testo
     * @param text Oggetto che verrà convertito in stringa e passato a {@code textField}
     */
    private void setTextField(TextField textField, Object text){
        if( textField!=null && text!=null )
            textField.setText(text.toString());
    }



}
