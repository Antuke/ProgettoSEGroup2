package com.segroup2.progettosegroup2.Controllers;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Threads.MainThread;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /* ID ELEMENTI TABELLA */
    @FXML
    private TableView<Rule> RuleTable;

    @FXML
    private TableColumn<Rule, TriggerInterface> TriggerCLM;

    @FXML
    private TableColumn<Rule, ActionInterface> ActionCLM;

    @FXML
    private TableColumn<Rule, Boolean> OnOffCLM;

    /* ID BOTTONE */
    @FXML
    private Button AddRuleBTN;

    @FXML
    void OpenCreateViewActions(ActionEvent event) {
        try {
            /*Prendo il path dove è contenuta la view da aprire*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/segroup2/progettosegroup2/add-rule-box.fxml"));
            /*Apertura view*/
            Parent root = loader.load();
            Stage addRuleStage = new Stage();
            Scene scene = new Scene(root);
            addRuleStage.setTitle("Definisci la regola");
            addRuleStage.setScene(scene);

            /* Non permette all'utente di interagire con la main-view mentre è aperta la view di creazione regola*/
            addRuleStage.initModality(Modality.APPLICATION_MODAL);
            addRuleStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deactivateRule(ActionEvent event){
        Rule toDeactivate = getSelectRule();
        RulesManager.getInstance().deactivateRule(toDeactivate);
        RuleTable.refresh();
    }

    @FXML
    void activateRule(ActionEvent event){
        Rule toActivate = getSelectRule();
        RulesManager.getInstance().activateRule(toActivate);
        RuleTable.refresh();
    }
    @FXML
    void deleteRule(ActionEvent event) {
        Rule toDelete = getSelectRule();

        if(toDelete == null){
            return;
        }

        /*Chiedo all'utente conferma*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancellazione regola");
        alert.setContentText("Vuoi cancellare la seguente regola?\nTRIGGER:"+toDelete.getTrigger().toString()+"\nACTION:" + toDelete.getAction().toString());
        Optional<ButtonType> scelta = alert.showAndWait();
        if(scelta.get() == ButtonType.OK){
            RulesManager.getInstance().removeRule(toDelete);
        }
    }

    private Rule getSelectRule(){
        return RuleTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Inizializzazione Tabella Regole */
        TriggerCLM.setCellValueFactory(new PropertyValueFactory<Rule,TriggerInterface>("trigger"));
        ActionCLM.setCellValueFactory(new PropertyValueFactory<Rule,ActionInterface>("action"));
        OnOffCLM.setCellValueFactory(new PropertyValueFactory<Rule,Boolean>("active"));
        RuleTable.setItems(RulesManager.getInstance().getRules());

        /* Inizializzazione Main Thread*/
        Thread thread = new Thread(new MainThread(RulesManager.getInstance().getRules()));
        thread.setDaemon(true);
        thread.start();

    }
}
