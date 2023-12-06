package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;

public class RenderTriggerTime implements RenderTrigger{
    private TriggerInterface trigger = null;
    @Override
    public void render(VBox parent) {
        LocalTime time = LocalTime.now();

        TextField hour = new TextField();
        hour.setText(String.valueOf(time.getHour()));
        // Controllo sui valori inseriti nella casella delle ore
        hour.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                hour.setText(newValue.replaceAll("\\D", ""));
                return;
            }

            if(!newValue.isBlank() )
                if (Integer.parseInt(newValue) > 23 || newValue.length() > 2)
                    hour.setText(newValue.substring(0, newValue.length() - 1));
        });

        TextField minute = new TextField();
        minute.setText(String.valueOf(time.getMinute()));
        // Controllo sui valori inseriti nella casella minuti
        minute.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("\\d*") ) {
                minute.setText(newValue.replaceAll("\\D", ""));
                return;
            }

            if( !newValue.isBlank() )
                if (Integer.parseInt(newValue) > 59 || newValue.length() > 2)
                    minute.setText(newValue.substring(0, newValue.length() - 1));
        });

        Label l = new Label(":");

        HBox box = new HBox();
        box.setSpacing(20);
        box.getChildren().addAll(hour,l,minute);
        Button addTriggerBtn = new Button("Add Trigger");

        addTriggerBtn.setOnAction(e->{
            int hh = Integer.parseInt(hour.getText());
            int mm = Integer.parseInt(minute.getText());

            ((Stage) addTriggerBtn.getScene().getWindow()).close();
            trigger = new TriggerTime(hh, mm);
        });
        addTriggerBtn.disableProperty().bind(hour.textProperty().isEmpty().or(minute.textProperty().isEmpty()));
        parent.getChildren().addAll(box, addTriggerBtn);
    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
