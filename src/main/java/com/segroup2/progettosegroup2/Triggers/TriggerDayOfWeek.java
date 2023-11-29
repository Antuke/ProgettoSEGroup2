package com.segroup2.progettosegroup2.Triggers;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Condizione per un giorno specificato della settimana
 */
public class TriggerDayOfWeek implements TriggerInterface, Serializable {
    private final DayOfWeek day;

    public TriggerDayOfWeek(DayOfWeek day){
        this.day= day;
    }

    /**
     * @return Vero se la condizione è verificata altrimenti falso
     */
    @Override
    public boolean check() {
        return( LocalDate.now().getDayOfWeek().equals(day) );
    }

    @Override
    public String toString() {
        return("Trigger giorno della settimana il: "+day.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
    }
}