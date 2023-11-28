package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDayOfWeekTest {

    @Test
    void check() {
        TriggerDayOfWeek trigger;
        int day= LocalDate.now().getDayOfWeek().getValue();

        trigger= new TriggerDayOfWeek( DayOfWeek.of(day) );
        assertTrue(trigger.check() );

        if( day==DayOfWeek.MONDAY.getValue() ){
            day++;
        }else{
            day--;
        }
        trigger= new TriggerDayOfWeek( DayOfWeek.of(day) );
        assertFalse(trigger.check());

        trigger= new TriggerDayOfWeek(null);
        assertFalse(trigger.check());
    }
}