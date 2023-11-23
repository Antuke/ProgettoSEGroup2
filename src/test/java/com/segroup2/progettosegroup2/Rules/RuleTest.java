package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class RuleTest {

    static Rule rule;

    @BeforeAll
    public static void setUp() {
        ActionInterface action = new Temp();
        TriggerInterface trigger = new TriggerTime(LocalTime.now().getHour(), LocalTime.now().getMinute());
        rule = new Rule(trigger, action);
    }


    @Test
    void isFired() {
        rule = new Rule(new TriggerTime(LocalTime.now().getHour(), LocalTime.now().getMinute()), new Temp());
        Assertions.assertFalse(rule.isFired());
        rule.execute();
        Assertions.assertTrue(rule.isFired());
    }

    @Test
    void check() {
        int s = 1000;
        Assertions.assertTrue(rule.check());
        try {
            Thread.sleep(60*s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(rule.check());
    }

    @Test
    void execute() {
        Assertions.assertTrue(rule.execute());

    }
    private static class Temp implements ActionInterface{

        @Override
        public boolean execute() {
            return true;
        }
    }
}