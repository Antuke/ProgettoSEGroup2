package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/* TODO MAYBE, PASSARE UN ALTA CLASSE IL COMPITO DI CARICARE E SALVARE LE REGOLE SU FILE*/
public class RulesManager {
    private ObservableList<Rule> rules;
    private static RulesManager ruleManager;
    public final static String savePath = "Salvataggi/save.bin";

    private RulesManager(){
        rules = FXCollections.observableList(new LinkedList<Rule>());
        load();
    }

    public boolean addRule(Rule rule){
        boolean returnValue = rules.add(rule);
        save();
        return returnValue;
    }

    public boolean removeRule(Rule rule){
        Boolean returnValue = rules.remove(rule);
        save();
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

    public void save(){
        StoreService storeService = new StoreService(rules,savePath);
        storeService.start();

    }

    private void load(){
        LoadService ls = new LoadService(savePath);
        ls.start();
        ls.setOnSucceeded( e-> rules.addAll(ls.getValue())
        );
    }

}
