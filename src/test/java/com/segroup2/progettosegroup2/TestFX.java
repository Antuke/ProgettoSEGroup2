package com.segroup2.progettosegroup2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestFX{
    private static boolean once=false;
    @BeforeAll
    public static void initFx(){
        if (!once) {
            Platform.startup(()->{});
            once=true;
        }
    }
}
