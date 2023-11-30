package com.segroup2.progettosegroup2.Threads;

import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Rules.SleepingRule;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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
                                    Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
                                    exceptionAlert.setTitle("Errore nell'eseguire la regola");
                                    exceptionAlert.setContentText("La regola che ha causato l'errore:\n" + r.toString() + "\nCausa:" + e.getMessage());
                                    exceptionAlert.show();
                                    r.setActive(false);
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
