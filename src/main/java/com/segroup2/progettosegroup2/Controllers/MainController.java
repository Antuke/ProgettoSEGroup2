package com.segroup2.progettosegroup2.Controllers;
import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Threads.MainThread;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /* ID ELEMENTI TABELLA */
    @FXML
    private TableView<Rule> RuleTable;

    @FXML
    private TableColumn<Rule, TriggerInterface> TriggerCLM;

    @FXML
    private TableColumn<Rule, ActionInterface> ActionCLM;

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

            /* Non permette all'utente di interagire con la main-view */
            addRuleStage.initModality(Modality.APPLICATION_MODAL);
            addRuleStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Inizializzazione Tabella Regole */
        RulesManager rm = RulesManager.getInstance();
        TriggerCLM.setCellValueFactory(new PropertyValueFactory<Rule,TriggerInterface>("trigger"));
        ActionCLM.setCellValueFactory(new PropertyValueFactory<Rule,ActionInterface>("action"));

        TriggerTime t = new TriggerTime(13,20);
        ActionAudio a = new ActionAudio();
        Rule testR = new Rule(t,a);

        ObservableList<Rule> tableValue = FXCollections.observableArrayList(rm.getRules());
        RuleTable.setItems(tableValue);
        rm.addRule(testR);

        /* Inizializzazione Thread */
        Thread thread = new Thread(new MainThread(rm.getRules()));
        thread.run();

    }
}
