package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
class SingleRuleTest{

    /**
     * Se l'esecuzione dell'azione va a buon fine (restituisce true) allora l'azione viene disattivata
     */
    @Test
    void executeTrueTest() {
        LocalTime time = LocalTime.now();
        SingleRule rule = new SingleRule(new TriggerTime(time.getHour(), time.getMinute()), new Temp(true));
        //La regola appena creata è attiva
        Assertions.assertTrue(rule.isActive());
        Assertions.assertTrue(rule.execute());
        // La regola è disattiva perché l'esecuzione dell'azione è andata a buon fine
        Assertions.assertFalse(rule.isActive());
    }

    /**
     * Se l'esecuzione dell'azione non va a buon fine (restituisce false) allora l'azione non viene disattivata
     */
    @Test
    void executeFalseTest() {
        LocalTime time = LocalTime.now();
        // Creo una nuova regola con un'azione che restituisce false
        SingleRule rule = new SingleRule(new TriggerTime(time.getHour(), time.getMinute()), new Temp(false));
        Assertions.assertTrue(rule.isActive());
        Assertions.assertFalse(rule.execute());
        Assertions.assertTrue(rule.isActive());

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
    }
}