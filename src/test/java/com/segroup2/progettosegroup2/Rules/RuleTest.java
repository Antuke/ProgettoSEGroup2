package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionAppendToFIle;
import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerDayOfMonth;
import com.segroup2.progettosegroup2.Triggers.TriggerDayOfWeek;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

class RuleTest{

    private static Rule rule;
    private static TriggerInterface trigger;

    @BeforeEach
    public void setUp() {
        ActionInterface action = new Temp(true);
        trigger = new TriggerTime(LocalTime.now().getHour(), LocalTime.now().getMinute());
        rule = new Rule(trigger, action);
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
        rule = new Rule(trigger, new Temp(false));
        Assertions.assertFalse(rule.execute());
    }

    @Test
    void setActiveTest(){
        rule.setActive(false);
        Assertions.assertFalse(rule.isActive());
        rule.setActive(true);
        Assertions.assertTrue(rule.isActive());
    }

    @Test
    void isActiveTest(){
        Assertions.assertTrue(rule.isActive()); // Una regola è attiva dopo la sua inizializzazione
    }

    @Test
    void getTriggerTest(){
        Assertions.assertInstanceOf(TriggerTime.class , rule.getTrigger());
        rule = new Rule(new TriggerDayOfMonth(20), new Temp(true));
        Assertions.assertInstanceOf(TriggerDayOfMonth.class , rule.getTrigger());
        rule = new Rule (new TriggerDayOfWeek(DayOfWeek.FRIDAY), new Temp(true));
        Assertions.assertInstanceOf(TriggerDayOfWeek.class, rule.getTrigger());
    }

    @Test
    void getActionTest(){
        Assertions.assertInstanceOf(Temp.class, rule.getAction());
        rule = new Rule(trigger, new ActionAudio());
        Assertions.assertInstanceOf(ActionAudio.class,rule.getAction());
        rule = new Rule(trigger, new ActionDialogBox());
        Assertions.assertInstanceOf(ActionDialogBox.class, rule.getAction());
        rule = new Rule(trigger, new ActionAppendToFIle(null,null));
        Assertions.assertInstanceOf(ActionAppendToFIle.class,rule.getAction());
    }

    @Test
    void isActivePropertyTest(){
        Assertions.assertTrue(rule.isActiveProperty().get());
        rule.setActive(false);
        Assertions.assertFalse(rule.isActiveProperty().get());
    }
    private static class Temp implements ActionInterface{
        private boolean executionResult;
        public Temp (boolean s){
            this.executionResult = s;
        }
        @Override
        public boolean execute() {
            return executionResult;
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