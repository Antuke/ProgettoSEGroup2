package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RulesManager {
    private ObservableList<Rule> rules;
    private static RulesManager ruleManager;

    private RulesManager(){
        rules = FXCollections.observableList(new LinkedList<Rule>());
    }

    public boolean addRule(Rule rule){
        return rules.add(rule);
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

}
