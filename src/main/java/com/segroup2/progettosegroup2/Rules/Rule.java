package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

import java.io.Serializable;

public class Rule implements Serializable {
    private final TriggerInterface trigger;
    private final ActionInterface action;
    private boolean fired;
    public Rule(TriggerInterface trigger, ActionInterface action) {
        this.trigger = trigger;
        this.action = action;
        this.fired = false;
    }

    //Forse da eliminare perché gestito internamente alla classe rule
    public boolean isFired(){ return fired; }

    /**
     * La funzione check() si occupa di verificare la condizione associata ad una regola.
     *
     * @return boolean: True se il trigger associato è vero e la regola non è già stata eseguita,
     * False se il trigger associato è vero e la regola non è ancora stata eseguita.
     */
    public boolean check(){
        boolean status = trigger.check();
        // Se la condizione è vera restiuisco il valore di fired
        if (status){
            return !fired;
        }
        fired = false;
        return status;
    }

    public TriggerInterface getTrigger() {
        return trigger;
    }

    public ActionInterface getAction() {
        return action;
    }

    public boolean execute(){
        boolean status =  action.execute();
        fired = true;
        return status;
    }
}
