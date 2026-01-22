package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionDisplayMessage;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderActionDisplayMessage implements RenderAction {

    private ActionInterface action = null;

    @Override
    public void render(VBox parent) {
        Label text = new Label("Enter the message (max 255 chars):");
        text.setPrefWidth(260);
        text.setWrapText(true);

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Enter your message here...");
        messageArea.setPrefWidth(260);
        messageArea.setPrefHeight(100);
        messageArea.setWrapText(true);

        // Limit input to 255 characters
        messageArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 255) {
                messageArea.setText(oldValue);
            }
        });

        Button addActionBtn = new Button("Add Action");
        addActionBtn.setPrefWidth(260);
        addActionBtn.setAlignment(Pos.BASELINE_CENTER);
        addActionBtn.setOnAction(e -> {
            String message = messageArea.getText();
            if (message != null && !message.isEmpty()) {
                action = new ActionDisplayMessage(message);
                ((Stage) addActionBtn.getScene().getWindow()).close();
            } else {
                messageArea.setStyle("-fx-border-color: red;");
            }
        });
        
        messageArea.setOnKeyTyped(e -> messageArea.setStyle(""));

        parent.getChildren().addAll(text, messageArea, addActionBtn);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
