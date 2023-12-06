package com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Controllers.RenderTriggerState.RenderTrigger;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import javafx.scene.layout.VBox;

public class ActionContext  {

    private RenderAction state;
    public ActionContext(){
        state = null;
    }

    public RenderAction getState() {
        return state;
    }

    public void setState(RenderAction state) {
        this.state = state;
    }

    public ActionInterface getReturnAction(){
        return state.getAction();
    }
}
