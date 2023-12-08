package com.segroup2.progettosegroup2.Managers;

public interface Observable {
    void subscribe(ListenerInterface cli);
    void notifyListeners();
}
