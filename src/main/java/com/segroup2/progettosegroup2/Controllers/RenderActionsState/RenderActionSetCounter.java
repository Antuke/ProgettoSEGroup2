package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.ActionSetCounter;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderActionSetCounter implements RenderAction {
    private ActionInterface action;

    @Override
    public void render(VBox parent) {
        // Elemento per la selezione del counter
        ComboBox<Counter> counterComboBox= new ComboBox<>();
        counterComboBox.setItems(CountersManager.getInstance().getCounters());
        counterComboBox.setPromptText("Choose counter");

        // Elemento per la scelta del valore
        TextField value= new TextField();
        value.setPromptText("Value");
        value.setAlignment(Pos.CENTER);
        value.textProperty().addListener((observable, oldValue, newValue) -> {
            if ( !newValue.matches("-?\\d*") ){
                value.setText(oldValue);
            }
        });

        // Pulsante di aggiunta dell'azione
        Button addButton= new Button("Add action");
        addButton.setOnAction( (ActionEvent e) -> {
            action= new ActionSetCounter( counterComboBox.getValue(), Integer.parseInt(value.getText()) );
            ( (Stage) addButton.getScene().getWindow() ).close();
        });

        // Allineamento elementi
        VBox box= new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(55));
        box.getChildren().addAll(counterComboBox, value, addButton);

        // Disabilita il pulsante in certe condizioni + aggiunta nodi a parent
        addButton.disableProperty().bind( counterComboBox.valueProperty().isNull().or( value.textProperty().isEmpty() ) );
        parent.getChildren().addAll(box);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
