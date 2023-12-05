package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerDayOfMonth;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class SleepingRuleTest {

    private TriggerInterface trigger;
    private ActionInterface action;
    @BeforeEach
    void setUp(){
        trigger = new TriggerDayOfMonth(LocalDate.now().getDayOfMonth());
        action = new Temp(true);
    }

    // Il costruttore non lancia eccezioni
    @Test
    void SleepingRuleConstructorTest(){
        TriggerInterface trigger = new TriggerDayOfMonth(LocalDate.now().getDayOfMonth());
        ActionInterface action = new Temp(true);
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 0,0,0));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 0,0,59));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 0,0,30));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 0,23,0));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 0,23,59));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 0,10,0));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 50,0,0));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 400,0,0));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 400,23,59));
        Assertions.assertDoesNotThrow(()->new SleepingRule(trigger, action, 400,0,59));
    }

    // Il costruttore lancia delle eccezioni
    @Test
    void SleepingRuleConstructorExeptionTest(){

        // i minuti sono sbagliati
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,0,-1));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,0,-10));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,0,60));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,0,200));

        // le ore sono sbagliate
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,-1,0));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,-100,0));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,24,0));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,50,0));

        // i giorni sono sbagliati
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, -1,0,0));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, -100,0,0));

        // altri test
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,10,60));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,10,-1));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,24,50));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,25,10));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, 0,-10,0));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, -2,23,0));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, -2,24,60));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new SleepingRule(trigger, action, -1,23,-30));

    }

    @Test
    void getSleepingPeriodTest(){
        SleepingRule rule = new SleepingRule(trigger,action,0,0,0);
        Duration expected = Duration.ofMinutes(0).truncatedTo(ChronoUnit.MINUTES);
        Assertions.assertEquals(expected, rule.getSleepingPeriod());

        rule = new SleepingRule(trigger,action, 0,0, 59);
        expected = Duration.ofMinutes(59).truncatedTo(ChronoUnit.MINUTES);
        Assertions.assertEquals(expected, rule.getSleepingPeriod());

        rule = new SleepingRule(trigger,action, 5,23, 59);
        expected = expected.plusDays(5).plusHours(23);
        Assertions.assertEquals(expected, rule.getSleepingPeriod());
    }
    @Test
    void executeTrueTest(){
        SleepingRule rule = new SleepingRule(trigger,action,0,0,1);
        Assertions.assertTrue(rule.isActive());
        Assertions.assertTrue(rule.execute());
        Assertions.assertFalse(rule.isActive());
    }
    @Test
    void executeFalseTest(){
        SleepingRule rule = new SleepingRule(trigger,new Temp(false),0,0,1);
        Assertions.assertTrue(rule.isActive());
        Assertions.assertFalse(rule.execute());
        Assertions.assertTrue(rule.isActive());
    }

    @Test
    void isToReactivateFalseTest(){
        SleepingRule rule = new SleepingRule(trigger,action,0,0,1);
        Assertions.assertTrue(rule.isActive());
        Assertions.assertTrue(rule.execute());
        Assertions.assertFalse(rule.isActive());
        Assertions.assertFalse(rule.isToReactivate());
    }
    @Test
    void isToReactivateTrueTest(){
        SleepingRule rule = new SleepingRule(trigger,action,0,0,1);
        Assertions.assertTrue(rule.isActive());
        Assertions.assertTrue(rule.execute());
        Assertions.assertFalse(rule.isActive());
        try {
            long sleep = rule.getSleepingPeriod().toMillis();
            Thread.sleep(sleep+1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(rule.isToReactivate());
    }


    private static class Temp implements ActionInterface{
        private boolean executionStatus;
        public Temp(boolean executionStatus){
            this.executionStatus = executionStatus;
        }
        @Override
        public boolean execute() {
            return executionStatus;
        }

        @Override
        public boolean add(ActionInterface a) {
            return false;
        }

        @Override
        public boolean remove(ActionInterface a) {
            return false;
        }
    }
}