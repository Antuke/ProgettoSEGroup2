package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.TestFX;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RulesManagerTest {

    private static RulesManager rulesManager;

    @BeforeAll
    public static void initJavaFX() {
        // Inizializzare un nuovo RulesManager prima di tutti test
        rulesManager = RulesManager.getInstance();
    }

    @Test
    void addRule() {
        Rule rule = new Rule(new TriggerTime(10,0),new ActionAudio());
        assertTrue(rulesManager.addRule(rule));
        assertTrue(rulesManager.getRules().contains(rule));
    }

    @Test
    void removeRule() {
        Rule rule = new Rule(new TriggerTime(10,0),new ActionAudio());
        rulesManager.addRule(rule);
        assertTrue(rulesManager.removeRule(rule));
        assertFalse(rulesManager.getRules().contains(rule));
    }

    @Test
    void RemoveAll() {
        Rule rule1 = new Rule(new TriggerTime(10,0),new ActionAudio());
        Rule rule2 = new Rule(new TriggerTime(10,1),new ActionAudio());
        rulesManager.addRule(rule1);
        rulesManager.addRule(rule2);
        ObservableList<Rule> t = FXCollections.observableArrayList();
        t.addAll(rule1,rule2);
        assertTrue(rulesManager.removeAll(t));
        assertFalse(rulesManager.getRules().contains(rule1));
        assertFalse(rulesManager.getRules().contains(rule2));
    }

    @Test
    void getInstance() {
        RulesManager rulesManager1 = RulesManager.getInstance();
        RulesManager rulesManager2 = RulesManager.getInstance();

        assertSame(rulesManager1,rulesManager2);
    }

    /*
    @Test
    void save() {
        Rule rule = new Rule(new TriggerTime(10, 0), new ActionAudio());
        rulesManager.getRules().add(rule);
        rulesManager.save("Salvataggi/testSave.bin");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        File file = new File(MainApplication.class.getResource("Salvataggi/testSave.bin").getPath());
        ArrayList<Rule> test = new ArrayList<>(rulesManager.getRules());
        assertEquals(file.length(),test.stream().toArray().length);
    }
    @Test
    void savea() {
        Rule rule = new Rule(new TriggerTime(10, 0), new ActionAudio());
        rulesManager.addRule(rule);
        rulesManager.save("Salvataggi/testSave.bin");
        rulesManager.removeRule(rule);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            rulesManager.load("Salvataggi/testSave.bin");
            System.out.println(rulesManager.getRules().toString());
            assertTrue(rulesManager.getRules().contains(rule), "The loaded rules do not contain the expected rule.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred during the save/load operations.");
        }
    }
    */

}