package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.Sequence.ActionComposite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        initBindings();
    }

    @FXML
    void commitRule(ActionEvent event) {

    }

    @FXML
    void openAddAction(ActionEvent event) {

    }

    @FXML
    void openAddTrigger(ActionEvent event) {

    }

    private void initBindings(){
        /* Toggle group per and e or */
        toggleGroup = new ToggleGroup();
        radioAnd.setToggleGroup(toggleGroup);
        radioOr.setToggleGroup(toggleGroup);



    }

}
