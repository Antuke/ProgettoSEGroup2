package com.segroup2.progettosegroup2.Counters;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Managers.CounterListnerInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Counter implements Serializable {

    private String name;

    private transient ArrayList<CounterListnerInterface> listners ;

    private Integer value;


    public Counter(String name, int value) {
        this.name = name;
        this.value = value;
        this.listners = new ArrayList<>();

    }

    public void subscribe(CounterListnerInterface cli){
        if(listners == null) listners = new ArrayList<>();
        if(listners.contains(cli)) return;
        System.out.println(cli.toString() + "si è iscritto!");
        listners.add(cli);
    }

    public void notifyListners(){
        for(CounterListnerInterface l : listners)
            l.update();
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
        notifyListners();

    }


    @Override
    public String toString() {
        return name;
    }


}
