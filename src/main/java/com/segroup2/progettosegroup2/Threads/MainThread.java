package com.segroup2.progettosegroup2.Threads;

import com.segroup2.progettosegroup2.Rules.Rule;

import java.util.List;

public class MainThread implements Runnable{
    private List<Rule> rules;

    public MainThread (List<Rule> rules){
        this.rules = rules;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                for (Rule r : rules){
                    if(r.check()){
                        if(!r.isFired()){
                            r.execute();
                        }
                    }else{
                        r.setFired(false);
                    }
                }

                //Aspetto 5 secondi e poi ricontrollo le condizioni
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
