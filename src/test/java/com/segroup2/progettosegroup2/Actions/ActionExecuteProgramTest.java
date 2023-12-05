package com.segroup2.progettosegroup2.Actions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActionExecuteProgramTest {
    File baseDirectory;
    ActionExecuteProgram action;

    @BeforeAll
    void init(){
        baseDirectory= Paths.get(System.getProperty("user.home"), "IfttGruppo2Save", "test").toFile();
    }

    @Test
    void constuctFailure() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new ActionExecuteProgram(null, null) );
        assertThrowsExactly(IllegalArgumentException.class, () -> new ActionExecuteProgram(null, "param 1") );
    }

    @Test
    void executeExe() throws IOException {
        File exeFile= baseDirectory.toPath().resolve("executableReturnValueFirstArg.exe").toFile();

        action= new ActionExecuteProgram(exeFile, null);
        assertTrue(action.execute());

        action= new ActionExecuteProgram(exeFile, "1");
        assertTrue(action.execute());


        File tempExe= File.createTempFile("executableExe", ".exe", baseDirectory);
        action= new ActionExecuteProgram(tempExe, null);
        assertFalse(action.execute());
    }

    @Test
    void executeJar() throws IOException {
        File jarFile= baseDirectory.toPath().resolve("executableReturnValueFirstArg.jar").toFile();

        action = new ActionExecuteProgram(jarFile, null);
        assertTrue(action.execute());

        action = new ActionExecuteProgram(jarFile, "1");
        assertTrue(action.execute());


        File tempExe = File.createTempFile("executableExe", ".jar", baseDirectory);
        action = new ActionExecuteProgram(tempExe, null);
        assertTrue(action.execute());
    }
}