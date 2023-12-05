package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TriggerExitStatusProgramTest {
    TriggerExitStatusProgram trigger;
    File baseDirectory;

    @BeforeAll
    void init() {
        baseDirectory= Paths.get(System.getProperty("user.home"), "IfttGruppo2Save", "test").toFile();
    }

    @Test
    void constuctFailure() {
        assertThrowsExactly(IllegalArgumentException.class, ()-> new TriggerExitStatusProgram(null, 0, null) );
        assertThrowsExactly(IllegalArgumentException.class, ()-> new TriggerExitStatusProgram(null, 0, "0") );
        assertThrowsExactly(IllegalArgumentException.class, ()-> new TriggerExitStatusProgram(null, 0, "0", "1") );
    }

    @Test
    void checkException() throws IOException {
        File notExecutable= File.createTempFile("notExecutable", null, baseDirectory);

        trigger= new TriggerExitStatusProgram(notExecutable, 0, null);
        assertThrowsExactly(RuntimeException.class, () -> trigger.check() );

        trigger= new TriggerExitStatusProgram(notExecutable, 0, "0");
        assertThrowsExactly(RuntimeException.class, () -> trigger.check() );
    }

    @Test
    void checkExe() throws IOException {
        File executableExe= baseDirectory.toPath().resolve("executableReturnValueFirstArg.exe").toFile();

        //caso True senza passaggio di valori, con passaggio di un valore, con passaggio di più valori
        trigger= new TriggerExitStatusProgram(executableExe, 0, null);
        assertTrue(trigger.check());
        trigger= new TriggerExitStatusProgram(executableExe, 15, "15");
        assertTrue(trigger.check());
        trigger= new TriggerExitStatusProgram(executableExe, 15, "15", "20");
        assertTrue(trigger.check());

        //caso False senza passaggio di valori, con passaggio di un valore, con passaggio di più valori
        trigger= new TriggerExitStatusProgram(executableExe, -999, null);
        assertFalse(trigger.check());
        trigger= new TriggerExitStatusProgram(executableExe, -999, "15");
        assertFalse(trigger.check());
        trigger= new TriggerExitStatusProgram(executableExe, -999, "15", "20");
        assertFalse(trigger.check());

        //caso eccezione, file con estensione corretta ma non eseguibile
        File notExecutable= File.createTempFile("notExecutable", ".exe");
        trigger= new TriggerExitStatusProgram(notExecutable, 0, null);
        assertThrowsExactly(RuntimeException.class, ()-> trigger.check() );
    }

    @Test
    void checkJar() throws IOException {
        File executableJar= baseDirectory.toPath().resolve("executableReturnValueFirstArg.jar").toFile();

        //caso True senza passaggio di valori, con passaggio di un valore, con passaggio di più valori
        trigger= new TriggerExitStatusProgram(executableJar, 0, null);
        assertTrue(trigger.check());
        trigger= new TriggerExitStatusProgram(executableJar, 15, "15");
        assertTrue(trigger.check());
        trigger= new TriggerExitStatusProgram(executableJar, 15, "15", "20");
        assertTrue(trigger.check());

        //caso False senza passaggio di valori, con passaggio di un valore, con passaggio di più valori
        trigger= new TriggerExitStatusProgram(executableJar, -999, null);
        assertFalse(trigger.check());
        trigger= new TriggerExitStatusProgram(executableJar, -999, "15");
        assertFalse(trigger.check());
        trigger= new TriggerExitStatusProgram(executableJar, -999, "15", "20");
        assertFalse(trigger.check());

        //caso eccezione, file con estensione corretta ma non eseguibile
        File notExecutable= File.createTempFile("notExecutable", ".jar");
        trigger= new TriggerExitStatusProgram(notExecutable, 0, null);
        assertFalse( trigger.check() );
    }
}