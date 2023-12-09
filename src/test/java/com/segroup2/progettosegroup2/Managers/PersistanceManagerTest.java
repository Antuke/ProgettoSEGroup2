package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Counters.Counter;
import com.segroup2.progettosegroup2.Rules.Rule;
import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistanceManagerTest{

    static RulesManager rule;
    static CountersManager counter;


    @BeforeAll
    public static void init(){
        rule = RulesManager.getInstance();
        rule.setRules(List.of(new Rule(new TriggerTime(10, 10), new TempAction())));
        counter = CountersManager.getInstance();
        counter.setCounters(List.of(new Counter("cnt",20), new Counter("cnt2",20)));
    }

    @Test
    void constuctSuccess(){
        Path defaultPath= new PersistanceManager(null, null).getFilePath();
        PersistanceManager manager;
        String fileName= "test.bin";
        Path directoryPath= Path.of("directory_test");

        //caso nessun parametro passato
        manager= new PersistanceManager(null, null);
        assertEquals(defaultPath, manager.getFilePath());

        //caso un solo parametro passato
        manager= new PersistanceManager(fileName, null);
        assertEquals(defaultPath.getParent(), manager.getFilePath().getParent());
        assertEquals(fileName, manager.getFilePath().getFileName().toString());

        //caso entrambi i parametri passati
        manager= new PersistanceManager(fileName, directoryPath);
        assertEquals(directoryPath, manager.getFilePath().getParent());
        assertEquals(fileName, manager.getFilePath().getFileName().toString());
    }

    @Test
    void save() throws IOException {
        Path tempDirectory= Files.createTempDirectory("PersistanceManagerTest");
        String fileName= "saveTest.bin";
        File file= tempDirectory.resolve(fileName).toFile();
        PersistanceManager manager= new PersistanceManager(fileName, tempDirectory);

        //salvataggio con cartella esistente
        manager.save();
        assertTrue( file.isFile() );

        //salvataggio con cartella inesistente
        FileUtils.deleteDirectory( tempDirectory.toFile() );
        manager.save();
        assertTrue( file.exists() );
    }

    @Test
    void loadTest() throws IOException {
        Path tempDirectory= Files.createTempDirectory("PersistanceManagerTest");
        String fileName= "saveTest.bin";

        PersistanceManager manager= new PersistanceManager(fileName, tempDirectory);
        List<Counter> counters = new LinkedList<>(counter.getCounters());
        List<Rule> rules = new LinkedList<>(rule.getRules());
        //salvataggio e caricamento con file esistente
        manager.save();
        //Pulisco le liste dei manager
        counter.clear();
        rule.clear();
        manager.load();

        Assertions.assertEquals(counters.size(),counter.getCounters().size());
        Assertions.assertEquals(rules.size(), rule.getRules().size());

        //salvataggio e caricamento con file inesistente
        FileUtils.deleteDirectory( tempDirectory.toFile() );
        manager.load();
        assertTrue( rule.getRules().isEmpty() );
        assertTrue(CountersManager.getInstance().getCounters().isEmpty());
    }
    private static class TempAction implements ActionInterface{

        @Override
        public boolean execute() throws RuntimeException {
            return false;
        }

        @Override
        public boolean add(ActionInterface a) {
            return false;
        }

        @Override
        public boolean remove(ActionInterface a) {
            return false;
        }
    }
}