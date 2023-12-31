package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCounterController implements Initializable {

    @FXML
    private TextField inputTextFieldName;

    @FXML
    private TextField inputTextFieldValue;

    @FXML
    private Button addCounterBtn;

    @FXML
    void addCounter(ActionEvent event) {
        Counter counter = new Counter(inputTextFieldName.getText(),Integer.parseInt(inputTextFieldValue.getText()));
        if(CountersManager.getInstance().contains(counter)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create counter");
            alert.setHeaderText(null);
            alert.setContentText("Error during the creation of the counter, name already used");
            alert.showAndWait();
            return;
        }
        CountersManager.getInstance().addCounter(counter);

        /*Chiudo la finestra Aggiungi contatore dopo aver premuto il pulsante*/
        ((Stage) addCounterBtn.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inputTextFieldValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*")) {
                inputTextFieldValue.setText(newValue.replaceAll("[^\\d-]", ""));
            }
        });


        /*codice per il controllo di input sbagliati*/
        addCounterBtn.disableProperty().bind(
                Bindings.or(
                        inputTextFieldName.textProperty().isEmpty(),
                        inputTextFieldValue.textProperty().isEmpty()
                )
        );






    }
}
