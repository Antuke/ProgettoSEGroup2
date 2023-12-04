package com.segroup2.progettosegroup2.Actions;

public interface ActionInterface{
    boolean execute() throws RuntimeException;

    boolean add(ActionInterface a);

    boolean remove(ActionInterface a);
}
