package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerDate;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe per la corretta visualizzazione e scelta di un oggetto {@link TriggerDate}
 */
public class RenderTriggerDate implements RenderTrigger{
    private TriggerDate trigger = null;
    @Override
    public void render(VBox parent) {
        DatePicker date = new DatePicker();
        date.setEditable(false);
        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            trigger = new TriggerDate(date.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });

        addTriggerBtn.disableProperty().bind(date.valueProperty().isNull());
        parent.getChildren().addAll(date, addTriggerBtn);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
