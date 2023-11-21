package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.Rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class RulesManager {
    private List<Rule> rules;
    private static RulesManager ruleManager;

    private RulesManager(){
        rules = new ArrayList<>();
    }

    public boolean addRule(Rule rule){
        return rules.add(rule);
    }

    public boolean removeRule(Rule rule){
        return rules.remove(rule);
    }

    public List<Rule> getRules(){
        return rules;
    }

    public static RulesManager getInstance(){
        if (ruleManager == null)
            ruleManager = new RulesManager();
        return ruleManager;
    }

}
