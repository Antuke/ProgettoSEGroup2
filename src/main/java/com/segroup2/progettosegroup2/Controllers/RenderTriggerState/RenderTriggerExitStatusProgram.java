package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerExitStatusProgram;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.layout.VBox;

public class RenderTriggerExitStatusProgram implements RenderTrigger{
    private TriggerExitStatusProgram trigger = null;
    @Override
    public void render(VBox parent) {

    }

    @Override
    public TriggerInterface getTriggerInterface() {
        return trigger;
    }
}
