package com.segroup2.progettosegroup2.Actions.Factory;

import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Actions.ActionInterface;

import java.util.List;

/**
 * Factory class for creation of actions
 */
public class ActionFactory implements ActionInterface {
    private ActionInterface action;

    /**
     * Parameters passed through {@code obj} must be passed in the correct order and of the correct type
     * @param type type of object that want to create
     * @param obj List of parameters to pass for the construction of the real object
     * @exception IllegalArgumentException if the passed arguments do not satisfy the correct order and type
     * @exception NullPointerException if the {@code type} parameter is {@code null}
     */
    public ActionInterface createConcreteClass(ActionEnum type, List<Object> obj){
        if( type==null )
            throw new NullPointerException("Type must not be null");

        switch(type){
            case ACTION_DEFAULT_AUDIO -> { action= new ActionAudioFactory(type, obj); }
            case ACTION_DEFAULT_DIALOGBOX -> { action= new ActionDialogBoxFactory(type, obj); }
            default -> { throw new UnsupportedOperationException("Operation not yet implemented"); }
        }

        return action;
    }

    /**
     * @return true if the operation was successful otherwise false
     */
    @Override
    public boolean execute() {
        throw new NullPointerException("Method not usable; use createConcreteClass method for object creation");
    }
}
