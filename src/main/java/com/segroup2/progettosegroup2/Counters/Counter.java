package com.segroup2.progettosegroup2.Counters;

import java.io.Serializable;

public class Counter implements Serializable {

    private final String name;
    private int value;

    public Counter(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
