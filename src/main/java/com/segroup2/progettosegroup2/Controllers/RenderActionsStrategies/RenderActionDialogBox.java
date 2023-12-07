package com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies;

import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RenderActionDialogBox implements RenderAction{

    private ActionInterface action  = null;

    @Override
    public void render(VBox parent) {
        Label text = new Label("Visualizzazione di un messaggio di dafault: promemoria");
        Button addActionBtn = new Button("Add Action");
        addActionBtn.setOnAction(e->action = new ActionDialogBox());
        parent.getChildren().addAll(text, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
