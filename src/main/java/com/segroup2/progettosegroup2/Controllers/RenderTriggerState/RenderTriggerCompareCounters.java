package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Counters.CounterCompareEnum;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import com.segroup2.progettosegroup2.Triggers.TriggerCompareCounters;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RenderTriggerCompareCounters implements RenderTrigger{
    private TriggerCompareCounters trigger = null;


    @Override
    public void render(VBox parent) {
        ComboBox<CounterCompareEnum> operatore = new ComboBox<>();
        operatore.setItems(FXCollections.observableArrayList(CounterCompareEnum.values()));

        ComboBox<Counter> operando1 = new ComboBox<>();
        ComboBox<Counter> operando2 = new ComboBox<>();

        operando1.setItems(CountersManager.getInstance().getCounters());
        operando2.setItems(CountersManager.getInstance().getCounters());

        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        box.getChildren().addAll(operando1,operatore,operando2);

        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setOnAction(e->{
            trigger =new TriggerCompareCounters(operando1.getValue(),operando2.getValue(), operatore.getValue());
        });

        addTriggerBtn.disableProperty().bind(
                operando1.valueProperty().isNull().or(operando2.valueProperty().isNull()).or(operatore.valueProperty().isNull())
        );
        parent.getChildren().addAll(box,addTriggerBtn);

    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
