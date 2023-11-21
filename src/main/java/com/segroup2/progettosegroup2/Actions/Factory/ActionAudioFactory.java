package com.segroup2.progettosegroup2.Actions.Factory;

import com.segroup2.progettosegroup2.Actions.ActionEnum;

import java.util.List;

/**
 * ConcreteFactory for {@link ActionFactory}
 */
class ActionAudioFactory extends ActionFactory{
    /**
     * Parameters passed through {@code obj} must be passed in the correct order and of the correct type
     * @param type must be {@link ActionEnum#ACTION_DEFAULT_AUDIO}
     * @param obj List of parameters to pass for the construction of the real object
     */
    public ActionAudioFactory(ActionEnum type, List<Object> obj) {
        if( type==null || !type.equals(ActionEnum.ACTION_DEFAULT_AUDIO) || (obj!=null && !obj.isEmpty()) ){
            if( type==null || !type.equals(ActionEnum.ACTION_DEFAULT_AUDIO) )
                throw new IllegalArgumentException("Type must be "+ActionEnum.ACTION_DEFAULT_AUDIO.toString());

            if( obj!=null && !obj.isEmpty() )
                throw new IllegalArgumentException("Number of arguments greater than 0");
        }
    }
}