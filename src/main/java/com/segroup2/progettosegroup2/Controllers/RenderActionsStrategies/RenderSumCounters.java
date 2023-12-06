package com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.ActionSumBetweenCounters;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import com.segroup2.progettosegroup2.Triggers.TriggerDate;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderSumCounters implements RenderAction{

    private ActionInterface action;
    @Override
    public void render(VBox parent) {
        ComboBox<Counter> counterOne = new ComboBox<>();
        counterOne.setItems(CountersManager.getInstance().getCounters());

        ComboBox<Counter> counterTwo = new ComboBox<>();
        counterTwo.setItems(CountersManager.getInstance().getCounters());


        Button addAction = new Button("Aggiungi azione");
        addAction.setOnAction( e -> {
            action = new ActionSumBetweenCounters(counterOne.getValue(),counterTwo.getValue());
            ((Stage) addAction.getScene().getWindow()).close();

        });

        addAction.disableProperty().bind(Bindings.and(counterTwo.valueProperty().isNotNull(),counterOne.valueProperty().isNotNull()));
        parent.getChildren().addAll(counterOne, counterTwo,addAction);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
