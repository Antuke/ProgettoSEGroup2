package com.segroup2.progettosegroup2.Actions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ActionAppendToFileTest {

    static File fileToAppend;

    static ActionAppendToFile action;

    @BeforeAll
    static void beforeAll() throws IOException {
        /* creo un file temporaneo */
       fileToAppend = File.createTempFile("test","test");
       action = new ActionAppendToFile("test",fileToAppend);

    }
    @Test
    void executeTest() {
        action.execute();
        System.out.println(fileToAppend.getPath());
        assertEquals("test\r\n".length(), fileToAppend.length());
    }

    @Test
    void executeTestTwo(){
        action = new ActionAppendToFile("test",new File("err"));
        assertThrows(RuntimeException.class, ()->action.execute());

    }
}