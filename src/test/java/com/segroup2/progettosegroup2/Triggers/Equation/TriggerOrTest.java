package com.segroup2.progettosegroup2.Triggers.Equation;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriggerOrTest {

    static TriggerInterface t1;

    static TriggerInterface t2;

    static TriggerInterface t3;

    static TriggerOr equation;

    @BeforeAll
    static void beforeAll(){
        t1 = new TriggerOrTest.Temp(true);
        t2 = new TriggerOrTest.Temp(true);
        t3 = new TriggerOrTest.Temp(false);
        equation = new TriggerOr();
    }

    @AfterEach
    void afterEach(){
        equation.remove(t1);
        equation.remove(t2);
        equation.remove(t3);
    }


    @Test
    void add() {
        assertTrue(equation.add(t1));
        assertTrue(equation.add(t2));
        assertFalse(equation.add(t3));
    }

    @Test
    void check() {
        equation.add(t1);
        equation.add(t3);

        assertTrue(equation.check());

        equation.remove(t1);
        equation.add(t3);

        assertFalse(equation.check());
    }

    private static class Temp implements TriggerInterface {
        private boolean checkResult;
        public Temp (boolean s){
            this.checkResult = s;
        }
        @Override
        public boolean check() {
            return checkResult;
        }

    }
}