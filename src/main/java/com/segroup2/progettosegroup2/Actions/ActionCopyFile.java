package com.segroup2.progettosegroup2.Actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Azione di copia di un file
 */
public class ActionCopyFile implements ActionInterface{
    private final File sourceFile;
    private final File destinationFile;


    /**
     * @param sourceFile Sorgente da cui copiare il file
     * @param destinationDirectory Cartella di destinazione in cui copiare il file
     * @throws IllegalArgumentException Se {@code sourceFile} o {@code destinationDirectory} sono nulli
     */
    public ActionCopyFile(File sourceFile, File destinationDirectory){
        if( sourceFile==null || destinationDirectory==null )
            throw new IllegalArgumentException("Entrambi i parametri devono essere non nulli");

        this.sourceFile= sourceFile;
        this.destinationFile= destinationDirectory.toPath().resolve( sourceFile.getName() ).toFile();
    }

    /**
     * Esegue una copia del file nella cartella di destinazione.
     * @return Vero se l'azione è eseguita altrimenti lancia un eccezione
     * @throws RuntimeException Sempre quando:
     * <ul>
     *     <li>Il file sorgente non è un file oppure non esiste</li>
     *     <li>La directory di destinazione è un file già esistente</li>
     *     <li></li>
     * </ul>
     */
    @Override
    public boolean execute() {
        if( !sourceFile.exists() || sourceFile.isDirectory() ) {
            String message= null;
            if( !sourceFile.exists() )
                message= "Il file sorgente '"+sourceFile.getName()+"' non esiste";
            if( sourceFile.isDirectory() )
                message= "Il file sorgente '"+sourceFile.getName()+"' è una cartella";

            throw new RuntimeException(message);
        }

        try {
            Files.createDirectories( destinationFile.getParentFile().toPath() );
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Il file di destinazione "+destinationFile.getName()+" esiste già");
        }

        return(true);
    }

    @Override
    public String toString() {
        return("Copia del file "+sourceFile.getName()+" nella cartella "+destinationFile.getName());
    }
}
