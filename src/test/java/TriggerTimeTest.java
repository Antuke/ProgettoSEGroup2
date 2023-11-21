import com.segroup2.progettosegroup2.Triggers.TriggerTime;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TriggerTimeTest {


    @Test
    void checkTrue() {
        TriggerTime triggerTime = new TriggerTime(LocalTime.now().getHour(), LocalTime.now().getMinute());
        assertTrue(triggerTime.check());
    }

    @Test
    void checkFalse() {
        TriggerTime triggerTime = new TriggerTime(LocalTime.now().minusHours(2).getHour(), LocalTime.now().getMinute());
        assertFalse(triggerTime.check());
    }
}