package com.segroup2.progettosegroup2.Triggers;

public enum TriggerEnum {
    TRIGGER_TIME_OF_DAY("Time"),
    TRIGGER_DAY_OF_MONTH("Day of month"),
    TRIGGER_DAY_OF_WEEK("Day of week"),
    TRIGGER_DATE("Date"),
    TRIGGER_FILE_EXISTS("File exists"),
    TRIGGER_FILE_SIZE("File size"),
    TRIGGER_COMPARE_COUNTER_AND_VALUE("Compare between counter and value"),
    TRIGGER_COMPARE_COUNTERS("Compare between counters"),
    TRIGGER_EXIT_STATUS_PROGRAM("Compare exit status of a program");
    private final String message;
    TriggerEnum(String message){
        this.message=message;
    }
    @Override
    public String toString() {
        return message;
    }
    public static String[] stringValues(){
        String[] value = new String[TriggerEnum.values().length];
        int i=0;
        for(TriggerEnum e : TriggerEnum.values()){
            value[i] = e.toString();
            i++;
        }
        return value;
    }
    // Metodo statico per ottenere il valore dell'enum da un messaggio
    public static TriggerEnum fromMessage(String message) {
        for (TriggerEnum trigger : TriggerEnum.values()) {
            if (trigger.message.equals(message)) {
                return trigger;
            }
        }
        throw new IllegalArgumentException("No TriggerEnum corresponding to message: " + message);
    }
}
