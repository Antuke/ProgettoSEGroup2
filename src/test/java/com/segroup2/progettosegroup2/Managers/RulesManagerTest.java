package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.TestFX;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RulesManagerTest {

    private static RulesManager rulesManager;

    @BeforeEach
    void clearRulesManager(){
        rulesManager.clear();
    }
    @BeforeAll
    public static void init() {
        /*Ottiene il rules manager e cambia il file di salvataggio a quello per i test */
        rulesManager = RulesManager.getInstance();
        RulesPersistance.setSaveName("testSaves.bin");

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


    @Test
    public void testSaveAndLoad() throws IOException {
        /* file di salvataggio temporaneo con nome casuale*/
        Integer randomName = new Random().nextInt();
        File tmp = File.createTempFile(randomName.toString(),randomName.toString());
        /* regole da inserire */
        Rule rule1 = new Rule(new TriggerTime(10, 0), new ActionAudio());
        Rule rule2 = new Rule(new TriggerTime(10, 1), new ActionAudio());
        ObservableList<Rule> testList = FXCollections.observableArrayList();
        testList.addAll(rule1, rule2);

        /* aggiungo direttamente alla lista bypassando il salvataggio automatico */
        rulesManager.getRules().add(rule1);
        rulesManager.getRules().add(rule2);
        /* salvo le regole e poi le cancello */
        rulesManager.save();
        rulesManager.getRules().remove(rule1);
        rulesManager.getRules().remove(rule2);

        /* ricarico le regole */
        rulesManager.load();

        /* to String """così controllo i valori e non gli oggetti""" */
        assertEquals(testList.toString(), rulesManager.getRules().toString());

    }



}