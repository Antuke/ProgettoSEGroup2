package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderActionDialogBox implements RenderAction{

    private ActionInterface action  = null;

    @Override
    public void render(VBox parent) {
        Label text = new Label("Prompt default message: memo");
        text.setPrefWidth(260);
        text.setWrapText(true);
        Button addActionBtn = new Button("Add Action");
        addActionBtn.setPrefWidth(260);
        addActionBtn.setAlignment(Pos.BASELINE_CENTER);
        addActionBtn.setOnAction(e->{
            action = new ActionDialogBox();
            ((Stage) addActionBtn.getScene().getWindow()).close();
        });
        parent.getChildren().addAll(text, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
