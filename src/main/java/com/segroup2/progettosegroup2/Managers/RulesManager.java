package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
/* TODO MAYBE, PASSARE UN ALTA CLASSE IL COMPITO DI CARICARE E SALVARE LE REGOLE SU FILE*/
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
        save();
        return returnValue;
    }

    public boolean removeRule(Rule rule){
        Boolean returnValue = rules.remove(rule);
        save();
        return returnValue;
    }

    public void activateRule(Rule rule){
        rules.get(rules.indexOf(rule)).setActive(true);
        save();
    }

    public void deactivateRule(Rule rule){
        rules.get(rules.indexOf(rule)).setActive(false);
        save();
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
        storeService.setOnSucceeded( e -> System.out.println("Salvato con successo"));
    }

    private void load(){
        LoadService ls = new LoadService(savePath);
        ls.start();
        ls.setOnSucceeded( e-> {
            rules.addAll(ls.getValue());
            System.out.println("Sessione ripristinata con successo");
            }
        );
    }

}
