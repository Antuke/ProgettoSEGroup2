package com.segroup2.progettosegroup2.Triggers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Condizione per un giorno specificato
 */
public class TriggerDate implements TriggerInterface {
    private final LocalDate date;

    public TriggerDate(LocalDate date){
        this.date= date;
    }

    public TriggerDate(int year, int month, int day){
        this( LocalDate.of(year, month, day) );
    }

    /**
     * @return Vero se la condizione è verificata altrimenti falso
     */
    @Override
    public boolean check() {
        return( LocalDate.now().equals(date) );
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
        return(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALIAN)) );
    }
}
