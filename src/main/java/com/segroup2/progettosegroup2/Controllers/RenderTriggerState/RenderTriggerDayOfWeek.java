package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerDayOfWeek;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.DayOfWeek;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerDayOfWeek}
 */
public class RenderTriggerDayOfWeek implements RenderTrigger{
    private TriggerDayOfWeek trigger = null;
    @Override
    public void render(VBox parent) {
        ComboBox<DayOfWeek> list = new ComboBox<>();
        list.setPromptText("Choose day");
        list.setItems(FXCollections.observableArrayList(DayOfWeek.values()));
        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            trigger = new TriggerDayOfWeek(list.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });
        addTriggerBtn.disableProperty().bind(list.valueProperty().isNull());
        parent.getChildren().addAll(list, addTriggerBtn);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}