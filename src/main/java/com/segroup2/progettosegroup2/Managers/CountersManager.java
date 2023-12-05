package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Counters.Counter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
public class CountersManager {
    private ObservableList<Counter> counters;

    private CounterPersistance counterPersistance;
    private static CountersManager counterManager;

    /* locazione e nome del file salvataggio nelle risorse */

    private CountersManager(){
        counters = FXCollections.observableList(new LinkedList<>());
        counterPersistance = new CounterPersistance();
        load();
    }

    public boolean removeAll(ObservableList<Counter> toDelete){
        counters.removeAll(new LinkedList<>(toDelete));
        saveAsync();
        return true;
    }

    public boolean addCounter(Counter counter) {
        boolean returnValue = counters.add(counter);
        saveAsync();
        return returnValue;
    }

    public boolean searchCounter(Counter counter){
        for (Counter c : counters) {
            if(c.getName().equals(counter.getName()))
                return true;
        }
        return false;
    }

    public ObservableList<Counter> getCounters(){
        return counters;
    }


    public static CountersManager getInstance(){
        if (counterManager == null)
            counterManager = new CountersManager();
        return counterManager;
    }


    public void save(){
        counterPersistance.saveCounters(new LinkedList<>(counters));
    }

    public void load(){
        counters.setAll(counterPersistance.loadCounters());
    }

    private void saveAsync() {
        /*Lancia un thread che esegue la funzione save*/
        Thread saveThread = new Thread(this::save);
        saveThread.start();
    }

    public void clear(){
        counters = FXCollections.observableArrayList();
    }

}
