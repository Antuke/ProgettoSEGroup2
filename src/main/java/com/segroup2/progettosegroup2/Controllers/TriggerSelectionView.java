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
import javafx.stage.Stage;


public class TriggerSelectionView {
    private final ComboBox<TriggerEnum> triggersList;
    private final TextArea triggerDefinitionResume;
    private TriggerInterface trigger;
    private CheckBox notCheckBox;
    private Button andBtn;
    private Button orBtn;
    private Button simpleTriggerBtn;

    public TriggerSelectionView(){
        trigger = null;
        triggersList = new ComboBox<>();
        triggersList.setItems(FXCollections.observableArrayList(TriggerEnum.values()));
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
        stage.showAndWait();
        return trigger;
    }

    /** Aggiunta di un trigger ti tipo unario. Varrà aggiornato {@link #trigger} con il nuovo trigger */
    private void createSimpleTrigger(ActionEvent actionEvent) {
        TriggerInterface tempTrigger= createTriggerDefinitionView();
        if( tempTrigger==null )
            return;

        trigger= addNotIfNecessary(tempTrigger);

        updateTriggerDefinitionResume();
        simpleTriggerBtn.setDisable(true);
        andBtn.setDisable(false);
        orBtn.setDisable(false);
    }

    /** Aggiunta di un trigger ti tipo binario OR. Varrà aggiornato {@link #trigger} con il nuovo trigger */
    private void orAction(ActionEvent actionEvent) {
        TriggerInterface tempTrigger= createTriggerDefinitionView();
        if( tempTrigger==null )
            return;

        TriggerInterface triggerOr = new TriggerOr();
        triggerOr.add(trigger);
        triggerOr.add( addNotIfNecessary(tempTrigger) );

        trigger = triggerOr;

        updateTriggerDefinitionResume();
    }

    /** Aggiunta di un trigger ti tipo binario AND. Varrà aggiornato {@link #trigger} con il nuovo trigger */
    private void andAction(ActionEvent actionEvent) {
        TriggerInterface tempTrigger= createTriggerDefinitionView();
        if( tempTrigger==null )
            return;

        TriggerInterface triggerAnd = new TriggerAnd();
        triggerAnd.add(trigger);
        triggerAnd.add( addNotIfNecessary(tempTrigger) );

        trigger = triggerAnd;

        updateTriggerDefinitionResume();
    }

    /**
     * Decide se l'elemento da restituire deve essere di tipo {@link TriggerNot} oppure no
     * @param trigger trigger da incapsulare o meno il la classe di cui sopra
     * @return Il trigger che è stato incapsulato o meno la classe di cui sopra
     */
    private TriggerInterface addNotIfNecessary(TriggerInterface trigger){
        if (notCheckBox.isSelected())
            return( new TriggerNot(trigger) );
        return( trigger );
    }

    /**
     * Aggiorna la l'oggetto {@link #triggerDefinitionResume} mostrando a video
     * il testo restutito da {@link #trigger}
     */
    private void updateTriggerDefinitionResume(){
        triggerDefinitionResume.clear();
        triggerDefinitionResume.setText(trigger.toString());
    }

    /**
     * Crea una vista dove è possibile effettuare la scelta del trigger
     * @return Il trigger scelto dall'utente o {@code null} se l'utente non ha scelto nulla
     */
    private TriggerInterface createTriggerDefinitionView() {
        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(20);
        VBox triggerChoice = new VBox();
        triggerChoice.setAlignment(Pos.CENTER);
        triggerChoice.setSpacing(20);
        TriggerContext context = new TriggerContext();
        triggersList.getSelectionModel().clearSelection();
        triggersList.setOnAction(e->{
            //Prima di caricare la nuova view elimino quella già presente
            triggerChoice.getChildren().clear();
            var render = switch (triggersList.getValue()){
                case TRIGGER_TIME_OF_DAY -> new RenderTriggerTime();
                case TRIGGER_DATE -> new RenderTriggerDate();
                case TRIGGER_DAY_OF_WEEK -> new RenderTriggerDayOfWeek();
                case TRIGGER_DAY_OF_MONTH -> new RenderTriggerDayOfMonth();
                case TRIGGER_FILE_SIZE -> new RenderTriggerFileSize();
                case TRIGGER_FILE_EXISTS -> new RenderTriggerFileExists();
                case TRIGGER_EXIT_STATUS_PROGRAM -> new RenderTriggerExitStatusProgram();
                default -> null;
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
        return( context.getReturnTrigger() );
    }
}
