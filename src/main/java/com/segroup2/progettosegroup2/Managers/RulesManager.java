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
        rules = FXCollections.observableList(new LinkedList<Rule>());
        load();
    }

    public boolean addRule(Rule rule){
        boolean returnValue = rules.add(rule);
        saveAsync();
        return returnValue;
    }

    public boolean removeRule(Rule rule){
        Boolean returnValue = rules.remove(rule);
        saveAsync();
        return returnValue;
    }

    public void activateRule(Rule rule){
        rules.get(rules.indexOf(rule)).setActive(true);
        saveAsync();
    }

    public void deactivateRule(Rule rule){
        rules.get(rules.indexOf(rule)).setActive(false);
        saveAsync();
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

    private void load(){
        rules.setAll(RulePersistance.loadRules(savePath));
    }

    private void saveAsync() {
        Thread saveThread = new Thread(this::save);
        saveThread.start();
    }
}
