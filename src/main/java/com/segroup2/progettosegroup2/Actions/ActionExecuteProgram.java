package com.segroup2.progettosegroup2.Actions;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che esegue un programma esterno
 */
public class ActionExecuteProgram implements ActionInterface, Serializable {
    private final List<String> args;

    /**
     * @param program Programma da eseguire
     * @param params Parametri aggiuntivi passati al programma
     * @throws IllegalArgumentException Se {@code program} è nullo
     */
    public ActionExecuteProgram(File program, String... params){
        if( program==null )
            throw new IllegalArgumentException("The first parameter must be non-null.");

        args= new ArrayList<>();
        args.add( program.toString() );
        if( params!=null )
            args.addAll( List.of(params) );
    }

    /**
     * @return Vero se il programma è eseguito altrimenti falso
     * @throws RuntimeException Se l'estensione non è prevista
     * <br>
     * Estensioni previste: exe, jar
     */
    @Override
    public boolean execute() throws RuntimeException {
        List<String> internalArgs= new ArrayList<>(args);

        switch( FilenameUtils.getExtension(args.get(0)) ){
            case "exe" -> {}
            case "jar" -> { internalArgs.add(0, "java"); internalArgs.add(1, "-jar"); }
            default -> throw new RuntimeException("Extension '"+FilenameUtils.getExtension(args.get(0))+"' unexpected");
        }

        try{
            new ProcessBuilder(internalArgs).start().waitFor();
            return(true);
        }catch(IOException | InterruptedException e) {
            return(false);
        }
    }

    /**
     * Operazione non possibile in questa classe
     * @return False
     */
    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    /**
     * Operazione non possibile in questa classe
     * @return False
     */
    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }

    @Override
    public String toString() {
        return( "Program execution "+new File(args.get(0)).getName() ) + args;
    }
}
