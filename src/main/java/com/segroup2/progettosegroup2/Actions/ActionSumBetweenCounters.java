package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;
/**
 * Permette di sommare il valore di un contatore ad un altro contatore
 * */
public class ActionSumBetweenCounters implements ActionInterface {

    private Counter counterBeingAdded;
    private Counter counterToGetValue;

    public ActionSumBetweenCounters(Counter counterBeingAdded, Counter counterToGetValue) {
        this.counterBeingAdded = counterBeingAdded;
        this.counterToGetValue = counterToGetValue;
    }

    /**
     * @throws RuntimeException quando uno e/o entrambi i contatori sono stati cancellati
     * */
    @Override
    public boolean execute() throws RuntimeException {
        if (counterBeingAdded == null || counterToGetValue == null)
            throw new RuntimeException("One or both of the counters have been deleted.");
        counterBeingAdded.setValue(counterToGetValue.getValue() + counterBeingAdded.getValue());
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

    @Override
    public String toString() {
        return counterBeingAdded.getName() + "+=" + counterToGetValue.getName();
    }
}
