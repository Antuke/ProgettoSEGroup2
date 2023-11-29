package com.segroup2.progettosegroup2.Controllers;


import com.segroup2.progettosegroup2.Actions.ActionAppendToFIle;
import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Rules.SingleRule;
import com.segroup2.progettosegroup2.Triggers.*;
import com.segroup2.progettosegroup2.Rules.SleepingRule;
import com.segroup2.progettosegroup2.Triggers.TriggerDayOfMonth;
import com.segroup2.progettosegroup2.Triggers.TriggerEnum;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.security.cert.Extension;
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
    private Button pickFileBTNTwo;

    @FXML
    private HBox pickFileHBoxParamTwo;

    @FXML
    private HBox pickTextHBoxParamOne;

    @FXML
    private TextField pickedFileTwoPath;

    @FXML
    private RadioButton singleTimeRuleRadioBtn;

    @FXML
    private TextField sleepDayField;

    @FXML
    private TextField sleepHoiurField;

    @FXML
    private TextField sleepMinutesField;

    @FXML
    private RadioButton sleepingRuleRadioBtn;

    @FXML
    private HBox timePickerHBox;

    @FXML
    private ComboBox<TriggerEnum> triggerPickerComboBox;

    @FXML
    private StackPane triggerStackPane;

    private ObservableList<TriggerEnum> triggerPickerComboBoxValue;

    private ObservableList<ActionEnum> actionPickerComboBoxValue;

    private ToggleGroup radioButtonGroup;

    private File selectedFile;
    @FXML
    void commitRule(ActionEvent event) {

        var action = switch (actionPickerComboBox.getValue()) {
            case ACTION_DEFAULT_DIALOGBOX ->
                    new ActionDialogBox();
            case ACTION_DEFAULT_AUDIO ->
                    new ActionAudio();
            case ACTION_APPEND_TO_FILE ->
                new ActionAppendToFIle(inputTextFieldOne.getText(),selectedFile);
            default -> null;
        };
        var trigger = switch (triggerPickerComboBox.getValue()) {
            case TRIGGER_TIME_OF_DAY -> new TriggerTime(Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()));
            case TRIGGER_DAY_OF_MONTH -> new TriggerDayOfMonth( dayOfMonthPickerComboBox.getValue() );
            case TRIGGER_DAY_OF_WEEK -> new TriggerDayOfWeek( dayOfWeekPickerComboBox.getValue() );
            case TRIGGER_DATE -> new TriggerDate( datePicker.getValue() );
        };

        Rule rule = switch (((RadioButton) radioButtonGroup.getSelectedToggle()).getText().toLowerCase()){
            case "normal" ->  new Rule(trigger,action);
            case "single" -> new SingleRule(trigger,action);
            case "sleeping" -> {
                int day = Integer.parseInt(sleepDayField.getText());
                int hh = Integer.parseInt(sleepHoiurField.getText());
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
        /*inizializzazione hours e minutes TextField */
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

            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 23 || newValue.length() > 2)
                    hoursTextField.setText(newValue.substring(0, newValue.length() - 1));
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

        /* Aggiunta alle comboBox dei trigger e delle azione le possibi9li scelte */
        actionPickerComboBox.setItems(actionPickerComboBoxValue);
        triggerPickerComboBox.setItems(triggerPickerComboBoxValue);

        radioButtonGroup = new ToggleGroup();
        normalRuleRadioBtn.setToggleGroup(radioButtonGroup);
        singleTimeRuleRadioBtn.setToggleGroup(radioButtonGroup);
        sleepingRuleRadioBtn.setToggleGroup(radioButtonGroup);
        normalRuleRadioBtn.setSelected(true);


        bindingsInit();
    }

    private void bindingsInit(){
        /* Bindings */
        timePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_TIME_OF_DAY));
        dayOfMonthPickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DAY_OF_MONTH));
        dayOfWeekPickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DAY_OF_WEEK));
        datePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DATE));

        defaultLabelText.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_DIALOGBOX));
        deafultAudioLabel.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_AUDIO));

        /*append to file action binding*/
        ObservableBooleanValue pickedAppendFileAndText = Bindings.and(inputTextFieldOne.textProperty().isNotEmpty(),pickedFileTwoPath.textProperty().isNotEmpty());
        pickFileHBoxParamTwo.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_APPEND_TO_FILE));
        pickTextHBoxParamOne.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_APPEND_TO_FILE));

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
        /* Bindings per scelta azione testo default */
        ObservableBooleanValue pickedDefaultDialogBox = actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_DIALOGBOX);
        /* Bindings per scelta azione audio default */
        ObservableBooleanValue pickedDefaultAudio = actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_AUDIO);


        /*Da aggiornare all'aggiunta di ogni trigger e azione */
        ObservableBooleanValue pickedTrigger = Bindings.or(pickedTriggerTime, pickedTriggerDayOfMonth).or(pickedTriggerDayOfWeek).or(pickedTriggerDate);
        //ObservableBooleanValue pickedAction = actionPickerComboBox.valueProperty().isNotNull();
        ObservableBooleanValue pickedAction = Bindings.or(pickedAppendFileAndText,pickedDefaultDialogBox).or(pickedDefaultAudio);

        addRuleBTN.disableProperty().bind(Bindings.not(
                Bindings.and(pickedTrigger,pickedAction)
        ));
    }

    @FXML
    void pickFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setTitle("Scegli il file");

        switch(actionPickerComboBox.getValue()) {
            case ACTION_APPEND_TO_FILE -> {
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                selectedFile = fc.showOpenDialog(null);
                if(selectedFile != null) pickedFileTwoPath.setText(selectedFile.toString());
            }
            default -> throw new RuntimeException();
        }


    }



}
