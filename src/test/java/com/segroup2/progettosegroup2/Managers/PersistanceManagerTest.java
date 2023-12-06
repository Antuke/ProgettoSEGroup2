package com.segroup2.progettosegroup2.Managers;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersistanceManagerTest{

    @Test
    void constuctSuccess(){
        Path defaultPath= new PersistanceManager<>(null, null).getFilePath();
        PersistanceManager<Integer> manager;
        String fileName= "test.bin";
        Path directoryPath= Path.of("directory_test");

        //caso nessun parametro passato
        manager= new PersistanceManager<>(null, null);
        assertEquals(defaultPath, manager.getFilePath());

        //caso un solo parametro passato
        manager= new PersistanceManager<>(fileName, null);
        assertEquals(defaultPath.getParent(), manager.getFilePath().getParent());
        assertEquals(fileName, manager.getFilePath().getFileName().toString());

        //caso entrambi i parametri passati
        manager= new PersistanceManager<>(fileName, directoryPath);
        assertEquals(directoryPath, manager.getFilePath().getParent());
        assertEquals(fileName, manager.getFilePath().getFileName().toString());
    }

    @Test
    void save() throws IOException {
        Path tempDirectory= Files.createTempDirectory("PersistanceManagerTest");
        String fileName= "saveTest.bin";
        File file= tempDirectory.resolve(fileName).toFile();

        PersistanceManager<Integer> manager= new PersistanceManager<>(fileName, tempDirectory);

        //salvataggio con cartella esistente
        manager.save( List.of(1, 2, 3, 4, 5) );
        assertTrue( file.isFile() );

        //salvataggio con cartella inesistente
        FileUtils.deleteDirectory( tempDirectory.toFile() );
        manager.save( List.of(1, 2, 3, 4, 5) );
        assertTrue( file.isFile() );
    }

    @Test
    void saveAndLoad() throws IOException {
        Path tempDirectory= Files.createTempDirectory("PersistanceManagerTest");
        String fileName= "saveTest.bin";

        PersistanceManager<Integer> manager= new PersistanceManager<>(fileName, tempDirectory);
        List<Integer> list= List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> loadList;

        //salvataggio e caricamento con file esistente
        manager.save(list);
        loadList= manager.load();
        assertIterableEquals(list, loadList);

        //salvataggio e caricamento con file inesistente
        FileUtils.deleteDirectory( tempDirectory.toFile() );
        loadList= manager.load();
        assertTrue( loadList.isEmpty() );
    }
}