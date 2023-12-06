package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.ActionSelectionView;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.Sequence.ActionComposite;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.TriggerSelectionView;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRuleComplexController implements Initializable {

    @FXML
    private TextArea actionsTextArea;

    @FXML
    private Button addActionBTN;

    @FXML
    private Button addRuleBTN;

    @FXML
    private Button addTriggerBTN;

    @FXML
    private TextArea triggersTextArea;

    @FXML
    private RadioButton radioAnd;

    @FXML
    private RadioButton radioOr;

    @FXML
    private ToggleGroup toggleGroup;

    private ActionComposite actions;
    private TriggerInterface trigger;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        actions = new ActionComposite();
        initBindings();
    }

    @FXML
    void commitRule(ActionEvent event) {
        RulesManager.getInstance().addRule(
                new Rule(trigger,actions)
        );
        ((Stage) triggersTextArea.getScene().getWindow()).close();
    }

    @FXML
    void openAddAction(ActionEvent event) {
        ActionSelectionView actionView = new ActionSelectionView();
        actions.add(actionView.createView());
        actionsTextArea.setText(actions.toString());
    }

    @FXML
    void openAddTrigger(ActionEvent event) {
        trigger = new TriggerSelectionView().createView();
        System.out.println(trigger);
        triggersTextArea.setWrapText(true);
        triggersTextArea.setText(trigger.toString());
    }

    private void initBindings(){
        /* Toggle group per and e or */
        toggleGroup = new ToggleGroup();
        radioAnd.setToggleGroup(toggleGroup);
        radioOr.setToggleGroup(toggleGroup);



    }

}
