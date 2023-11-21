package com.segroup2.progettosegroup2.Actions;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionAudioTest {
    private static ActionInterface action;

    @BeforeAll
    static void beforeAll() {
        Platform.startup(()->{});
        action= new ActionAudio();
    }

    @AfterAll
    static void afterAll() {
        Platform.exit();
    }

    @Test
    void execute() {
        assertEquals(true, action.execute());
    }
}