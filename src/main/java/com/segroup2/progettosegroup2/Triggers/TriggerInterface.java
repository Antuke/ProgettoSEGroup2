package com.segroup2.progettosegroup2.Triggers;

import java.io.Serializable;

public interface TriggerInterface extends Serializable {
    boolean check() throws RuntimeException;
}
