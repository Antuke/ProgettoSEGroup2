package com.segroup2.progettosegroup2.Actions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ActionDeleteFileTest {

    static File fileToDelete;

    static ActionDeleteFile action;

    @BeforeAll
    static void beforeAll() throws IOException {
        fileToDelete = File.createTempFile("test","test");
        action = new ActionDeleteFile(fileToDelete);
    }
    @Test
    void executeTest() {
        assertTrue(action.execute());
        assertFalse(fileToDelete.exists());
    }
    @Test
    void executeTestTwo() {
        action = new ActionDeleteFile(null);
        assertThrows(RuntimeException.class,() -> action.execute());
    }
}