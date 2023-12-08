package com.segroup2.progettosegroup2.Triggers;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Counters.CounterCompareEnum;

public class TriggerCompareCounters implements TriggerInterface {
    private final Counter param1;
    private final Counter param2;
    private final CounterCompareEnum sign;

    public TriggerCompareCounters (Counter param1, Counter param2, CounterCompareEnum sign){
        this.param1 = param1;
        this.param2 = param2;
        this.sign = sign;
    }


    @Override
    public boolean check() throws RuntimeException {
        switch (sign) {
            case GREATER -> {
                return param1.getValue() > param2.getValue();
            }
            case GREATER_EQUALS -> {
                return param1.getValue() >= param2.getValue();
            }
            case EQUALS -> {
                return param1.getValue() == param2.getValue();
            }
            case NOT_EQUALS -> {
                return param1.getValue() != param2.getValue();
            }
            case LESSER_EQUALS -> {
                return param1.getValue() <= param2.getValue();
            }
            case LESSER -> {
                return param1.getValue() < param2.getValue();
            }
        }
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
        return(param1.getName() + " " + sign + " " + param2.getName());
    }
}
