package com.segroup2.progettosegroup2.Threads;

import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Rules.SleepingRule;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class MainThread implements Runnable{
    private ObservableList<Rule> rules;

    public MainThread (ObservableList<Rule> rules){
        this.rules = rules;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Rule r : rules) {
                    //Se la regola è di tipo sleepingRule allora controllo se deve essere riattivata
                    if (r.getClass() == SleepingRule.class)
                        if (((SleepingRule) r).isToReactivate())
                            r.setActive(true);
                    if (r.isActive())
                        if (r.check()){
                            try{
                                r.execute();
                            }catch(RuntimeException e){
                                Platform.runLater(()->{
                                    /*Nel caso di un eccezione mostro un allert e disattivo la regola */
                                    e.printStackTrace();
                                    r.setActive(false);

                                    Dialog<ButtonType> dialog = new Dialog<>();
                                    dialog.setTitle("Error in rule execution");
                                    TextArea textArea = new TextArea("Rule that caused the error:\n" + r.toString() + "\nCause:" + e.getMessage());
                                    textArea.getStylesheets().addAll(MainApplication.class.getResource("Styles/TextStyle.css").toString());
                                    textArea.setEditable(false);
                                    textArea.setWrapText(true);
                                    GridPane gridPane = new GridPane();
                                    gridPane.add(textArea, 0, 0);
                                    dialog.getDialogPane().setContent(gridPane);
                                    dialog.getDialogPane().getStylesheets().add(MainApplication.class.getResource("Styles/ButtonStyle.css").toString());
                                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                                    dialog.showAndWait();
                                });
                            }
                        }

                }
                //Aspetto 1 secondi e poi ricontrollo le condizioni
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
