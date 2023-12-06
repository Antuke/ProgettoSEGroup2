package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionAddConstantToCounterTest {

    private static ActionInterface action;
    private static Counter counter;

    @BeforeAll
    public static void init() {
        counter = new Counter("test",10);
        action = new ActionAddConstantToCounter(10,counter);
    }
    @Test
    void execute() {

        action.execute();
        assertEquals(counter.getValue(),20);


    }
}