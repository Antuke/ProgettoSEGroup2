package com.segroup2.progettosegroup2.Actions;

/**
 * Possible choices for creating objects of ActionInterface
 */
public enum ActionEnum {
    ACTION_DEFAULT_AUDIO("Play a default audio file"),
    ACTION_DEFAULT_DIALOGBOX("Show a default message"),
    ACTION_APPEND_TO_FILE("Append a string to a .txt file"),
    ACTION_COPY_FILE("Copy a file into a directory"),
    ACTION_DELETE_FILE("Delete a file"),
    ACTION_MOVE_FILE("Move a file to another directory"),
    ACTION_ADD_CONSTANT("Add a costant value to a counter"),
    ACTION_SUM_COUNTER("Add a counter's value to a counter"),
    ACTION_SET_COUNTER("Set counter"),
    ACTION_EXECUTE_PROGRAM("Execute a external pogram");

    private final String message;
    ActionEnum(String message){
        this.message=message;
    }
    @Override
    public String toString() {
        return message;
    }
    public static String[] stringValues(){
        String[] value = new String[ActionEnum.values().length];
        int i=0;
        for(ActionEnum e : ActionEnum.values()){
            value[i] = e.toString();
            i++;
        }
        return value;
    }

    // Metodo statico per ottenere il valore dell'enum da un messaggio
    public static ActionEnum fromMessage(String message) {
        for (ActionEnum action : ActionEnum.values()) {
            if (action.message.equals(message)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Nessun ActionEnum corrispondente al messaggio: " + message);
    }
}
