package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountersManagerTest {
    private static CountersManager countersManager;

    @BeforeEach
    void clearCountersManager(){
        countersManager.clear();
    }

    @BeforeAll
    public static void init() {
        /*Ottiene il rules manager e cambia il file di salvataggio a quello per i test */
        countersManager = CountersManager.getInstance();
    }

    @Test
    void addCounter() {
        Counter counter = new Counter("test", 1);
        assertTrue(countersManager.addCounter(counter));
        assertTrue(countersManager.getCounters().contains(counter));
    }

    @Test
    void RemoveAll() {
        Counter counter1 = new Counter("test1",1);
        Counter counter2 = new Counter("test2",2);
        countersManager.addCounter(counter1);
        countersManager.addCounter(counter2);
        ObservableList<Counter> t = FXCollections.observableArrayList();
        t.addAll(counter1,counter2);
        assertTrue(countersManager.removeAll(t));
        assertFalse(countersManager.getCounters().contains(counter1));
        assertFalse(countersManager.getCounters().contains(counter2));
    }

    @Test
    void getInstance() {
        CountersManager countersManager1 = CountersManager.getInstance();
        CountersManager countersManager2 = CountersManager.getInstance();

        assertSame(countersManager1,countersManager2);
    }

}