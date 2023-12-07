package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Actions.Sequence.ActionComposite;
import com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies.ActionContext;
import com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies.RenderActionAppendToFile;
import com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies.RenderAddConstanstCounter;
import com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies.RenderSumCounters;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActionSelectionView {

    private final ComboBox<ActionEnum> actionList;
    private final TextArea actionDefinitionResume;
    private ActionInterface action;
    private Button simpleActionBtn;

    public ActionSelectionView(){
        action = new ActionComposite();
        actionList = new ComboBox<>();
        actionList.setItems(FXCollections.observableArrayList(ActionEnum.values()));
        actionList.setPromptText("Seleziona una azione");
        actionDefinitionResume = new TextArea();
        actionDefinitionResume.setEditable(false);
        actionDefinitionResume.setWrapText(true);
    }


    private void createSimpleAction(ActionEvent actionEvent) {
        ActionInterface tempAction = createActionDefinitionView();
        if( tempAction==null )
            return;

        action.add(tempAction);

        updateActionDefinitionResume();

    }
    private void updateActionDefinitionResume(){
        actionDefinitionResume.clear();
        actionDefinitionResume.setText(action.toString());
    }

    public ActionInterface createActionDefinitionView() {
        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(20);
        VBox actionChoice = new VBox();
        actionChoice.setAlignment(Pos.CENTER);
        actionChoice.setSpacing(20);
        ActionContext context = new ActionContext();
        actionList.getSelectionModel().clearSelection();
        actionList.setOnAction(e->{
            //Prima di caricare la nuova view elimino quella già presente
            actionChoice.getChildren().clear();
            var render = switch (actionList.getValue()){
                case ACTION_APPEND_TO_FILE -> new RenderActionAppendToFile();
                case ACTION_ADD_CONSTANT -> new RenderAddConstanstCounter();
                case ACTION_SUM_COUTNER -> new RenderSumCounters();
                default -> null;
            };
            context.setState(render);
            context.getState().render(actionChoice);
        });
        main.getChildren().addAll(actionList,actionChoice);

        Stage s = new Stage();
        Scene scene = new Scene(main, 300,300);
        s.setScene(scene);
        s.setTitle("Action definition");
        s.setAlwaysOnTop(true);
        s.showAndWait();
        return( context.getReturnAction() );
    }
}
