package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.LinkedList;
public class RulesManager {
    private ObservableList<Rule> rules;

    private RulesPersistance rulesPersistance;
    private static RulesManager ruleManager;

    /* locazione e nome del file salvataggio nelle risorse */





    private RulesManager(){
        rules = FXCollections.observableList(new LinkedList<>());
        rulesPersistance = new RulesPersistance();
        load();
    }


    public boolean removeAll(ObservableList<Rule> toDelete){
        rules.removeAll(new LinkedList<>(toDelete));
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
        rulesPersistance.saveRules(new LinkedList<>(rules));
    }

    public void load(){
        rules.setAll(rulesPersistance.loadRules());
    }

    private void saveAsync() {
        /*Lancia un thread che esegue la funzione save*/
        Thread saveThread = new Thread(this::save);
        saveThread.start();
    }

    public void clear(){
        rules = FXCollections.observableArrayList();
    }

}
