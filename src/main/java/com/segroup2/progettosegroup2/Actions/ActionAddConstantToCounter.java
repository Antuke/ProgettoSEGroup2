package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;

public class ActionAddConstantToCounter implements ActionInterface{

    private Integer constant;
    private Counter counter;

    public ActionAddConstantToCounter(Integer constant,Counter counter) {
        this.constant = constant;
        this.counter = counter;
    }

    @Override
    public boolean execute() throws RuntimeException {
        if(counter == null){
            throw new RuntimeException("Il counter è stato cancellato");
        }

        counter.setValue( counter.getValue() + constant);
        return true;
    }




    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }
}
