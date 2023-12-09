package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Counters.CounterCompareEnum;
import com.segroup2.progettosegroup2.Launcher;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import com.segroup2.progettosegroup2.Triggers.TriggerCompareCounterAndValue;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderTriggerCompareCounterAndValue implements RenderTrigger{
    private TriggerCompareCounterAndValue trigger = null;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();

    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        ComboBox<CounterCompareEnum> operatore = new ComboBox<>();
        operatore.setPromptText("Operator");
        operatore.setItems(FXCollections.observableArrayList(CounterCompareEnum.values()));

        ComboBox<Counter> counter = new ComboBox<>();
        counter.setPromptText("Counter");
        TextField value = new TextField();

        counter.setItems(CountersManager.getInstance().getCounters());
        value.textProperty().addListener( (observable, oldValue, newValue)->{
            if ( !newValue.matches("-?\\d*") ) {
                value.setText(oldValue);
            }
        });

        HBox box = new HBox();
        box.setId("hbox-3-child");

        box.getChildren().addAll(counter,operatore,value);

        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setId("pref-width");
        addTriggerBtn.setOnAction(e->{
            trigger = new TriggerCompareCounterAndValue(counter.getValue(), Integer.parseInt(value.getText()), operatore.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
        });

        addTriggerBtn.disableProperty().bind(
                counter.valueProperty().isNull().or(value.textProperty().isNull()).or(operatore.valueProperty().isNull())
        );

        parent.getChildren().addAll(box,addTriggerBtn);

    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
