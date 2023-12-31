package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerDayOfMonth;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerDayOfMonth}
 */
public class RenderTriggerDayOfMonth implements RenderTrigger{
    private TriggerInterface trigger = null;

    @Override
    public void render(VBox parent) {
        ComboBox<Integer> dayOfMonth = new ComboBox<>();
        dayOfMonth.setPromptText("Choose day");

        Integer[] list = new Integer[31];
        for (int i=0; i<31; i++)
            list[i]= i+1;
        dayOfMonth.setItems(FXCollections.observableArrayList(list));
        dayOfMonth.setId("comboBox");

        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setId("pref-width");
        addTriggerBtn.setOnAction(e->{
            trigger = new TriggerDayOfMonth(dayOfMonth.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        addTriggerBtn.disableProperty().bind(dayOfMonth.valueProperty().isNull());
        parent.getChildren().addAll(dayOfMonth,addTriggerBtn);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
