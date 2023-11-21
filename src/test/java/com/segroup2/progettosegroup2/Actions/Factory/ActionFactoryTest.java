package com.segroup2.progettosegroup2.Actions.Factory;

import com.segroup2.progettosegroup2.Actions.ActionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionFactoryTest {
    private ActionFactory action;

    @BeforeEach
    void setUp() {
        action= new ActionFactory();
    }

    @Test
    void createConcreteClassException() {
        List<Object> obj= new ArrayList<>();

        assertThrows(NullPointerException.class, ()->{ action.createConcreteClass(null, obj); });
        assertThrows(NullPointerException.class, ()->{ action.createConcreteClass(null, null); });
    }

    @Test
    void createConcreteClassSuccessful() {
        List<Object> obj= new ArrayList<>();

        assertInstanceOf(ActionFactory.class, action.createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, null));
        assertInstanceOf(ActionFactory.class, action.createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, obj));

        assertInstanceOf(ActionFactory.class, action.createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, null));
        assertInstanceOf(ActionFactory.class, action.createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, obj));
    }

    @Test
    void execute() {
        assertThrows(NullPointerException.class, ()->{ action.execute(); });
    }
}