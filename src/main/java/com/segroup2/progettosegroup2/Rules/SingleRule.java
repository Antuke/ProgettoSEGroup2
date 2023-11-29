package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;


public class SingleRule extends Rule{

    public SingleRule(TriggerInterface trigger, ActionInterface action) {
        super(trigger, action);
    }

    @Override
    public boolean execute(){
        boolean status = super.execute();
        //Se l'esecuzione dell'azione ha esito positivo allora solo in questo caso disattivo la regola
        if (status)
            setActive(false);
        return status;
    }
}
