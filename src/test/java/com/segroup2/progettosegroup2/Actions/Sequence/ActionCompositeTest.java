package com.segroup2.progettosegroup2.Actions.Sequence;

import com.segroup2.progettosegroup2.Actions.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionCompositeTest {
    static ActionComposite actionComposite;
    static Temp actionOne;
    static Temp actionTwo;
    static Temp actionThree;
    @BeforeAll
    static void beforeAll(){
         actionComposite = new ActionComposite();
         actionOne = new Temp(true);
         actionTwo = new Temp(true);
         actionThree = new Temp(false);
    }

    @AfterEach
     void afterEach(){
        actionComposite.remove(actionThree);
        actionComposite.remove(actionTwo);
        actionComposite.remove(actionOne);
    }

    @Test
    void execute() {
        actionComposite.add(actionOne);
        actionComposite.add(actionThree);
        actionComposite.add(actionTwo);

        assertFalse(actionComposite.execute());

        actionComposite.remove(actionThree);

        assertTrue(actionComposite.execute());
    }

    @Test
    void add() {
        assertTrue(actionComposite.add(actionOne));
        assertTrue(actionComposite.add(actionTwo));
        assertTrue(actionComposite.add(actionThree));
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