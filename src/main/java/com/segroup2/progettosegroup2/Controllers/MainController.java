package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.CounterListnerInterface;
import com.segroup2.progettosegroup2.Managers.CountersManager;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    /* ID ELEMENTI TABELLA */
    @FXML
    private TableView<Rule> ruleTable;

    @FXML
    private TableView<Counter> counterTable;

    @FXML
    private TableColumn<Rule, TriggerInterface> triggerCLM;

    @FXML
    private TableColumn<Rule, ActionInterface> actionCLM;

    @FXML
    private TableColumn<Rule, Boolean> onOffCLM;

    @FXML
    private TableColumn<Counter, String> nameCLM;

    @FXML
    private TableColumn<Counter, Integer> valueCLM;

    /* ID BOTTONE */
    @FXML
    private Button addRuleBTN;

    @FXML
    private Button AddCounterBTN;

    @FXML
    private Button AddComplexRuleBTN;

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
            addRuleStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void openCreateRuleComplex(ActionEvent event) {
        openNewStage("add-rule-complex-box.fxml");
    }

        @FXML
    void openCreateRuleSimple(ActionEvent event) {
        openNewStage("add-rule-box.fxml");
    }


    @FXML
    void OpenCreateViewCounters(ActionEvent event) {
        try {
            /*Prendo il path dove è contenuta la view da aprire*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/segroup2/progettosegroup2/add-counter-box.fxml"));
            /*Apertura view*/
            Parent root = loader.load();
            Stage addCounterStage = new Stage();
            Scene scene = new Scene(root);
            addCounterStage.setTitle("Definisci il contatore");
            addCounterStage.setScene(scene);
            addCounterStage.setResizable(false);

            /* Non permette all'utente di interagire con la main-view mentre è aperta la view di creazione regola*/
            addCounterStage.initModality(Modality.APPLICATION_MODAL);

            addCounterStage.showAndWait();

            for(Counter c : CountersManager.getInstance().getCounters()){
                c.subscribe(new TableViewListner(counterTable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteRule(ActionEvent event) {
        ObservableList<Rule> toDelete =  ruleTable.getSelectionModel().getSelectedItems();

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

    @FXML
    void deleteCounter(ActionEvent event){
        ObservableList<Counter> toDelete = counterTable.getSelectionModel().getSelectedItems();

        if(toDelete == null){
            return;
        }

        /*Chiedo all'utente conferma*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancellazione Counter");
        alert.setContentText("Vuoi cancellare i counter selezionati?");
        Optional<ButtonType> scelta = alert.showAndWait();
        if(scelta.get() == ButtonType.OK){
            CountersManager.getInstance().removeAll(toDelete);
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

        /* Inizializzazione Tabella Counter */
        nameCLM.setCellValueFactory(new PropertyValueFactory<Counter,String>("name"));
        valueCLM.setCellValueFactory(new PropertyValueFactory<Counter,Integer>("value"));
        valueCLM.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Rendere la colonna editabile
        valueCLM.setEditable(true);

        valueCLM.setOnEditCommit(event -> {
            Counter item = event.getRowValue();
            item.setValue(event.getNewValue());
        });

        counterTable.setItems(CountersManager.getInstance().getCounters());

        /*Iscrivo la la countTable ai counter */
        for(Counter c : CountersManager.getInstance().getCounters()){
            c.subscribe(new TableViewListner(counterTable));
        }



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

