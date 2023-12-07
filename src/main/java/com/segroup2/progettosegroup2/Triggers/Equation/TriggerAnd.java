package com.segroup2.progettosegroup2.Triggers.Equation;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

import java.util.LinkedList;

public class TriggerAnd implements TriggerInterface {

    private final LinkedList<TriggerInterface> triggers;

    public TriggerAnd() {
        this.triggers = new LinkedList<>();
    }

    /**
     * Aggiunge un trigger se la lista non ha già due elementi
     */
    @Override
    public boolean add(TriggerInterface t) {
        if (triggers.size() == 2) return false;
        return triggers.add(t);
    }
    @Override
    public boolean remove(TriggerInterface t) {
        return triggers.remove(t);
    }

    @Override
    public boolean check() throws RuntimeException {
        for (TriggerInterface t : triggers) {
            if (!t.check()) return false;
        }
        return true;
    }

    public String toString(){
        
        return triggers.get(0).toString() + " AND (" + triggers.get(1).toString() +")\n";
    }


}
