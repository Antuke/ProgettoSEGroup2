package com.segroup2.progettosegroup2.Triggers;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TriggerTime implements TriggerInterface, Serializable {
    private LocalTime time;

    public TriggerTime(int h, int m) { /* Da discutere l'eventuale creazione di più costruttori */
        time = LocalTime.of(h, m, 0);
    }

    @Override
    public boolean check() {
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        return now.equals(time);
    }

    public String toString(){
        return "Trigger orario del giorno alle: " + this.time.toString();
    }
}

