package com.segroup2.progettosegroup2.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/segroup2/progettosegroup2/add-rule-box.fxml"));
            Parent root = loader.load();
            Stage addRuleStage = new Stage();
            Scene scene = new Scene(root);
            addRuleStage.setScene(scene);
            addRuleStage.initModality(Modality.APPLICATION_MODAL);
            addRuleStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
