package com.segroup2.progettosegroup2.Triggers;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Condizione che verifica l'exit status di un programma
 */
public class TriggerExitStatusProgram implements TriggerInterface, Serializable {
    private final List<String> args;
    private final Integer value;

    /**
     * @param program Programma da esguire
     * @param value Valore con cui confrontare il valore
     * @param params Parametri aggiuntivi passati al programma
     * @throws IllegalArgumentException Se {@code program} è nullo
     */
    public TriggerExitStatusProgram(File program, int value, String... params) {
        if( program==null )
            throw new IllegalArgumentException("Il primo parametro deve essere non nullo");

        this.value= value;
        args= new ArrayList<>();
        args.add( program.toString() );
        if( params!=null )
            args.addAll( List.of(params) );
    }

    /**
     * Richiama il costuttore principale passando cone terzo parametro {@code null}<br>
     * {@link TriggerExitStatusProgram#TriggerExitStatusProgram(File, int, String...)}
     * @param program Programma da esguire
     * @param value Valore con cui confrontare il valore
     * @throws IllegalArgumentException Se {@code program} è nullo
     */
    public TriggerExitStatusProgram(File program, int value){
        this(program, value, null);
    }

    /**
     * @return Vero se la condizione è verificata altrimenti falso
     * @throws RuntimeException Se l'estensione non è prevista oppure se si verifica un errore durante l'esecuzione del programma
     * <br>
     * Estensioni previste: exe, jar
     */
    @Override
    public boolean check() throws RuntimeException {
        List<String> internalArgs= new ArrayList<>(args);

        switch( FilenameUtils.getExtension(args.get(0)) ){
            case "exe" -> {}
            case "jar" -> { internalArgs.add(0, "java"); internalArgs.add(1, "-jar"); }
            default -> throw new RuntimeException("Estensione '"+FilenameUtils.getExtension(args.get(0))+"' non prevista");
        }
        
        try {
            int exitValue= new ProcessBuilder(internalArgs).start().waitFor();
            return( value.equals(exitValue) );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Impossibile eseguire, errore "+e.getMessage());
        }
    }

    /**
     * Operazione non possibile per questa classe
     * @return False
     */
    @Override
    public boolean add(TriggerInterface t) {
        return false;
    }

    /**
     * Operazione non possibile per questa classe
     * @return False
     */
    @Override
    public boolean remove(TriggerInterface t) {
        return false;
    }

    @Override
    public String toString() {
        return( "Trigger exit status di '"+new File(args.get(0)).getName()+"' con valore "+value);
    }
}
