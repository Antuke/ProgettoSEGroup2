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
        int dayOfMonth= LocalDate.now().getDayOfMonth();
        TriggerInterface triggerTrue= new TriggerDayOfMonth( dayOfMonth );
        assertTrue(triggerTrue.check());

        if(dayOfMonth==1){
            dayOfMonth++;
        }else{
            dayOfMonth--;
        }
        TriggerInterface triggerFalse= new TriggerDayOfMonth( dayOfMonth );
        assertFalse(triggerFalse.check());
    }
}