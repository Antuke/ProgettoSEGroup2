package com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.layout.VBox;

public interface RenderAction {
    void render(VBox parent);

    ActionInterface getAction();
}
