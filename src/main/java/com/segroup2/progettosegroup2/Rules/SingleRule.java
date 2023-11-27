package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

public class SingleRule extends Rule{

    public SingleRule(TriggerInterface trigger, ActionInterface action) {
        super(trigger, action);
    }

    @Override
    public boolean check(){
        return super.check();
    }

    @Override
    public boolean execute(){
        boolean status = super.execute();
        active = false;
        return status;
    }
}
