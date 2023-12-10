package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe singleton per la gestione delle regole
 */
public class RulesManager {
    private ObservableList<Rule> rules;
    private static RulesManager ruleManager;

    /* locazione e nome del file salvataggio nelle risorse */
    private RulesManager(){
        rules = FXCollections.observableList(new LinkedList<>());
    }
    public void setRules(List<Rule> r) {
        rules = FXCollections.observableList(r);
    }

    public boolean removeAll(ObservableList<Rule> toDelete){
        return rules.removeAll(new LinkedList<>(toDelete));
    }

    public boolean addRule(Rule rule){
        return  rules.add(rule);
    }

    public boolean removeRule(Rule rule){
        return rules.remove(rule);
    }

    public ObservableList<Rule> getRules(){
        return rules;
    }

    public static RulesManager getInstance(){
        if (ruleManager == null)
            ruleManager = new RulesManager();
        return ruleManager;
    }

    public void clear(){
        rules = FXCollections.observableArrayList();
    }
}