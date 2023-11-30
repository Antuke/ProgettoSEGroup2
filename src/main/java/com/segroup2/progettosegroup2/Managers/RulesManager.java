package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
public class RulesManager {
    private ObservableList<Rule> rules;
    private static RulesManager ruleManager;

    // locazione e nome del file salvataggio nelle risorse
    public final static String savePath = "Salvataggi/save.bin";

    private RulesManager(){
        rules = FXCollections.observableList(new LinkedList<>());
        load();
    }

    public boolean removeAll(ObservableList<Rule> toDelete){
        rules.removeAll(toDelete);
        saveAsync();
        return true;
    }

    public boolean addRule(Rule rule){
        boolean returnValue = rules.add(rule);
        saveAsync();
        return returnValue;
    }

    public boolean removeRule(Rule rule){
        boolean returnValue = rules.remove(rule);
        saveAsync();
        return returnValue;
    }

    public ObservableList<Rule> getRules(){
        return rules;
    }

    public static RulesManager getInstance(){
        if (ruleManager == null)
            ruleManager = new RulesManager();
        return ruleManager;
    }

    public void save(){
        RulePersistance.saveRules(savePath,new LinkedList<>(rules));
    }

    public void save(String saveTest){
        RulePersistance.saveRules(saveTest,new LinkedList<>(rules));
    }

    public void load(){
        rules.setAll(RulePersistance.loadRules(savePath));
    }

    public void load(String testSave){
        rules.setAll(RulePersistance.loadRules(testSave));
    }

    private void saveAsync() {
        /*Lancia un thread che esegue la funzione save*/
        Thread saveThread = new Thread(this::save);
        saveThread.start();
    }


}
