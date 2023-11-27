package com.segroup2.progettosegroup2.Controllers;


import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddRuleController implements Initializable {

    @FXML
    private ComboBox<ActionEnum> actionPickerComboBox;

    @FXML
    private StackPane actionStackPane;

    @FXML
    private Button addRuleBTN;

    @FXML
    private Label deafultAudioLabel;

    @FXML
    private Label defaultLabelText;

    @FXML
    private HBox timePickerHBox;

    @FXML
    private ComboBox<TriggerEnum> triggerPickerComboBox;

    @FXML
    private StackPane triggerStackPane;

    @FXML
    private TextField minutesTextField;
    @FXML
    private TextField hoursTextField;
    @FXML
    private ComboBox<Integer> dayPickerComboBox;
    @FXML
    private HBox dayPickerHBox;

    private ObservableList<TriggerEnum> triggerPickerComboBoxValue;

    private ObservableList<ActionEnum> actionPickerComboBoxValue;

    @FXML
    void commitRule(ActionEvent event) {

        var action = switch (actionPickerComboBox.getValue()) {
            case ACTION_DEFAULT_DIALOGBOX ->
                    new ActionDialogBox();
            case ACTION_DEFAULT_AUDIO ->
                    new ActionAudio();
        };
        var trigger = switch (triggerPickerComboBox.getValue()) {
            case TRIGGER_TIME_OF_DAY -> new TriggerTime(Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()));
            case TRIGGER_DAY_OF_MONTH -> new TriggerDayOfMonth( dayPickerComboBox.getValue() );
        };
        Rule rule = new Rule(trigger,action);
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
                minutesTextField.setText(newValue.replaceAll("[^\\d]", ""));
                return;
            }

            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 59 || newValue.length() > 2)
                    minutesTextField.setText(newValue.substring(0, newValue.length() - 1));
        });
        hoursTextField.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                hoursTextField.setText(newValue.replaceAll("[^\\d]", ""));
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
        dayPickerComboBox.setItems(dayPickerComboBoxValue);

        /* inizializzazione dei trigger e action picker */
        actionPickerComboBoxValue = FXCollections.observableArrayList(ActionEnum.values());
        triggerPickerComboBoxValue = FXCollections.observableArrayList(TriggerEnum.values());

        /* Aggiunta alle comboBox dei trigger e delle azione le possibili scelte */
        actionPickerComboBox.setItems(actionPickerComboBoxValue);
        triggerPickerComboBox.setItems(triggerPickerComboBoxValue);


        /* Bindings */
        timePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_TIME_OF_DAY));
        dayPickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_DAY_OF_MONTH));

        defaultLabelText.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_DIALOGBOX));
        deafultAudioLabel.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_AUDIO));

        /* Button Bindings */
        /* Bindings per la scelta dell'ora */
        ObservableBooleanValue pickedTriggerTime = Bindings.and(
                timePickerHBox.visibleProperty(),
                Bindings.and(
                        hoursTextField.textProperty().isNotEmpty(),
                        minutesTextField.textProperty().isNotEmpty()
                )
        );
        /* Bindings per la scelta del giorno del mese */
        ObservableBooleanValue pickedTriggerDay = Bindings.and( dayPickerHBox.visibleProperty(), dayPickerComboBox.valueProperty().isNotNull() );

        /*Da aggiornare all'aggiunta di ogni trigger e azione */
        ObservableBooleanValue pickedTrigger = Bindings.or(pickedTriggerTime, pickedTriggerDay);
        ObservableBooleanValue pickedAction = actionPickerComboBox.valueProperty().isNotNull();


        addRuleBTN.disableProperty().bind(Bindings.not(Bindings.and(
                    pickedTrigger,pickedAction
                )
        ));


    }
}
