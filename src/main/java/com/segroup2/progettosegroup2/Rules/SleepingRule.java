package com.segroup2.progettosegroup2.Rules;

import com.segroup2.progettosegroup2.Actions.ActionInterface;
import com.segroup2.progettosegroup2.Triggers.TriggerInterface;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public class SleepingRule extends Rule {

    private Duration sleeping;
    private LocalDateTime lastExecuted;
    public SleepingRule(TriggerInterface trigger, ActionInterface action, int day, int hours, int minutes) throws IllegalArgumentException{
        super(trigger, action);
        if (minutes>=60 || minutes < 0)
            throw new IllegalArgumentException("Minutes must be between 0 and 59");
        if  (hours>=24 || hours < 0)
            throw new IllegalArgumentException("Hours must be between 0 and 23");
        if (day < 0)
            throw new IllegalArgumentException("Days can't be negative");
        long min = ((long) day *24*60) + (hours*60) + minutes;
        sleeping = Duration.ofMinutes(min).truncatedTo(ChronoUnit.MINUTES);
        lastExecuted=null;
    }

    public Duration getSleepingPeriod(){
        return sleeping;
    }
    @Override
    public boolean execute() {
        boolean status = super.execute();
        if (status){
            lastExecuted = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            super.setActive(false);
        }
        return status;
    }

    public boolean isToReactivate(){
        if (lastExecuted!=null) {
            LocalDateTime setActiveTime = lastExecuted.plusMinutes(sleeping.toMinutes());
            boolean result =  LocalDateTime.now().isAfter(setActiveTime);
            if (result) {
                lastExecuted = null;
                super.setFired(false);
            }
            return result;
        }
        return false;
    }



    @Override
    public String getDetail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy    HH:mm:ss");
        String next = formatter.format(lastExecuted.plusMinutes(sleeping.toMinutes()));
        String last = formatter.format(lastExecuted);
        long days = sleeping.toDays();
        long hours = sleeping.toHours() % 24;
        long minutes = sleeping.toMinutes() % 60;
        String sleepingString = days+" days, "+hours+" hours and "+minutes+" minutes";
        return "Type:\tSleeping\nSleeping period:\t"+sleepingString+"\nLast execution:\t"+last+"\nNext activation:\t"+next+"\n\n"+super.toString();
    }


}
