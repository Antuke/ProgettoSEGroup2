package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Controllers.RenderTriggerState.*;
import com.segroup2.progettosegroup2.Triggers.*;
import com.segroup2.progettosegroup2.Triggers.Equation.TriggerAnd;
import com.segroup2.progettosegroup2.Triggers.Equation.TriggerNot;
import com.segroup2.progettosegroup2.Triggers.Equation.TriggerOr;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TriggerSelectionView {

    private ComboBox<String> triggersList;
    private TextArea triggerDefinitionResume;
    private TriggerInterface tempTrigger;
    private TriggerInterface trigger;
    private CheckBox notCheckBox;
    private Button andBtn;
    private Button orBtn;
    private Button simpleTriggerBtn;

    public TriggerSelectionView(){
        trigger = null;
        tempTrigger = null;
        triggersList = new ComboBox<>();
        triggersList.setItems(FXCollections.observableArrayList(TriggerEnum.stringValues()));
        triggersList.setPromptText("Seleziona un trigger");
        triggerDefinitionResume = new TextArea();
        triggerDefinitionResume.setEditable(false);
        triggerDefinitionResume.setWrapText(true);
    }

    public TriggerInterface createView(){
        Stage stage = new Stage();
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setDividerPositions(0.80);
        Scene scene = new Scene(root,400,300);

        // Creazione UpperPane
        VBox upperPane = new VBox();
        upperPane.getChildren().add(triggerDefinitionResume);

        // Creazione BottomPane
        HBox bottomPane = new HBox();
        bottomPane.setSpacing(20);
        bottomPane.setAlignment(Pos.CENTER);
        andBtn = new Button("And");
        orBtn = new Button("Or");
        notCheckBox = new CheckBox("Not");
        simpleTriggerBtn = new Button("Simple");
        Button confirmBtn = new Button("Conferma Trigger");

        andBtn.setDisable(true);
        orBtn.setDisable(true);

        andBtn.setOnAction(this::andAction);
        orBtn.setOnAction(this::orAction);
        simpleTriggerBtn.setOnAction(this::createSimpleTrigger);
        confirmBtn.setOnAction(e->stage.close());

        bottomPane.getChildren().addAll(simpleTriggerBtn, andBtn, orBtn, notCheckBox, confirmBtn);

        root.getItems().addAll(upperPane, bottomPane);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return trigger;
    }

    /** Aggiunta di un trigger ti tipo unario. Varrà aggiornato {@link #trigger} con il nuovo trigger */
    private void createSimpleTrigger(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        if(tempTrigger!=null){
            if (notCheckBox.isSelected())
                trigger = new TriggerNot(tempTrigger);
            else
                trigger = tempTrigger;
            updateTriggerDefinitionResume();
            simpleTriggerBtn.setDisable(true);
            andBtn.setDisable(false);
            orBtn.setDisable(false);
            tempTrigger= null;
        }
    }

    /** Aggiunta di un trigger ti tipo binario OR. Varrà aggiornato {@link #trigger} con il nuovo trigger */
    private void orAction(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        if(tempTrigger != null){
            TriggerInterface triggerOr = new TriggerOr();
            triggerOr.add(trigger);
            if(notCheckBox.isSelected())
                triggerOr.add(new TriggerNot(tempTrigger));
            else
                triggerOr.add(tempTrigger);

            trigger = triggerOr;
            updateTriggerDefinitionResume();
            tempTrigger =null;
        }
    }

    /** Aggiunta di un trigger ti tipo binario AND. Varrà aggiornato {@link #trigger} con il nuovo trigger */
    private void andAction(ActionEvent actionEvent) {
        createTriggerDefinitionView();
        if (tempTrigger != null) {
            TriggerInterface triggerAnd = new TriggerAnd();
            triggerAnd.add(trigger);
            if (notCheckBox.isSelected())
                triggerAnd.add(new TriggerNot(tempTrigger));
            else
                triggerAnd.add(tempTrigger);
            trigger = triggerAnd;
            updateTriggerDefinitionResume();
            tempTrigger = null;
        }
    }

    /**
     * Aggiorna la l'oggetto {@link #triggerDefinitionResume} mostrando a video
     * il testo restutito da {@link #trigger}
     */
    private void updateTriggerDefinitionResume(){
        triggerDefinitionResume.clear();
        triggerDefinitionResume.setText(trigger.toString());
    }
    private void createTriggerDefinitionView() {
        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(20);
        VBox triggerChoice = new VBox();
        triggerChoice.setAlignment(Pos.CENTER);
        triggerChoice.setSpacing(20);
        TriggerContext context = new TriggerContext();
        triggersList.setOnAction(e->{
            //Prima di caricare la nuova view elimino quella già presente
            triggerChoice.getChildren().clear();
            var render = switch (TriggerEnum.fromMessage(triggersList.getValue())){
                case TRIGGER_TIME_OF_DAY -> new RenderTriggerTime();
                case TRIGGER_DATE -> new RenderTriggerDate();
                case TRIGGER_DAY_OF_WEEK -> new RenderTriggerDayOfWeek();
                case TRIGGER_DAY_OF_MONTH -> new RenderTriggerDayOfMonth();
                case TRIGGER_FILE_SIZE -> new RenderTriggerFileSize();
                case TRIGGER_FILE_EXISTS -> new RenderTriggerFileExists();
                case TRIGGER_COMPARE_COUNTERS ->  new RenderTriggerCompareCounters();
                case TRIGGER_COMPARE_COUNTER_AND_VALUE -> new RenderTriggerCompareCounterAndValue();


            };

            context.setState(render);
            context.getState().render(triggerChoice);
        });
        main.getChildren().addAll(triggersList,triggerChoice);

        Stage s = new Stage();
        Scene scene = new Scene(main, 300,300);
        s.setScene(scene);
        s.setTitle("Trigger definition");
        s.setAlwaysOnTop(true);
        s.showAndWait();
        tempTrigger = context.getReturnTrigger();
    }
}
