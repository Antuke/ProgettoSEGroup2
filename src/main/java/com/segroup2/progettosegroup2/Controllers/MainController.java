package com.segroup2.progettosegroup2.Controllers;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Threads.MainThread;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /* ID ELEMENTI TABELLA */
    @FXML
    private TableView<Rule> ruleTable;

    @FXML
    private TableColumn<Rule, TriggerInterface> triggerCLM;

    @FXML
    private TableColumn<Rule, ActionInterface> actionCLM;

    @FXML
    private TableColumn<Rule, Boolean> onOffCLM;

    /* ID BOTTONE */
    @FXML
    private Button addRuleBTN;

    @FXML
    private Button AddComplexRuleBTN;

    @FXML
    void openCreateRuleComplex(ActionEvent event) {
        openNewStage("add-rule-complex-box.fxml");
    }

        @FXML
    void openCreateRuleSimple(ActionEvent event) {
        openNewStage("add-rule-box.fxml");
    }

    @FXML
    void deleteRule(ActionEvent event) {
        ObservableList<Rule> toDelete =  ruleTable.getSelectionModel().getSelectedItems();;

        if(toDelete == null){
            return;
        }

        /*Chiedo all'utente conferma*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancellazione regola");
        alert.setContentText("Vuoi cancellare le regole selezionate?");
        Optional<ButtonType> scelta = alert.showAndWait();
        if(scelta.get() == ButtonType.OK){
            RulesManager.getInstance().removeAll(toDelete);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Inizializzazione Tabella Regole */
        triggerCLM.setCellValueFactory(new PropertyValueFactory<Rule,TriggerInterface>("trigger"));
        actionCLM.setCellValueFactory(new PropertyValueFactory<Rule,ActionInterface>("action"));

        onOffCLM.setCellValueFactory(cellData -> cellData.getValue().isActiveProperty());
        onOffCLM.setCellFactory(column -> new CheckBoxTableCell<>());

        onOffCLM.setOnEditCommit(event -> {
            Rule item = event.getRowValue();
            item.setActive(event.getNewValue());
        });

        // Rendere la colonna editabile
        onOffCLM.setEditable(true);

        ruleTable.setItems(RulesManager.getInstance().getRules());

        ruleTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        /* Inizializzazione Main Thread*/
        Thread thread = new Thread(new MainThread(RulesManager.getInstance().getRules()));
        thread.setDaemon(true);
        thread.start();

    }

    /**
     *
     * @param resourceName nome della risorsa da aprire
     * */
    private void openNewStage(String resourceName){
        try {
            /*Prendo il path dove è contenuta la view da aprire*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/segroup2/progettosegroup2/"+resourceName));
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
}
