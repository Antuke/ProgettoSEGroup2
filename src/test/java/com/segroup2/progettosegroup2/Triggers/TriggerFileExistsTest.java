package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TriggerFileExistsTest {

    TriggerFileExists trigger;

    @Test
    void testExists(){
        String filePath = "src/main/java/com/segroup2/progettosegroup2/Triggers/TriggerFileExists.java";
        File file = new File(getAbsolutePath(filePath));
        trigger = new TriggerFileExists(file);
        assertTrue(trigger.check());
    }

    @Test
    void testNotExists(){
        String filePath = "src/main/java/com/segroup2/progettosegroup2/Triggers/TriggerFileNotExists.java";
        File file = new File(getAbsolutePath(filePath));
        trigger = new TriggerFileExists(file);
        assertFalse(trigger.check());
    }

    private String getAbsolutePath(String relativePath) {
        Path path = Paths.get(relativePath);
        return path.toAbsolutePath().toString();
    }
}
