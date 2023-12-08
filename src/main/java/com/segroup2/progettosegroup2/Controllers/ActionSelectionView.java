package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Controllers.RenderActionsState.*;
import com.segroup2.progettosegroup2.Launcher;
import com.segroup2.progettosegroup2.MainApplication;
import javafx.collections.FXCollections;
import javafx.css.Style;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActionSelectionView {

    private final ComboBox<String> actionList;
    private final TextArea actionDefinitionResume;



    public ActionSelectionView(){

        actionList = new ComboBox<>();
        actionList.setItems(FXCollections.observableArrayList(ActionEnum.stringValues()));
        actionList.setPromptText("Seleziona un'azione");
        actionDefinitionResume = new TextArea();
        actionDefinitionResume.setEditable(false);
        actionDefinitionResume.setWrapText(true);
    }


    public ActionInterface createActionDefinitionView() {
        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(20);
        String btnStyle = Launcher.class.getResource("Styles/ButtonStyle.css").toString();
        String textStyle = Launcher.class.getResource("Styles/TextStyle.css").toString();
        main.getStylesheets().addAll(btnStyle,textStyle);
        VBox actionChoice = new VBox();
        actionChoice.setAlignment(Pos.CENTER);
        actionChoice.setSpacing(20);
        ActionContext context = new ActionContext();
        actionList.setOnAction(e->{
            //Prima di caricare la nuova view elimino quella già presente
            actionChoice.getChildren().clear();
            RenderAction render = switch (ActionEnum.fromMessage(actionList.getValue())){
                case ACTION_DEFAULT_AUDIO -> new RenderActionAudioView();
                case ACTION_DEFAULT_DIALOGBOX -> new RenderActionDialogBox();
                case ACTION_DELETE_FILE -> new RenderActionDeleteFile();
                case ACTION_APPEND_TO_FILE -> new RenderActionAppendToFile();
                case ACTION_ADD_CONSTANT -> new RenderAddConstanstCounter();
                case ACTION_SUM_COUNTER -> new RenderSumCounters();
                case ACTION_MOVE_FILE -> new RenderActionMoveFile();
                case ACTION_COPY_FILE -> new RenderActionCopyFile();
                default -> null;
            };
            context.setState(render);
            context.getState().render(actionChoice);
        });
        main.getChildren().addAll(actionList,actionChoice);

        Stage s = new Stage();
        Scene scene = new Scene(main, 500,300);
        s.setScene(scene);
        s.setTitle("Action definition");
        s.setAlwaysOnTop(true);
        s.initModality(Modality.APPLICATION_MODAL);
        s.showAndWait();
        return context.getReturnAction();
    }
}
