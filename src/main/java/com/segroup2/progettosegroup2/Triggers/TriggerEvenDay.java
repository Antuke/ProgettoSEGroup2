package com.segroup2.progettosegroup2.Triggers;

import java.time.LocalDate;








public class TriggerEvenDay implements TriggerInterface {

    @Override
    public boolean check() throws RuntimeException {
        return (LocalDate.now().getDayOfMonth() % 2 == 0);
    }

    @Override
    public boolean add(TriggerInterface t) {
        return false;
    }

    @Override
    public boolean remove(TriggerInterface t) {
        return false;
    }


    @Override
    public String toString() {
        return("Day is even");
    }
    

}
