package com.segroup2.progettosegroup2.Counters;

import com.segroup2.progettosegroup2.Managers.ListenerInterface;
import com.segroup2.progettosegroup2.Managers.Observable;

import java.io.Serializable;
import java.util.ArrayList;

public class Counter implements Serializable, Observable {

    private String name;

    private transient ArrayList<ListenerInterface> listners ;

    private Integer value;


    public Counter(String name, int value) {
        this.name = name;
        this.value = value;
        this.listners = new ArrayList<>();

    }

    public void subscribe(ListenerInterface cli){
        if(listners == null)
            listners = new ArrayList<>();
        if(!listners.contains(cli))
            listners.add(cli);
    }

    public void notifyListeners(){
        for(ListenerInterface l : listners)
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
        notifyListeners();

    }


    @Override
    public String toString() {
        return name;
    }


}
