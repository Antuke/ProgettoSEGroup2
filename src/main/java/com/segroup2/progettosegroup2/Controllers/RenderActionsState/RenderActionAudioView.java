package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderActionAudioView implements RenderAction {

    private ActionInterface action  = null;


    @Override
    public void render(VBox parent) {
        Label text = new Label("Play default audio: default_audio.wav");
        Button addActionBtn = new Button("Add Action");
        addActionBtn.setOnAction(e->{
            action = new ActionAudio();
            ((Stage) addActionBtn.getScene().getWindow()).close();
        });
        parent.getChildren().addAll(text, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
