package com.segroup2.progettosegroup2.Triggers;

import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Counters.CounterCompareEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriggerCompareCounterAndValueTest {
    Counter c1;
    int c2;
    @BeforeEach
    void setUp(){
        c1 = new Counter("Test",1);
        c2 = 1;

    }
    @Test
    void checkTrue() {
        TriggerCompareCounterAndValue test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.EQUALS);
        assertTrue(test.check());
        c1.setValue(2);
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.GREATER);
        assertTrue(test.check());
        c1.setValue(0);
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.GREATER_EQUALS);
        assertFalse(test.check());
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.LESSER);
        assertTrue(test.check());
        c1.setValue(1);
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.LESSER_EQUALS);
        assertTrue(test.check());
    }

    @Test
    void checkFalse() {
        TriggerCompareCounterAndValue test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.NOT_EQUALS);
        assertFalse(test.check());
        c1.setValue(1);
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.GREATER);
        assertFalse(test.check());
        c1.setValue(2);
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.GREATER_EQUALS);
        assertTrue(test.check());
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.LESSER);
        assertFalse(test.check());
        c1.setValue(2);
        test = new TriggerCompareCounterAndValue(c1, c2, CounterCompareEnum.LESSER_EQUALS);
        assertFalse(test.check());
    }
}