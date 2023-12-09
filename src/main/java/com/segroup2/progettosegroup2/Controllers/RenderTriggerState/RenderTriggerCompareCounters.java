package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Counters.CounterCompareEnum;
import com.segroup2.progettosegroup2.Launcher;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import com.segroup2.progettosegroup2.Triggers.TriggerCompareCounters;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderTriggerCompareCounters implements RenderTrigger{
    private TriggerInterface trigger = null;

    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();
    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        ComboBox<CounterCompareEnum> operatore = new ComboBox<>();
        operatore.setItems(FXCollections.observableArrayList(CounterCompareEnum.values()));
        operatore.setPromptText("Operator");
        ComboBox<Counter> operando1 = new ComboBox<>();
        operando1.setPromptText("First Operand");
        ComboBox<Counter> operando2 = new ComboBox<>();
        operando2.setPromptText("Second Operand");

        operando1.setItems(CountersManager.getInstance().getCounters());
        operando2.setItems(CountersManager.getInstance().getCounters());

        VBox box = new VBox();
        box.setId("trigger-counter");
        box.getChildren().addAll(operando1,operatore,operando2);

        Button addTriggerBtn = new Button("Add Trigger");
        addTriggerBtn.setId("pref-width");
        addTriggerBtn.setOnAction(e->{
            trigger =new TriggerCompareCounters(operando1.getValue(),operando2.getValue(), operatore.getValue());
            ((Stage) addTriggerBtn.getScene().getWindow()).close();
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
