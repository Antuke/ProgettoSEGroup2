package com.segroup2.progettosegroup2.Triggers;

import java.io.File;
import java.io.Serializable;

public class TriggerFileExists implements TriggerInterface, Serializable {
    private File file;

    public TriggerFileExists(File file) {
        this.file = file;
    }

    @Override
    public boolean check() {
        //Restituisco true se il file inizializzato esiste, false altrimenti
        return file.exists();
    }

    public String toString(){
        return "Trigger esistenza file: " + this.file.toString();
    }


}

