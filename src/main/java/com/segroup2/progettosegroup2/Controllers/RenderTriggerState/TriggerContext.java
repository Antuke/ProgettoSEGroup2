package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import com.segroup2.progettosegroup2.Triggers.TriggerInterface;

public class TriggerContext {
    private RenderTrigger state;
    public TriggerContext(){
        state = null;
    }

    public RenderTrigger getState() {
        return state;
    }

    public void setState(RenderTrigger state) {

        this.state = state;
    }

    public TriggerInterface getReturnTrigger(){
        if(state == null) return null;
        return state.getTriggerInterface();
    }
}
