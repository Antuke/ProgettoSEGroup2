package com.segroup2.progettosegroup2.managers;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Managers.RulesManager;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesManagerTest {

    private RulesManager rulesManager;

    @BeforeEach
    public void setUp() {
        // Inizializzare un nuovo RulesManager prima di ogni test
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
    void getInstance() {
        RulesManager rulesManager1 = RulesManager.getInstance();
        RulesManager rulesManager2 = RulesManager.getInstance();

        assertSame(rulesManager1,rulesManager2);
    }
}