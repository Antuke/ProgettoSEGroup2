package com.segroup2.progettosegroup2.Actions.Factory;

import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDialogBoxFactoryTest {

    @Test
    void correctCreation() {
        List<Object> obj= new ArrayList<>();

        assertInstanceOf(ActionDialogBox.class, new ActionDialogBoxFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, obj));
        assertInstanceOf(ActionDialogBox.class, new ActionDialogBoxFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, null));
    }

    @Test
    void exceptionCreation() {
        List<Object> objEmpty= new ArrayList<>();
        List<Object> objNotEmpty= new ArrayList<>();
        objNotEmpty.add(new Object());

        assertThrows(IllegalArgumentException.class, ()->{new ActionDialogBoxFactory().createConcreteClass(null, objEmpty);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionDialogBoxFactory().createConcreteClass(null, null);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionDialogBoxFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, objEmpty);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionDialogBoxFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, null);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionDialogBoxFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, objNotEmpty);});
    }
}