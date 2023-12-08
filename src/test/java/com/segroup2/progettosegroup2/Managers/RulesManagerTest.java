package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



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

}