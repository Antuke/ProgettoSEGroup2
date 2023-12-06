package com.segroup2.progettosegroup2.Triggers;

import com.segroup2.progettosegroup2.Counters.Counter;

import java.io.Serializable;

public class TriggerCompareCounters implements TriggerInterface, Serializable {
    Counter param1;
    Counter param2;

    public TriggerCompareCounters (Counter param1, Counter param2){
        this.param1 = param1;
        this.param2 = param2;
    }


    @Override
    public boolean check() throws RuntimeException {
        return param1.getValue() == param2.getValue();
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
        return("Trigger confronto Counter " + param1.getName() + " con Counter " + param2.getName());
    }
}
