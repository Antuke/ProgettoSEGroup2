package com.segroup2.progettosegroup2.Triggers;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Counters.CounterCompareEnum;

import java.io.Serializable;

public class TriggerCompareCounterAndValue implements TriggerInterface, Serializable {
    private final Counter param1;
    private final int param2;
    private final CounterCompareEnum sign;

    public TriggerCompareCounterAndValue (Counter param1, int param2, CounterCompareEnum sign){
        this.param1 = param1;
        this.param2 = param2;
        this.sign = sign;
    }


    @Override
    public boolean check() throws RuntimeException {
        switch (sign) {
            case GREATER -> {
                return param1.getValue() > param2;
            }
            case GREATER_EQUALS -> {
                return param1.getValue() >= param2;
            }
            case EQUALS -> {
                return param1.getValue() == param2;
            }
            case NOT_EQUALS -> {
                return param1.getValue() != param2;
            }
            case LESSER_EQUALS -> {
                return param1.getValue() <= param2;
            }
            case LESSER -> {
                return param1.getValue() < param2;
            }
        };
        return false;
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
        return("Trigger confronto Counter " + param1.getName() + " " + sign + " " + param2);
    }
}
