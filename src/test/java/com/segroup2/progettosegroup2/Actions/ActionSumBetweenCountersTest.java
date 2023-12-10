package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionSumBetweenCountersTest {

    private static Counter counterOne;
    private static Counter counterTwo;

    private static ActionInterface action;
    @BeforeAll
    public static void init() {
        counterOne = new Counter("test",10);
        counterTwo = new Counter("test",20);


        action = new ActionSumBetweenCounters(counterOne,counterTwo);
    }

    @Test
    void execute() {
        action.execute();
        action.execute();
        assertEquals(counterOne.getValue(),50);
    }
}