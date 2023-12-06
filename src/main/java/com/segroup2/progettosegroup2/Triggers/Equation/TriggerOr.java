package com.segroup2.progettosegroup2.Triggers.Equation;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

import java.util.LinkedList;


public class TriggerOr implements TriggerInterface {
    private LinkedList<TriggerInterface> triggers;

    public TriggerOr() {
        this.triggers = new LinkedList<>();
    }

    /**
     * Aggiunge un trigger se la lista non ha già due elementi
     * */
    public boolean add(TriggerInterface t){
        if(triggers.size() == 2) return false;
        return triggers.add(t);
    }

    public boolean remove(TriggerInterface t){
        return triggers.remove(t);
    }

    @Override
    public boolean check() throws RuntimeException {
        for(TriggerInterface t : triggers){
            if(t.check()) return true;
        }
        return false;

    }

    public String toString(){
        return triggers.get(0).toString() + " OR (" + triggers.get(1).toString()+")";
    }
}
