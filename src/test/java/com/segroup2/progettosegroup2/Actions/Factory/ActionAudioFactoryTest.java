package com.segroup2.progettosegroup2.Actions.Factory;

import com.segroup2.progettosegroup2.Actions.ActionAudio;
import com.segroup2.progettosegroup2.Actions.ActionEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionAudioFactoryTest {
    @Test
    void correctCreation() {
        List<Object> obj= new ArrayList<>();

        assertInstanceOf(ActionAudio.class, new ActionAudioFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, obj));
        assertInstanceOf(ActionAudio.class, new ActionAudioFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, null));
    }

    @Test
    void exceptionCreation() {
        List<Object> objEmpty= new ArrayList<>();
        List<Object> objNotEmpty= new ArrayList<>();
        objNotEmpty.add(new Object());

        assertThrows(IllegalArgumentException.class, ()->{new ActionAudioFactory().createConcreteClass(null, objEmpty);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionAudioFactory().createConcreteClass(null, null);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionAudioFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, objEmpty);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionAudioFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_DIALOGBOX, null);});
        assertThrows(IllegalArgumentException.class, ()->{new ActionAudioFactory().createConcreteClass(ActionEnum.ACTION_DEFAULT_AUDIO, objNotEmpty);});
    }
}