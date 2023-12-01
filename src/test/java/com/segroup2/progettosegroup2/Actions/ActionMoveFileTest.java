package com.segroup2.progettosegroup2.Actions;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

class ActionMoveFileTest {

    @Test
    void execute_SpostaFile_FileSpostatoConSuccesso() {
        try {
            File fileOrigine = Files.createTempFile("fileOrigine", ".txt").toFile();
            File cartellaDestinazione = Files.createTempDirectory("cartellaDestinazione").toFile();
            File fileDestinazione = new File(cartellaDestinazione, fileOrigine.getName());

            ActionMoveFile actionMoveFile = new ActionMoveFile(fileOrigine, cartellaDestinazione);

            boolean risultato = actionMoveFile.execute();

            assertTrue(risultato);
            assertFalse(fileOrigine.exists());
            assertTrue(fileDestinazione.exists());
        } catch (IOException e) {
            fail("IOException inaspettata: " + e.getMessage());
        }
    }

    @Test
    void execute_FileOrigineNonEsiste_RuntimeExceptionLanciata() {
        try {
            File fileOrigine = File.createTempFile("fileToDelete", ".txt");
            Files.delete(fileOrigine.toPath());
            File cartellaDestinazione = Files.createTempDirectory("cartellaDestinazione").toFile();

            ActionMoveFile actionMoveFile = new ActionMoveFile(fileOrigine, cartellaDestinazione);

            assertThrows(RuntimeException.class, actionMoveFile::execute);
        } catch (IOException e) {
            fail("IOException inaspettata: " + e.getMessage());
        }
    }
}

