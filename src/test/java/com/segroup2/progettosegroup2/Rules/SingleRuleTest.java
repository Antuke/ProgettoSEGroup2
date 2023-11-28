package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.TestFX;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SingleRuleTest extends TestFX {

    @Test
    void executeTrueTest() {
        LocalTime time = LocalTime.now();
        SingleRule rule = new SingleRule(new TriggerTime(time.getHour(), time.getMinute()), new ActionAudio());
        Assertions.assertTrue(rule.isActive());
        if(rule.check()){
            Assertions.assertTrue(rule.execute());
            Assertions.assertFalse(rule.isActive());
        }
    }

    @Test
    void executeFalseTest() {
        LocalTime time = LocalTime.now();
        SingleRule rule = new SingleRule(new TriggerTime(time.getHour(), time.getMinute()), new ActionInterface() {
            @Override
            public boolean execute() {
                return false;
            }
        });
        Assertions.assertTrue(rule.isActive());
        if(rule.check()){
            Assertions.assertFalse(rule.execute());
            Assertions.assertTrue(rule.isActive());
        }
    }
}