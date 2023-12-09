package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.ActionSetCounter;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Launcher;
import com.segroup2.progettosegroup2.Managers.CountersManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderActionSetCounter implements RenderAction {
    private ActionInterface action;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();

    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);
        HBox box = new HBox();
        box.setId("counter");
        // Elemento per la selezione del counter
        ComboBox<Counter> counterComboBox= new ComboBox<>();
        counterComboBox.setItems(CountersManager.getInstance().getCounters());
        counterComboBox.setPromptText("Choose counter");

        // Elemento per la scelta del valore
        TextField value= new TextField();
        value.setPromptText("Value");
        value.textProperty().addListener((observable, oldValue, newValue) -> {
            if ( !newValue.matches("-?\\d*") ){
                value.setText(oldValue);
            }
        });

        // Pulsante di aggiunta dell'azione
        Button addButton= new Button("Add action");
        addButton.setId("pref-width");
        addButton.setOnAction( (ActionEvent e) -> {
            action= new ActionSetCounter( counterComboBox.getValue(), Integer.parseInt(value.getText()) );
            ( (Stage) addButton.getScene().getWindow() ).close();
        });

        // Allineamento elementi
        box.getChildren().addAll(counterComboBox, value);

        // Disabilita il pulsante in certe condizioni + aggiunta nodi a parent
        addButton.disableProperty().bind( counterComboBox.valueProperty().isNull().or( value.textProperty().isEmpty() ) );
        parent.getChildren().addAll(box,addButton);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
