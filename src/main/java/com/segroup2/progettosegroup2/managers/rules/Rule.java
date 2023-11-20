package com.segroup2.progettosegroup2.managers.rules;

import com.segroup2.progettosegroup2.managers.rules.actions.ActionInterface;
import com.segroup2.progettosegroup2.managers.rules.triggers.TriggerInterface;

public class Rule {
    private final TriggerInterface trigger;
    private final ActionInterface action;

    public Rule(TriggerInterface trigger, ActionInterface action) {
        this.trigger = trigger;
        this.action = action;
    }

    public boolean check(){
        return trigger.check();
    }

    public boolean execute(){
        return action.execute();
    }
}
