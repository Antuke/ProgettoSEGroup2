package com.segroup2.progettosegroup2.Actions;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
public class ActionDeleteFile implements ActionInterface, Serializable {

    private File file;
    /**
     * @param file  file da cancellare
     * @throws IllegalArgumentException Se {@code sourceFile} o {@code destinationDirectory} sono nulli
     */
    public ActionDeleteFile(File file) {
        this.file = file;
    }

    /**
     * La funzione si occupa di cancellare il fiel
     *
     * @throws RuntimeException quando il file è stato spostato/cancellato
     * @return boolean: True quando l'ese'cuzione termina con successo, False altrimenti
     */
    @Override
    public boolean execute() throws RuntimeException {

        if(!file.exists()) throw new RuntimeException(file.toString() + "è stato cancellato/spostato");
        return file.delete();
    }

    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }

    public String toString(){
        return "Cancellazione del file:" + file.toString();
    }
}
