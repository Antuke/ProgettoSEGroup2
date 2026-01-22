package com.segroup2.progettosegroup2.Actions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionDisplayMessageTest {

    @Test
    void constructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new ActionDisplayMessage(null));
        assertDoesNotThrow(() -> new ActionDisplayMessage("Test message"));
    }
}
