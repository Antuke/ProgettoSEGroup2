package com.segroup2.progettosegroup2.Actions.Sequence;

import com.segroup2.progettosegroup2.Actions.ActionInterface;

import java.util.LinkedList;

public class ActionComposite implements ActionInterface {

    private LinkedList<ActionInterface> children;

    public ActionComposite() {
        this.children = new LinkedList<>();
    }

    @Override
    public boolean execute() throws RuntimeException {

        for(ActionInterface a : children){
            if(!a.execute()) return false;
        }
        return true;
    }

    public boolean add(ActionInterface a){
        return children.add(a);
    }

    public boolean remove(ActionInterface a){
        return children.remove(a);
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        for(ActionInterface a: children)
            string.append(a+"\n");
        return string.toString();
    }


}
