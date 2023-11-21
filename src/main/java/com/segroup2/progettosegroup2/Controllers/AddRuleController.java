package com.segroup2.progettosegroup2.Controllers;


import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Triggers.TriggerEnum;
import javafx.beans.Observable;
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

import java.net.URL;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddRuleController implements Initializable {

    private boolean triggerSet;
    private boolean actionSet;

    @FXML
    private ComboBox<?> actionPickerComboBox;

    @FXML
    private StackPane actionStackPane;

    @FXML
    private Button addRuleBTN;

    @FXML
    private Label deafultAudioLabel;

    @FXML
    private Label defaultLabelText;

    @FXML
    private ComboBox<?> hoursComboBox;

    @FXML
    private ComboBox<?> minutesComboBox;

    @FXML
    private HBox timePickerHBox;

    @FXML
    private ComboBox<?> triggerPickerComboBox;

    @FXML
    private StackPane triggerStackPane;

    private ObservableList<TriggerEnum> triggerPickerComboBoxValue;

    private ObservableList<ActionEnum> actionPickerComboBoxValue;

    @FXML
    void commitRule(ActionEvent event) {
        // Switch in cui vedo il triggerpicker scelto e prendo dalle cose che so che sono scelte (sicuro per via dei binding)
        //speculare per le actions


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* inizializzazione dei trigger e action picker */
        actionPickerComboBoxValue = FXCollections.observableArrayList(ActionEnum.values());
        triggerPickerComboBoxValue = FXCollections.observableArrayList(TriggerEnum.values());



        /* Bindings */
        timePickerHBox.visibleProperty().bind(triggerPickerComboBox.valueProperty().isEqualTo("Orario del giorno"));
        defaultLabelText.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo("Testo di default"));
        deafultAudioLabel.visibleProperty().bind(actionPickerComboBox.valueProperty().isEqualTo("Audio di default"));

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
