package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDayOfMonthTest {

    @Test
    void constructorSuccess(){
        assertDoesNotThrow( ()->{new TriggerDayOfMonth(3);} );
    }

    @Test
    void constructorException(){
        assertThrowsExactly(IllegalArgumentException.class, ()->{ new TriggerDayOfMonth(0); } );
        assertThrowsExactly(IllegalArgumentException.class, ()->{ new TriggerDayOfMonth(32); } );
    }

    @Test
    void check() {
        int dayOfMonth;

        dayOfMonth= LocalDate.now().getDayOfMonth();
        TriggerInterface triggerTrue= new TriggerDayOfMonth( dayOfMonth );
        assertTrue(triggerTrue.check());

        dayOfMonth= LocalDate.now().plusDays(1).getDayOfMonth();
        TriggerInterface triggerFalse= new TriggerDayOfMonth( dayOfMonth );
        assertFalse(triggerFalse.check());
    }
}