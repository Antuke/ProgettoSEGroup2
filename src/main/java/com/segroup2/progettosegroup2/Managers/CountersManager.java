package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Counters.Counter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton
 * */
public class CountersManager {
    private ObservableList<Counter> counters;
    private static CountersManager counterManager;
    private CountersManager(){
        counters = FXCollections.observableList(new LinkedList<>());
    }
    public void setCounters(List<Counter> r) {
        counters = FXCollections.observableList(r);
    }


    public boolean removeAll(ObservableList<Counter> toDelete){
        return counters.removeAll(new LinkedList<>(toDelete));
    }

    public boolean addCounter(Counter counter) {
        return counters.add(counter);
    }

    public boolean contains(Counter counter){
        for (Counter c : counters) {
            if(c.getName().equals(counter.getName()))
                return true;
        }
        return false;
    }

    public synchronized ObservableList<Counter> getCounters(){
        return counters;
    }


    public static CountersManager getInstance(){
        if (counterManager == null)
            counterManager = new CountersManager();
        return counterManager;
    }

    public void clear(){
        counters = FXCollections.observableArrayList();
    }

}
