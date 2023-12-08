package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;
/**
 * Aggiunge un valore intero definito dall'utente alla creazione a
 * un contatore
 * */
public class ActionAddConstantToCounter implements ActionInterface{

    private Integer constant;
    private Counter counter;

    public ActionAddConstantToCounter(String constant,Counter counter) {
        this.constant = Integer.parseInt(constant);
        this.counter = counter;
    }

    public ActionAddConstantToCounter(Integer constant,Counter counter) {
        this.constant = constant;
        this.counter = counter;
    }

    /**
     *
     * @throws RuntimeException quando il contatore è null (quando è stato cancellato)
     * */
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

    public String toString(){
        return "Aggiungi "+ constant.toString() + " al contatore: " + counter.getName();
    }
}
