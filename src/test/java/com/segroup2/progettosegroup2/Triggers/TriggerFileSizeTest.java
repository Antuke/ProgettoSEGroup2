package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TriggerFileSizeTest {
    
    private static final String TEST_DIRECTORY = "tmp";

    @Test
    public void testSizeMatch() throws IOException {
        File testFile = createFile("test.txt", 50);

        /*
        Creo un trigger che controlla la dimensione del file, fornendogli la stessa dimensione del File (File: 50 byte, Trigger: 50 byte)
        Risultato atteso: trigger.check() restituirà 'true'
        */
        TriggerFileSize trigger = new TriggerFileSize(testFile, 50);

        assertTrue(trigger.check());

        deleteFile(testFile);
    }

    @Test
    public void testSizeNotMatch() throws IOException {
        File testFile = createFile("test.txt", 50);

        /*
        Creo un trigger che controlla la dimensione del file, fornendogli dimensione differente da quella del File (File: 50 byte, Trigger: 100 byte)
        Risultato atteso: trigger.check() restituirà 'false'
        */
        TriggerFileSize trigger = new TriggerFileSize(testFile, 100);

        assertFalse(trigger.check());

        deleteFile(testFile);
    }

    private File createFile(String fileName, long fileSize) throws IOException {
        File testDirectory = new File(TEST_DIRECTORY);
        testDirectory.mkdirs();

        File testFile = new File(testDirectory, fileName);
        testFile.createNewFile();

        //Inserisco fileSize byte nel file
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(testFile)) {
            byte[] data = new byte[(int) fileSize];
            fos.write(data);
        }

        return testFile;
    }

    private void deleteFile(File file) {
        //Elimino il file creato
        File directory = file.getParentFile();
        if (file.exists()) {
            file.delete();
        }
        //Elimino la directory creata
        if (directory.exists()) {
            directory.delete();
        }
    }
}