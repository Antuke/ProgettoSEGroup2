package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.layout.VBox;

public interface RenderTrigger {
    void render(VBox parent);
    TriggerInterface getTriggerInterface();
}
