package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;

/**
 * Azione di modifica del valore di un contatore
 */
public class ActionSetCounter implements ActionInterface{
    private final Integer value;
    private final Counter counter;

    /**
     * Crea un oggetto capace di modificare il valore del contatore passato
     * @param counter Contatore di cui si vuole modificare il valore
     * @param value Valore da assegnare al contatore
     * @throws IllegalArgumentException Se un qualsiasi parametro è nullo
     */
    public ActionSetCounter(Counter counter, Integer value){
        if( counter==null || value==null )
            throw new IllegalArgumentException("I parametri devono essere non nulli");
        this.counter= counter;
        this.value= value;
    }


    /**
     * @return Vero se l'azione è andata a buon fine, falso altrimenti
     */
    @Override
    public boolean execute() {
        counter.setValue(value);
        return( value.equals( counter.getValue() ) );
    }

    /**
     * Operazione non possibile in questa classe
     * @return False
     */
    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    /**
     * Operazione non possibile in questa classe
     * @return False
     */
    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }

    @Override
    public String toString() {
        return( "Assegnazione del valore "+value+" al contatore '"+counter.getName()+"'" );
    }
}
