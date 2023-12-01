package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Rule implements Serializable {
    /*Variabile che serve per rendere una regola attiva o disattiva*/
    private transient SimpleBooleanProperty  active;
    private TriggerInterface trigger;
    private ActionInterface action;
    private Boolean fired;
    public Rule(TriggerInterface trigger, ActionInterface action) {
        this.trigger = trigger;
        this.action = action;
        this.fired = false;
        this.active = new SimpleBooleanProperty(true);
    }

    /**
     * La funzione check() si occupa di verificare la condizione associata ad una regola.
     *
     * @return boolean: True se il trigger associato è vero e la regola non è già stata eseguita,
     * False se il trigger associato è vero e la regola non è ancora stata eseguita.
     */
    public boolean check(){
        boolean status = trigger.check();
        // Se la condizione è vera restiuisco il valore di fired
        if (status)
            return !fired;
        fired = false;
        return status;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public boolean isActive() {
        return active.get();
    }
    public SimpleBooleanProperty isActiveProperty(){
        return active;
    }
    public TriggerInterface getTrigger() {
        return trigger;
    }

    public ActionInterface getAction() {
        return action;
    }

    public boolean execute() throws RuntimeException{
        boolean status =  action.execute();
        fired = true;
        return status;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(trigger);
        s.writeObject(action);
        s.writeBoolean(fired);
        boolean state = active.getValue();
        s.writeBoolean(state);
    }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        trigger = (TriggerInterface) s.readObject();
        action = (ActionInterface) s.readObject();
        fired = s.readBoolean();
        boolean state =  s.readBoolean();
        active= new SimpleBooleanProperty(state);
    }

    public String toString(){
        return "action: " + action.toString() + "\ttrigger: " + trigger.toString();
    }
}
