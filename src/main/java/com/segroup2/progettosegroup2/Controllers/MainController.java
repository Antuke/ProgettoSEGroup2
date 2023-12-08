package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Threads.MainThread;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.beans.property.SimpleStringProperty;
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

    @FXML
    private TableColumn<Rule, ActionInterface> actionClm;

    @FXML
    private TableColumn<Rule, Boolean> activeClm;

    @FXML
    private TableView<Counter> counterTable;

    @FXML
    private TableColumn<Counter, String> nameCLM;

    @FXML
    private TableView<Rule> ruleTable;

    @FXML
    private TableColumn<Rule, TriggerInterface> triggerClm;

    @FXML
    private TableColumn<Rule, String> typeClm;

    @FXML
    private TableColumn<Counter, Integer> valueCLM;

    @FXML
    void openCreateRuleAction(ActionEvent event) {
        openNewStage("add-rule-box.fxml","Definisci la regola");
        for(Rule r : RulesManager.getInstance().getRules()){
            r.subscribe(new TableViewListener(ruleTable));
        }
    }

    @FXML
    void openCreateViewCounters(ActionEvent event) {
        openNewStage("add-counter-box.fxml", "Definisci il contatore");
        for(Counter c : CountersManager.getInstance().getCounters()){
            c.subscribe(new TableViewListener(counterTable));
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
        typeClm.setCellValueFactory(cellData -> new SimpleStringProperty(
                switch (cellData.getValue().getClass().getCanonicalName()){
                    case "com.segroup2.progettosegroup2.Rules.Rule" -> "Normal";
                    case "com.segroup2.progettosegroup2.Rules.SingleRule" -> "Single";
                    default -> "Sleeping";
        }));
        triggerClm.setCellValueFactory(new PropertyValueFactory<Rule,TriggerInterface>("trigger"));

        activeClm.setEditable(true);
        actionClm.setCellValueFactory(new PropertyValueFactory<Rule,ActionInterface>("action"));
        activeClm.setCellFactory(col -> {
            CheckBoxTableCell<Rule, Boolean> cell = new CheckBoxTableCell<Rule, Boolean>() {
                private CheckBox checkBox = new CheckBox();

                /*  Il metodo updateItem viene chiamato ogni volta che la tabella invoca il refresh
                 *  essendo la tabella iscritta al cambiamento su ogni regola quando si verifica una
                 *  modifica nello stato active della regola la tabella esegue l'update aggiornando il
                 *  valore della checkbox. Grazie al metodo onAction invece si può cambiare lo stato
                 *  della regola attraverso la checkbox
                 */
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!isEmpty()) {
                        Rule rule = getTableView().getItems().get(getIndex());
                        checkBox.setSelected(rule.isActive());
                        setGraphic(checkBox);
                        // Aggiungi un listener al clic della CheckBox
                        checkBox.setOnAction(event -> {
                            rule.setActive(checkBox.isSelected());
                        });
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });

        ruleTable.setItems(RulesManager.getInstance().getRules());

        ruleTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );


        /*Iscrivo la ruleTalbe alle regole caricate da file*/
        for(Rule r: RulesManager.getInstance().getRules()){
            r.subscribe(new TableViewListener(ruleTable));
        }

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

        /*Iscrivo la countTable ai counter caricati da file */
        for(Counter c : CountersManager.getInstance().getCounters()){
            c.subscribe(new TableViewListener(counterTable));
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
    private void openNewStage(String resourceName, String stageTitle){
        try {
            /*Prendo il path dove è contenuta la view da aprire*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/segroup2/progettosegroup2/"+resourceName));
            /*Apertura view*/
            Parent root = loader.load();
            Stage addRuleStage = new Stage();
            Scene scene = new Scene(root);
            addRuleStage.setTitle(stageTitle);
            addRuleStage.setScene(scene);

            /* Non permette all'utente di interagire con la main-view mentre è aperta la view di creazione regola*/
            addRuleStage.initModality(Modality.APPLICATION_MODAL);
            addRuleStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showInfoAction(ActionEvent event) {
        ObservableList<Rule> selected = ruleTable.getSelectionModel().getSelectedItems();
        if (selected.size() == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info Regola");
            alert.setContentText(selected.get(0).getDetail());
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Info Regola");
            alert.setContentText("Per visualizzare le informazioni selezionare una regola per volta");
            alert.showAndWait();
        }
    }


}

