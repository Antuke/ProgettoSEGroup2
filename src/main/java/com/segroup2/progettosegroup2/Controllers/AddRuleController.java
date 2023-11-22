package com.segroup2.progettosegroup2.Controllers;


import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.Factory.ActionFactory;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Triggers.TriggerEnum;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
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
    private ComboBox<Integer> hoursComboBox;

    @FXML
    private ComboBox<Integer> minutesComboBox;

    @FXML
    private HBox timePickerHBox;

    @FXML
    private ComboBox<TriggerEnum> triggerPickerComboBox;

    @FXML
    private StackPane triggerStackPane;

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
            case TRIGGER_TIME_OF_DAY -> new TriggerTime(hoursComboBox.getValue(), minutesComboBox.getValue());
        };
        Rule rule = new Rule(trigger,action);
        RulesManager.getInstance().addRule(rule);

        /*Chiudo la finestra Aggiungi regola dopo aver premuto il pulsante*/
        ((Stage) addRuleBTN.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* inizializzazione combobox ore e minuti */
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        // Populate the lists with values
        for (int i = 0; i <= 23; i++) {
            hours.add(i);
        }

        for (int i = 0; i <= 59; i++) {
            minutes.add(i);
        }
        hoursComboBox.setItems(hours);
        minutesComboBox.setItems(minutes);

        /* inizializzazione dei trigger e action picker */
        actionPickerComboBoxValue = FXCollections.observableArrayList(ActionEnum.values());
        triggerPickerComboBoxValue = FXCollections.observableArrayList(TriggerEnum.values());

        /* Aggiunta alle comboBox dei trigger e delle azione le possibili scelte */
        actionPickerComboBox.setItems(actionPickerComboBoxValue);
        triggerPickerComboBox.setItems(triggerPickerComboBoxValue);


        /* Bindings */
        timePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo(TriggerEnum.TRIGGER_TIME_OF_DAY));
        defaultLabelText.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_DIALOGBOX));
        deafultAudioLabel.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo(ActionEnum.ACTION_DEFAULT_AUDIO));

        /* Button Bindings */

        ObservableBooleanValue pickedTriggerTime = Bindings.and(
                timePickerHBox.visibleProperty(),
                Bindings.and(
                        hoursComboBox.valueProperty().isNotNull(),
                        minutesComboBox.valueProperty().isNotNull()
                )
        );

        ObservableBooleanValue pickedTigger = pickedTriggerTime; //in futuro aggiungo l'or con gli altri possibili trigger

        ObservableBooleanValue pickedAction = actionPickerComboBox.valueProperty().isNotNull();


        addRuleBTN.disableProperty().bind(Bindings.not(Bindings.and(
                pickedTriggerTime,pickedAction
                )
        ));

    }
}
