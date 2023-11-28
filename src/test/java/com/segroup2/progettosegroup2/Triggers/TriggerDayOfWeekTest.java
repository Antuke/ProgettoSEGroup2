package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDayOfWeekTest {

    @Test
    void checkTrue() {
        TriggerDayOfWeek trigger= new TriggerDayOfWeek( LocalDate.now().getDayOfWeek() );
        assertTrue(trigger.check() );
    }

    @Test
    void checkFalse(){
        TriggerDayOfWeek trigger;

        trigger= new TriggerDayOfWeek( LocalDate.now().plusDays(1).getDayOfWeek() );
        assertFalse(trigger.check());

        trigger= new TriggerDayOfWeek(null);
        assertFalse(trigger.check());
    }
}