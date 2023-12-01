package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class RulesPersistance {
    private Path saveFilePath;
    private static String saveName = "save.bin";

    public static void setSaveName(String name){
        saveName = name;
    }
    public RulesPersistance() {
        saveFilePath = Paths.get(System.getProperty("user.home"), "IfttGruppo2Save", "saves");

        try {
            Files.createDirectories(saveFilePath);
            Path saveFile = saveFilePath.resolve(saveName);
            if (!Files.exists(saveFile)) {
                Files.createFile(saveFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRules(LinkedList<Rule> rules) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( saveFilePath.resolve(saveName).toString()))) {
            oos.writeObject(new ArrayList<>(rules));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Rule> loadRules() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveFilePath.resolve(saveName).toString()))) {
            return (ArrayList<Rule>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
