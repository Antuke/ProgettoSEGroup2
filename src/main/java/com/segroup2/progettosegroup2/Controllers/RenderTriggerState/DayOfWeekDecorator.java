package com.segroup2.progettosegroup2.Controllers.RenderTriggerState;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * Utilizzo dell'enumerazione {@link DayOfWeek} nella lingua scelta
 */
public class DayOfWeekDecorator {
    private static final TextStyle style= TextStyle.FULL;
    private static Locale language= Locale.ITALIAN;

    /**
     * Modifica della lingua da utilizzare per tutti i metodi statici.
     * Se questo metodo non è invocato la lingua di default impostata è {@link Locale#ITALIAN}
     * @param language Lingua da scegliere, se il valore è {@code null} non verrà effettuata la modifica
     */
    public static void setLanguage(Locale language){
        if( language!=null )
            DayOfWeekDecorator.language= language;
    }

    /**
     * @return La lingua impostata correntemente
     */
    public static Locale getLanguage(){
        return language;
    }

    /**
     * Restituisce la collezione di enumerazione {@link DayOfWeek} nella lingua scelta
     * @param language Lingua da scegliere, se {@code null} imposta quella scelta tramite {@link #setLanguage(Locale)}
     * @return Un array contenente le stringe di questa lingua, nell'ordine in cui sono dichiarate nell'enumerazione
     */
    public static String[] values(Locale language){
        Locale internalLanguage= (language!=null) ? language : DayOfWeekDecorator.language;

        String[] value= new String[DayOfWeek.values().length];
        int i=0;
        for(DayOfWeek e : DayOfWeek.values()){
            value[i++] = e.getDisplayName(style, internalLanguage);
        }
        return value;
    }

    /**
     * Restituisce la collezione di enumerazione {@link DayOfWeek} nella lingua scelta tramite {@link #setLanguage(Locale)}
     * @return Un array contenente le stringe di questa lingua, nell'ordine in cui sono dichiarate nell'enumerazione
     */
    public static String[] values(){
        return values(null);
    }

    /**
     * Converte il giorno inserito nella lingua scelta
     * @param day Giorno di cui fare la conversione
     * @param language Lingua da scegliere, se {@code null} imposta quella scelta tramite {@link #setLanguage(Locale)}
     * @return Il giorno convertito, {@code null} se {@code day} è nullo
     */
    public static String valueOf(DayOfWeek day, Locale language){
        if( day==null )
            return null;

        Locale internalLanguage= (language!=null) ? language : DayOfWeekDecorator.language;
        return day.getDisplayName(style, internalLanguage);
    }

    /**
     * Converte il giorno inserito nella lingua scelta tramite {@link #setLanguage(Locale)}
     * @param day Giorno di cui fare la conversione
     * @return Il giorno convertito, {@code null} se {@code day} è nullo
     */
    public static String valueOf(DayOfWeek day){
        return valueOf(day, null);
    }

    /**
     * @param str Testo da convertire
     * @param language Lingua da scegliere, se {@code null} imposta quella scelta tramite {@link #setLanguage(Locale)}
     * @return L'enumerazione corrispondente al giorno passato come parametro
     * @throws IllegalArgumentException Se {@code str} è {@code null} oppure se non è possibile effettuare la conversione
     */
    public static DayOfWeek parseFromString(String str, Locale language) {
        if( str==null )
            throw new IllegalArgumentException("Il primo parametro deve essere non nullo");
        Locale internalLanguage= (language!=null) ? language : DayOfWeekDecorator.language;

        DateTimeFormatterBuilder formatterBuilder= new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_WEEK, style);
        DateTimeFormatter formatter= formatterBuilder.toFormatter(internalLanguage);

        try{
            return DayOfWeek.from(formatter.parse(str));
        }catch( DateTimeParseException e){
            throw new IllegalArgumentException("Nessun DayOfWeek corrispondente al messaggio: "+str+" in '"+language+"'");
        }
    }

    /**
     * La lingua utilizzata è quella imposta tramite {@link #setLanguage(Locale)}
     * @param str Testo da convertire
     * @return L'enumerazione corrispondente al giorno passato come parametro
     * @throws IllegalArgumentException Se {@code str} è {@code null} oppure se non è possibile effettuare la conversione
     */
    public static DayOfWeek parseFromString(String str) {
        return parseFromString(str, null);
    }
}