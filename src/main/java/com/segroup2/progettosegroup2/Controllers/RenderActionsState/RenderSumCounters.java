package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.ActionSumBetweenCounters;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Launcher;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderSumCounters implements RenderAction{

    private ActionInterface action;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();
    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        ComboBox<Counter> counterOne = new ComboBox<>();
        counterOne.setItems(CountersManager.getInstance().getCounters());
        counterOne.setPromptText("Counter to add the value to");
        counterOne.setId("pref-width");
        ComboBox<Counter> counterTwo = new ComboBox<>();
        counterTwo.setPromptText("Counter from which to withdraw the value");
        counterTwo.setItems(CountersManager.getInstance().getCounters());
        counterTwo.setId("pref-width");


        Button addAction = new Button("Add action");
        addAction.setId("pref-width");
        addAction.setOnAction( e -> {
            action = new ActionSumBetweenCounters(counterOne.getValue(),counterTwo.getValue());
            ((Stage) addAction.getScene().getWindow()).close();

        });

        addAction.disableProperty().bind(Bindings.or(counterTwo.valueProperty().isNull(),counterOne.valueProperty().isNull()));
        parent.getChildren().addAll(counterOne, counterTwo,addAction);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
