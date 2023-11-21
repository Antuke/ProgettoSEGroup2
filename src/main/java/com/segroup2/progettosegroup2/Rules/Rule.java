package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

public class Rule {
    private final TriggerInterface trigger;
    private final ActionInterface action;
    private boolean fired;
    public Rule(TriggerInterface trigger, ActionInterface action) {
        this.trigger = trigger;
        this.action = action;
        this.fired = false;
    }

    public boolean isFired(){ return fired; }
    public void setFired(boolean x){
        fired = x;
    }
    public boolean check(){
        return trigger.check();
    }

    public boolean execute(){
        boolean status =  action.execute();
        fired = true;
        return status;
    }
}
