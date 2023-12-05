package com.segroup2.progettosegroup2.Counters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CounterTest {

    Counter counter;
    final int value = 1,new_value = 5;
    final String name = "test";

    @BeforeEach
    public void setUp() {
        counter = new Counter(name,value);
    }

    @Test
    void getName() {
        Assertions.assertEquals(name, counter.getName());
    }

    @Test
    void getValue() {
        Assertions.assertEquals(value, counter.getValue());
    }

    @Test
    void setValue() {
        Assertions.assertEquals(value, counter.getValue());
        counter.setValue(new_value);
        Assertions.assertEquals(new_value, counter.getValue());
    }
}