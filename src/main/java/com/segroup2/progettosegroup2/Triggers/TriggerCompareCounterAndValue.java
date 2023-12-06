package com.segroup2.progettosegroup2.Triggers;

import com.segroup2.progettosegroup2.Counters.Counter;

import java.io.Serializable;

public class TriggerCompareCounterAndValue implements TriggerInterface, Serializable {
    private final Counter param1;
    private final int param2;

    public TriggerCompareCounterAndValue (Counter param1, int param2){
        this.param1 = param1;
        this.param2 = param2;
    }


    @Override
    public boolean check() throws RuntimeException {
        return param1.getValue() == param2;
    }

    @Override
    public boolean add(TriggerInterface t) {
        return false;
    }

    @Override
    public boolean remove(TriggerInterface t) {
        return false;
    }

    @Override
    public String toString() {
        return("Trigger confronto Counter " + param1.getName() + " con Intero " + param2);
    }
}
