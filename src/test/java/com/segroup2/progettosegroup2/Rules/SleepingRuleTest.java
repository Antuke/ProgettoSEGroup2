package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class SleepingRuleTest {

    private static SleepingRule rule;
    @BeforeAll
    public static void setup(){
        TriggerInterface trigger = new TriggerTime(LocalTime.now().getHour(),LocalTime.now().getMinute());
        ActionInterface action = new ActionInterface() {
            @Override
            public boolean execute() {
                System.out.println("Eseguito");
                return true;
            }
        };
        rule = new SleepingRule(trigger,action,0,0,5);
    }

    @Test
    void executeAndSleepTest() throws InterruptedException {
        Assertions.assertTrue(rule.isActive());
        Assertions.assertTrue(rule.execute());
        Assertions.assertFalse(rule.isActive());
        Thread.sleep(1000*62);
        Assertions.assertFalse(rule.check());
        Thread.sleep(1000*60*5+1000);
        Assertions.assertTrue(rule.isActive());
    }

    @Test
    void afterSleepExecution() throws InterruptedException {
        Assertions.assertTrue(rule.isActive());
        Assertions.assertTrue(rule.execute());
        Assertions.assertFalse(rule.isActive());
    }
}