package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;

public class ActionSumBetweenCounters implements ActionInterface{

    private Counter counterBeingAdded;
    private Counter counterToGetValue;

    public ActionSumBetweenCounters(Counter counterBeingAdded, Counter counterToGetValue) {
        this.counterBeingAdded = counterBeingAdded;
        this.counterToGetValue = counterToGetValue;
    }


    @Override
    public boolean execute() throws RuntimeException {
        if(counterBeingAdded == null || counterToGetValue == null) throw new RuntimeException("Uno dei due contatori o entrambi sono stati cancellati");
        counterBeingAdded.setValue( counterToGetValue.getValue() + counterBeingAdded.getValue());
        return true;
    }

    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }
}
