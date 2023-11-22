package com.segroup2.progettosegroup2.Actions.Factory;

import com.segroup2.progettosegroup2.Actions.ActionDialogBox;
import com.segroup2.progettosegroup2.Actions.ActionEnum;
import com.segroup2.progettosegroup2.Actions.ActionInterface;

import java.util.List;

/**
 * ConcreteFactory for {@link ActionFactory}
 */
class ActionDialogBoxFactory extends ActionFactory{
    /**
     * Parameters passed through {@code obj} must be passed in the correct order and of the correct type
     * @param type must be {@link ActionEnum#ACTION_DEFAULT_DIALOGBOX}
     * @param obj List of parameters to pass for the construction of the real object
     * @return An object of type {@link ActionDialogBox}
     */
    @Override
    public ActionInterface createConcreteClass(ActionEnum type, List<Object> obj) {
        if( type==null || !type.equals(ActionEnum.ACTION_DEFAULT_DIALOGBOX) || (obj!=null && !obj.isEmpty()) ){
            if( type==null || !type.equals(ActionEnum.ACTION_DEFAULT_DIALOGBOX) )
                throw new IllegalArgumentException("Type must be "+ActionEnum.ACTION_DEFAULT_DIALOGBOX.toString());

            if( obj!=null && !obj.isEmpty() )
                throw new IllegalArgumentException("Number of arguments greater than 0");
        }

        return new ActionDialogBox();
    }
}
