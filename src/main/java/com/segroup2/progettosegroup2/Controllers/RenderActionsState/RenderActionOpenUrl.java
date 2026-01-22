package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.ActionOpenUrl;
import com.segroup2.progettosegroup2.Launcher;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenderActionOpenUrl implements RenderAction {

    private ActionInterface action = null;
    private final static String containerStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();

    @Override
    public void render(VBox parent) {
        parent.getStylesheets().add(containerStyle);

        TextField urlField = new TextField();
        urlField.setPromptText("Enter URL (e.g., https://www.google.com)");
        urlField.setPrefWidth(260); // Manual width to match others if CSS ID not sufficient or unknown

        Button addAction = new Button("Add Action");
        addAction.setPrefWidth(260);
        addAction.setOnAction(e -> {
            action = new ActionOpenUrl(urlField.getText());
            ((Stage) addAction.getScene().getWindow()).close();
        });

        // Disable button if URL is empty
        addAction.disableProperty().bind(urlField.textProperty().isEmpty());

        parent.getChildren().addAll(urlField, addAction);
    }

    @Override
    public ActionInterface getAction() {
        return action;
    }
}
