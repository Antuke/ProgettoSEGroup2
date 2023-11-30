package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class RulePersistance {

    public static void saveRules(String filePath, LinkedList<Rule> rules) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( filePath))) {
            oos.writeObject(new ArrayList<>(rules));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Rule> loadRules(String filePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Rule>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
