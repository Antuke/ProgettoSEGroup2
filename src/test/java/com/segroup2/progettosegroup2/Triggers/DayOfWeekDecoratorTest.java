package com.segroup2.progettosegroup2.Triggers;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class DayOfWeekDecoratorTest {
    @Test
    void testSetLanguage() {
        Locale language;

        language= DayOfWeekDecorator.getLanguage();
        DayOfWeekDecorator.setLanguage(null);
        assertEquals( DayOfWeekDecorator.getLanguage(), language );

        language= Locale.ENGLISH;
        DayOfWeekDecorator.setLanguage(language);
        assertEquals(language, DayOfWeekDecorator.getLanguage());
    }

    @Test
    void conversion(){
        DayOfWeek day= DayOfWeek.SUNDAY;

        //controllo conversione con lingua standard
        String dayConverted= DayOfWeekDecorator.valueOf(day);
        DayOfWeek dayInverseConverted= DayOfWeekDecorator.parseFromString(dayConverted);
        assertEquals(day, dayInverseConverted);

        //controllo conversione con lingua modificata
        dayConverted= DayOfWeekDecorator.valueOf(day, Locale.FRENCH);
        dayInverseConverted= DayOfWeekDecorator.parseFromString(dayConverted, Locale.FRENCH);
        assertEquals(day, dayInverseConverted);
    }

    @Test
    void conversionFailed(){
        assertNull( DayOfWeekDecorator.valueOf(null) );
        assertNull( DayOfWeekDecorator.valueOf(null, Locale.GERMAN) );

        assertThrowsExactly(IllegalArgumentException.class, () -> DayOfWeekDecorator.parseFromString("") );
        assertThrowsExactly(IllegalArgumentException.class, () -> DayOfWeekDecorator.parseFromString("", Locale.GERMAN) );
    }

    @Test
    void testValues(){
        String[] values;

        values= DayOfWeekDecorator.values();
        for(int i=0; i<values.length; i++){
            assertEquals(DayOfWeekDecorator.parseFromString(values[i]), DayOfWeek.of(i+1));
        }

        Locale language= Locale.GERMAN;
        values= DayOfWeekDecorator.values(language);
        for(int i=0; i<values.length; i++){
            assertEquals(DayOfWeekDecorator.parseFromString(values[i], language), DayOfWeek.of(i+1));
        }
    }
}