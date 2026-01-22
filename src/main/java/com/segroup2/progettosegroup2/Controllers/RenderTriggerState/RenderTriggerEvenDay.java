package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerEvenDay;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderTriggerEvenDay implements RenderTrigger{

    private TriggerInterface trigger = null;
    @Override
    public void render(VBox parent) {

        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setId("pref-width");
        addTriggerBtn.setOnAction(e->{
            trigger = new TriggerEvenDay();
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });

        parent.getChildren().add(addTriggerBtn);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }

}
