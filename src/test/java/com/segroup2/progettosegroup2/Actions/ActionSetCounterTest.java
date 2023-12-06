package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.Counters.Counter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionSetCounterTest {
    @Test
    void constructFailure(){
        assertThrowsExactly(IllegalAccessError.class, () -> new ActionSetCounter(null, null) );
        assertThrowsExactly(IllegalAccessError.class, () -> new ActionSetCounter(null, 10) );
        assertThrowsExactly(IllegalAccessError.class, () -> new ActionSetCounter(new Counter("name", 1), null) );
    }

    @Test
    void execute() {
        ActionSetCounter action;
        Counter counter= new Counter("name", 1);

        action= new ActionSetCounter(counter, 10);
        assertTrue( action.execute() );

        action= new ActionSetCounter(counter, -10);
        assertTrue( action.execute() );
    }
}