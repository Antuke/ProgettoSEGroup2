package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;



public class SleepingRule extends Rule implements Serializable {

    private Duration sleeping;
    private LocalDateTime lastExecuted;
    public SleepingRule(TriggerInterface trigger, ActionInterface action, int day, int hours, int minutes) throws IllegalArgumentException{
        super(trigger, action);
        if (minutes>=60 || minutes < 0)
            throw new IllegalArgumentException("I minuti devono essere compresi tra 0 e 59");
        if  (hours>=24 || hours < 0)
            throw new IllegalArgumentException("Le ore devono essere comprese tra 0 e 23");
        if (day < 0)
            throw new IllegalArgumentException("I giorni non possono essere negativi");
        long min = ((long) day *24*60) + (hours*60) + minutes;
        sleeping = Duration.ofMinutes(min).truncatedTo(ChronoUnit.MINUTES);
        lastExecuted=null;
    }

    @Override
    public boolean check(){
        return super.getTrigger().check();
    }
    public Duration getSleepingPeriod(){
        return sleeping;
    }
    @Override
    public boolean execute() {
        boolean status = super.execute();
        if (status){
            lastExecuted = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            setActive(false);
        }
        return status;
    }

    public boolean isToReactivate(){
        if (lastExecuted!=null) {
            LocalDateTime setActiveTime = lastExecuted.plusMinutes(sleeping.toMinutes());
            boolean result =  LocalDateTime.now().isAfter(setActiveTime);
            if (result)
                lastExecuted=null;
            return result;
        }
        return false;
    }



    @Override
    public String toString() {
        return "SleepingRule{" +
                "sleeping=" + sleeping +
                ", lastExecuted=" + lastExecuted +
                '}';
    }

    // Ridefinisco il modo in cui viene serializzato l'oggetto aggiungendo gli attributi non presenti nella classe padre nella logica di serializzazione
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeLong(sleeping.toMinutes());
        s.writeObject(lastExecuted);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        sleeping = Duration.ofMinutes(s.readLong());
        lastExecuted = (LocalDateTime) s.readObject();

    }

}
