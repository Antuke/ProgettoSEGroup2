package com.segroup2.progettosegroup2.Controllers.RenderActionsState;

import com.segroup2.progettosegroup2.Actions.ActionInterface;

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
