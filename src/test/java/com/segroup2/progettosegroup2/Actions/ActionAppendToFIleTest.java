package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.MainApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ActionAppendToFIleTest {

    static File fileToAppend;

    static ActionAppendToFIle action;

    @BeforeAll
    static void beforeAll() throws IOException {
        /* creo un file temporaneo */
       fileToAppend = File.createTempFile("test","test");
       action = new ActionAppendToFIle("test",fileToAppend);

    }
    @Test
    void executeTest() {
        action.execute();
        System.out.println(fileToAppend.getPath());
        assertEquals("test\r\n".length(), fileToAppend.length());
    }

    @Test
    void executeTestTwo(){
        action = new ActionAppendToFIle("test",new File("err"));
        assertThrows(RuntimeException.class, ()->action.execute());

    }
}