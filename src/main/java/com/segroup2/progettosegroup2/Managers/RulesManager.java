package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Classe singleton per la gestione delle regola
 */
public class RulesManager {
    private ObservableList<Rule> rules;
    private PersistanceManager<Rule> rulesPersistance;
    private static RulesManager ruleManager;
    private static String fileName= "saveRules.bin";

    /* locazione e nome del file salvataggio nelle risorse */
    private RulesManager(){
        rules = FXCollections.observableList(new LinkedList<>());
        rulesPersistance = new PersistanceManager<>(fileName);
        load();
    }

    /**
     * Modifica il nome del file da salvare, se è nullo o vuoto non effettua la modifica.
     * Se esiste già un'istanza dell'oggetto questo metodo non viene eseguito
     */
    public static void setSaveName(String file){
        if( file!=null && ruleManager==null )
            fileName= "saveRules.bin";
    }

    /**
     * @return Il path della posizione del file dove vengono gestite le regole
     */
    public Path getFilePath(){
        return( rulesPersistance.getFilePath() );
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

    public synchronized ObservableList<Rule> getRules(){
        return rules;
    }

    public static RulesManager getInstance(){
        if (ruleManager == null)
            ruleManager = new RulesManager();
        return ruleManager;
    }

    public void save(){
        rulesPersistance.save(new LinkedList<>(rules));
    }

    public void load(){
        rules.setAll(rulesPersistance.load());
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