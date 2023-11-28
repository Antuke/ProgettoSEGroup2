package com.segroup2.progettosegroup2;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestFX {
    private static boolean call = false;
    @BeforeAll
    public static void initFx(){
        if (!call){
            Platform.startup(()->{});
            call = true;
        }
    }
}
