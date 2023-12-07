package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import javafx.scene.layout.VBox;

public interface RenderAction {
    void render(VBox parent);

    ActionInterface getAction();
}
