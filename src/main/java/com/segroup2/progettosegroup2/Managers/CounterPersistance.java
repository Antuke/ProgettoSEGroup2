package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Counters.Counter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class CounterPersistance {
    private Path saveFilePath;
    private static String saveName = "saveCounters.bin";

    public static void setSaveName(String name){
        saveName = name;
    }
    public CounterPersistance() {
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

    public void saveCounters(LinkedList<Counter> counters) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( saveFilePath.resolve(saveName).toString()))) {
            oos.writeObject(new ArrayList<>(counters));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Counter> loadCounters() {
        if(saveFilePath.resolve(saveName).toFile().length()!=0) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveFilePath.resolve(saveName).toString()))) {
                return (ArrayList<Counter>) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
