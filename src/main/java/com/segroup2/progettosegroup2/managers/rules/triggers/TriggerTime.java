package com.segroup2.progettosegroup2.managers.rules.triggers;

import java.time.LocalTime;

public class TriggerTime implements TriggerInterface{
    private LocalTime time;

    public TriggerTime(int h, int m) { /* Da discutere l'eventuale creazione di più costruttori */
        time = LocalTime.of(h, m, 0);
    }

    @Override
    public boolean check() {
        LocalTime now = LocalTime.now().withSecond(0);
        return now.equals(time);
    }
}

