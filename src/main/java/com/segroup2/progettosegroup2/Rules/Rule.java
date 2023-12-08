package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Managers.ListenerInterface;
import com.segroup2.progettosegroup2.Managers.Observable;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import java.io.*;
import java.util.ArrayList;

public class Rule implements Serializable, Observable {
    /*Variabile che serve per rendere una regola attiva o disattiva*/
    private Boolean  active;
    private TriggerInterface trigger;
    private ActionInterface action;
    private boolean fired;
    private transient ArrayList<ListenerInterface> listeners;

    public Rule(TriggerInterface trigger, ActionInterface action) {
        this.trigger = trigger;
        this.action = action;
        this.fired = false;
        this.active = true;
        this.listeners = new ArrayList<>();
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
    protected void setFired(boolean fired){
        this.fired=fired;
    }
    public void setActive(Boolean active) {
        this.active = active;
        this.setFired(false);
        notifyListeners();
    }

    public boolean isActive() {
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

    public void subscribe(ListenerInterface cli){
        // Quando carico l'oggetto da file devo inizializzare l'array
        if(listeners == null)
            listeners = new ArrayList<>();
        // Uno stesso oggetto non può sottoscriversi più volte
        if(!listeners.contains(cli))
            listeners.add(cli);
    }

    public void notifyListeners(){
        for(ListenerInterface l : listeners) {
            l.update();
        }
    }

    public String getDetail(){
        return "Type : Normal\n"+toString();
    }
    public String toString(){
        String attivo = "Attivo";
        if (!active)
            attivo = "Disattivo";

        return "Trigger:\n"+trigger+"\n\nAction:\n"+action+"\nStato:\t"+attivo+"\n";
    }
}
