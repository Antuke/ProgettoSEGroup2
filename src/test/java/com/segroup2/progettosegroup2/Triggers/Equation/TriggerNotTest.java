package com.segroup2.progettosegroup2.Triggers.Equation;

import com.segroup2.progettosegroup2.Triggers.TriggerDayOfMonth;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TriggerNotTest {

    static TriggerNot triggerNot1;

    static TriggerNot triggerNot2;

    static TriggerInterface t1;

    static TriggerInterface t2;
    @BeforeAll
    static void beforeAll(){
        /*t1 = vero ; t2 = falso */
        t1 = new TriggerDayOfMonth(LocalDateTime.now().getDayOfMonth());
        t2 = new TriggerDayOfMonth(LocalDateTime.now().getDayOfMonth()+1);

        triggerNot1 = new TriggerNot(t1);
        triggerNot2 = new TriggerNot(t2);
    }

    @Test
    void check() {
        assertFalse(triggerNot1.check());
        assertTrue(triggerNot2.check());
    }
}