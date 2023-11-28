package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TriggerDateTest {
    private TriggerDate trigger;
    private LocalDate date;
    private int year;
    private int month;
    private int day;

    void setParameter(LocalDate date){
        this.date= date;
        this.year= date.getYear();
        this.month= date.getMonthValue();
        this.day= date.getDayOfMonth();
    }

    @Test
    void checkTrue() {
        setParameter(LocalDate.now());

        trigger= new TriggerDate(date);
        assertTrue(trigger.check());

        trigger= new TriggerDate(year, month, day);
        assertTrue(trigger.check());
    }

    @Test
    void checkFalse(){
        setParameter(LocalDate.now().plusDays(1));

        trigger= new TriggerDate(date);
        assertFalse(trigger.check());

        trigger= new TriggerDate(year, month, day);
        assertFalse(trigger.check());

        trigger= new TriggerDate(null);
        assertFalse(trigger.check());
    }
}