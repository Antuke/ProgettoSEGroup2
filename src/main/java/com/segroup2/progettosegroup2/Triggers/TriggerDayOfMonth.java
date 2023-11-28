package com.segroup2.progettosegroup2.Triggers;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Condizione per un giorno specificato del mese
 */
public class TriggerDayOfMonth implements TriggerInterface, Serializable {
    private final Integer day;

    /**
     * @param day Giorno specifico del mese
     * @throws IllegalArgumentException Se il parametro non è compreso tra 1 e 31 (inclusi)
     */
    public TriggerDayOfMonth(int day){
        if( day<1 || day>31 )
            throw new IllegalArgumentException("Il parametro deve essere compreso tra 1 e 31 (inclusi)");
        this.day= day;
    }

    /**
     * @return Vero se la condizione è verificata altrimenti falso
     */
    @Override
    public boolean check() {
        return( day.equals( LocalDate.now().getDayOfMonth() ) );
    }

    @Override
    public String toString() {
        return( "Trigger giorno del mese il: "+day );
    }
}
