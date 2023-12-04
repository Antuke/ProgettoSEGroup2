package com.segroup2.progettosegroup2.Triggers.Equation;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

/* Decorator-lite */
public class TriggerNot implements TriggerInterface {

    private TriggerInterface trigger;

    public TriggerNot(TriggerInterface trigger) {
        this.trigger = trigger;
    }
    @Override
    public boolean check() throws RuntimeException {
        return !trigger.check();
    }


}
