package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Controllers.RenderActionsState.*;
import com.segroup2.progettosegroup2.Launcher;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActionSelectionView {

    private final ComboBox<String> actionList;
    private final TextArea actionDefinitionResume;
    private final static String btnStyle = Launcher.class.getResource("Styles/ButtonStyle.css").toString();
    private final static String textStyle = Launcher.class.getResource("Styles/TextStyle.css").toString();
    private final static String comboBoxStyle = Launcher.class.getResource("Styles/ComboBoxStyle.css").toString();
    private final static String boxStyle = Launcher.class.getResource("Styles/BoxStyle.css").toString();




    public ActionSelectionView(){

        actionList = new ComboBox<>();
        actionList.setItems(FXCollections.observableArrayList(ActionEnum.stringValues()));
        actionList.setPromptText("Pick an action");
        actionList.setPrefWidth(260);

        actionDefinitionResume = new TextArea();
        actionDefinitionResume.setEditable(false);
        actionDefinitionResume.setWrapText(true);
    }


    public ActionInterface createActionDefinitionView() {
        VBox main = new VBox();
        main.setId("main-vbox");
        main.getStylesheets().addAll(btnStyle,textStyle,comboBoxStyle,boxStyle);
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
                case ACTION_SET_COUNTER -> new RenderActionSetCounter();
                case ACTION_EXECUTE_PROGRAM -> new RenderActionExecuteProgram();
            };
            context.setState(render);
            context.getState().render(actionChoice);
        });
        main.getChildren().addAll(actionList,actionChoice);

        Stage s = new Stage();
        Scene scene = new Scene(main, 300,350);
        s.setScene(scene);
        s.setTitle("Action definition");
        s.initModality(Modality.APPLICATION_MODAL);
        s.showAndWait();
        return context.getReturnAction();
    }
}
