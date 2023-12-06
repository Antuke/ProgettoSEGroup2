package com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies;

import com.segroup2.progettosegroup2.Controllers.RenderActionsStrategies.RenderAction;

public class ContextAction implements RenderAction {

    private RenderAction strategy;

    public void setStrategy(RenderAction strategy) {
        this.strategy = strategy;
    }

    @Override
    public void render() {
        strategy.render();
    }
}
