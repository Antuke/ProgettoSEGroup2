package com.segroup2.progettosegroup2.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {

    /* ID ELEMENTI TABELLA */
    @FXML
    private TableColumn<?, ?> ActionCLM;

    @FXML
    private TableView<?> RuleTable;

    @FXML
    private TableColumn<?, ?> TriggerCLM;

    /* ID BOTTONE */
    @FXML
    private Button AddRuleBTN;

    @FXML
    void OpenCreateViewActions(ActionEvent event) {

    }

}
