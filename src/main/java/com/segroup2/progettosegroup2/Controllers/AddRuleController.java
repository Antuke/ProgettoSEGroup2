package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.Sequence.ActionComposite;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Rules.SingleRule;
import com.segroup2.progettosegroup2.Rules.SleepingRule;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRuleController implements Initializable {

    @FXML
    private TextArea actionsTextArea;

    @FXML
    private Button addRuleBTN;

    @FXML
    private TextArea triggersTextArea;

    @FXML
    private ToggleGroup radioButtonGroup;

    @FXML
    private TextField sleepDayField;

    @FXML
    private TextField sleepHourField;

    @FXML
    private TextField sleepMinutesField;

    @FXML
    private RadioButton normalRuleRadioBtn;

    @FXML
    private RadioButton sleepingRuleRadioBtn;

    @FXML
    private RadioButton singleTimeRuleRadioBtn;

    private ActionComposite actions;
    private TriggerInterface trigger;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
        actions = new ActionComposite();
        initBindings();
    }

    @FXML
    void commitRule(ActionEvent event) {
        Rule rule = switch (((RadioButton) radioButtonGroup.getSelectedToggle()).getText().toLowerCase()){
            case "normal" ->  new Rule(trigger,actions);
            case "single" -> new SingleRule(trigger,actions);
            case "sleeping" -> {
                int day = Integer.parseInt(sleepDayField.getText());
                int hh = Integer.parseInt(sleepHourField.getText());
                int mm =Integer.parseInt(sleepMinutesField.getText());
                yield new SleepingRule(trigger,actions,day,hh, mm);
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + ((RadioButton) radioButtonGroup.getSelectedToggle()).getText().toLowerCase());
        };

        ((Stage) triggersTextArea.getScene().getWindow()).close();
        RulesManager.getInstance().addRule(rule);


    }



    @FXML
    void openAddAction(ActionEvent event) {
        ActionSelectionView actionView = new ActionSelectionView();
        ActionInterface action = actionView.createActionDefinitionView();

        if(action!= null) {
            actions.add(action);
            actionsTextArea.setText(actions.toString());
        }

    }

    @FXML
    void openAddTrigger(ActionEvent event) {
        trigger = new TriggerSelectionView().createView();
        triggersTextArea.setWrapText(true);
        if(trigger!=null)
            triggersTextArea.setText(trigger.toString());
    }

    private void initBindings(){
        /* Toggle group per and e or */
        radioButtonGroup = new ToggleGroup();
        normalRuleRadioBtn.setToggleGroup(radioButtonGroup);
        sleepingRuleRadioBtn.setToggleGroup(radioButtonGroup);
        singleTimeRuleRadioBtn.setToggleGroup(radioButtonGroup);
        sleepHourField.disableProperty().bind(sleepingRuleRadioBtn.selectedProperty().not());
        sleepDayField.disableProperty().bind(sleepingRuleRadioBtn.selectedProperty().not());
        sleepMinutesField.disableProperty().bind(sleepingRuleRadioBtn.selectedProperty().not());
        ObservableBooleanValue sleepingBind = sleepingRuleRadioBtn.selectedProperty().and(sleepMinutesField.textProperty().isEmpty().or(sleepHourField.textProperty().isEmpty().or(sleepDayField.textProperty().isEmpty())));
        addRuleBTN.disableProperty().bind(actionsTextArea.textProperty().isEmpty().or(triggersTextArea.textProperty().isEmpty()).or(sleepingBind));

    }

}
