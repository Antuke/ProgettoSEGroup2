package com.segroup2.progettosegroup2.Triggers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TriggerTime implements TriggerInterface {
    private final LocalTime time;

    public TriggerTime(int h, int m) { /* Da discutere l'eventuale creazione di più costruttori */
        time = LocalTime.of(h, m, 0);
    }

    @Override
    public boolean check() {
        //Creo un oggetto LocalTime che contiene l'orario attuale fino ai minuti
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        //Restituisco true se l'orario attuale corrisponde all'orario impostato durante la creazione dell'oggetto
        return now.equals(time);
    }

    @Override
    public boolean add(TriggerInterface t) {
        return false;
    }

    @Override
    public boolean remove(TriggerInterface t) {
        return false;
    }

    public String toString(){
        return "Time of the day: " + this.time.toString();
    }
}

