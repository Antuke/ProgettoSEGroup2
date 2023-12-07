package com.segroup2.progettosegroup2.Triggers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Condizione per un giorno specificato della settimana
 */
public class TriggerDayOfWeek implements TriggerInterface {
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
    public boolean add(TriggerInterface t) {
        return false;
    }

    @Override
    public boolean remove(TriggerInterface t) {
        return false;
    }

    @Override
    public String toString() {
        return("Trigger giorno della settimana il: "+day.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
    }
}
