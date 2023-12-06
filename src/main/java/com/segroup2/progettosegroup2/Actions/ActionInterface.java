package com.segroup2.progettosegroup2.Actions;

import java.io.Serializable;

public interface ActionInterface extends Serializable {
    boolean execute() throws RuntimeException;

    boolean add(ActionInterface a);

    boolean remove(ActionInterface a);
}
