package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionAddConstantToCounter;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderAddConstanstCounter implements RenderAction{

    private ActionInterface action  = null;

    @Override
    public void render(VBox parent) {


        ComboBox<Counter> counterCB = new ComboBox<>();
        counterCB.setItems(CountersManager.getInstance().getCounters());
        counterCB.setPromptText("Scegli il counter");
        TextField constant = new TextField();
        constant.setPromptText("Inserisci valore intero");
        constant.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*")) {
                constant.setText(newValue.replaceAll("[^\\d-]", ""));
            }
        });

        Button addAction = new Button("Add action");
        addAction.setOnAction( e ->{
            action = new ActionAddConstantToCounter(constant.getText(),counterCB.getValue());
            ((Stage) addAction.getScene().getWindow()).close();
        });

        addAction.disableProperty().bind(counterCB.valueProperty().isNull());

        parent.getChildren().addAll(counterCB, constant,addAction);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
