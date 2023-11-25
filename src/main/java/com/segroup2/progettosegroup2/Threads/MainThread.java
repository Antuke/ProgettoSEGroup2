package com.segroup2.progettosegroup2.Threads;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.ObservableList;

public class MainThread implements Runnable{
    private ObservableList<Rule> rules;

    public MainThread (ObservableList<Rule> rules){
        this.rules = rules;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Rule r : rules)
                    if (r.check())
                        r.execute();

                //Aspetto 5 secondi e poi ricontrollo le condizioni
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
